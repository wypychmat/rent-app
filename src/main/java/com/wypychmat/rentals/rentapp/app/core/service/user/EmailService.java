package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.security.LoginRegisterPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

abstract class EmailService<T> {
    public static final String CLASSPATH = "classpath:";
    protected final JavaMailSender javaMailSender;
    private final ResourceLoader resourceLoader;
    protected final RegisterMailMessageSourceProvider registerMailMessageSourceProvider;
    private final LoginRegisterPath loginRegisterPath;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    protected EmailService(JavaMailSender javaMailSender,
                           ResourceLoader resourceLoader,
                           RegisterMailMessageSourceProvider registerMailMessageSourceProvider, LoginRegisterPath loginRegisterPath) {
        this.javaMailSender = javaMailSender;
        this.resourceLoader = resourceLoader;
        this.registerMailMessageSourceProvider = registerMailMessageSourceProvider;
        this.loginRegisterPath = loginRegisterPath;
    }

    protected Optional<String> getResourceString() {
        try (InputStream inputStream =
                     resourceLoader.getResource(CLASSPATH + registerMailMessageSourceProvider.getMessageSource()).getInputStream()) {
            byte[] byteArrayData = FileCopyUtils.copyToByteArray(inputStream);
            String emailMessage = new String(byteArrayData, StandardCharsets.UTF_8);
            return Optional.of(emailMessage);
        } catch (IOException e) {
            LOGGER.error("Cannot read email from resource");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    protected abstract Function<T, Optional<Exception>> send();

    @Async
    void sendEmail(RegistrationMessagePayload registrationMessagePayload) {
        Runnable runnable = getSendRunnable(registrationMessagePayload, getConfirmationPath());
        sendAsync(runnable);
    }

    private Runnable getSendRunnable(RegistrationMessagePayload registrationMessagePayload, String confirmationPath) {
        return () -> {
            try {
                Optional<T> message = getMessage(registrationMessagePayload, confirmationPath);
                if (message.isPresent()) {
                    Optional<Exception> optionalException = send().apply(message.get());
                    if (optionalException.isPresent())
                        throw new MessagingException("Empty email message", optionalException.get());
                } else {
                    throw new MessagingException("Empty email message");
                }
            } catch (MessagingException e) {
                e.printStackTrace();
                LOGGER.error("Registration email to: " + registrationMessagePayload.getEmail() + "wasn't send");
            }
        };
    }

    void sendAsync(Runnable send) {
        CompletableFuture.runAsync(send);
    }

    // TODO: 28.03.2021 change later to frontendPath
    protected String getConfirmationPath() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + loginRegisterPath.getConfirmPath();
    }

    protected abstract Optional<T> getMessage(RegistrationMessagePayload registrationMessagePayload, String confirmationPath) throws MessagingException;

}
