package com.wypychmat.rentals.rentapp.app.core.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.FileCopyUtils;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

abstract class EmailService<T> {
    public static final String CLASSPATH = "classpath:";
    protected final JavaMailSender javaMailSender;
    private final ResourceLoader resourceLoader;
    protected final RegisterMailMessageSourceProvider registerMailMessageSourceProvider;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    protected EmailService(JavaMailSender javaMailSender,
                           ResourceLoader resourceLoader,
                           RegisterMailMessageSourceProvider registerMailMessageSourceProvider) {
        this.javaMailSender = javaMailSender;
        this.resourceLoader = resourceLoader;
        this.registerMailMessageSourceProvider = registerMailMessageSourceProvider;
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

    protected abstract Function<T,Optional<Exception>> send();

    void sendEmail(RegistrationMessagePayload registrationMessagePayload) {
        CompletableFuture.runAsync(() -> {
            try {
                Optional<T> message = getMessage(registrationMessagePayload);
                if (message.isPresent()) {
                    Optional<Exception> optionalException = send().apply(message.get());
                    if(optionalException.isPresent())
                        throw new MessagingException("Empty email message",optionalException.get());
                } else {
                    throw new MessagingException("Empty email message");
                }
            } catch (MessagingException e) {
                e.printStackTrace();
                LOGGER.error("Registration email to: " + registrationMessagePayload.getEmail() + "wasn't send");
            }
        });
    }

    protected abstract Optional<T> getMessage(RegistrationMessagePayload registrationMessagePayload) throws MessagingException;

}
