package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.security.LoginRegisterPath;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.function.Function;


public class SimpleEmailMessageService extends EmailService<SimpleMailMessage> {
    protected SimpleEmailMessageService(JavaMailSender javaMailSender,
                                        ResourceLoader resourceLoader,
                                        RegisterMailMessageSourceProvider registerMailMessageSourceProvider,
                                        LoginRegisterPath loginRegisterPath) {
        super(javaMailSender, resourceLoader, registerMailMessageSourceProvider, loginRegisterPath);
    }

    @Override
    protected Function<SimpleMailMessage, Optional<Exception>> send() {
        return (simpleMail) -> {
            javaMailSender.send(simpleMail);
            return Optional.empty();
        };
    }

    @Override
    protected Optional<SimpleMailMessage> getMessage(RegistrationMessagePayload registrationMessagePayload,
                                                     String confirmationPath) throws MessagingException {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("test@testmail");
        mail.setSubject("Test message");
        mail.setText("Test message" + registrationMessagePayload.getToken());
        mail.setTo(registrationMessagePayload.getEmail());
        return Optional.of(mail);
    }
}
