package com.wypychmat.rentals.rentapp.app.core.service.mail;

import com.wypychmat.rentals.rentapp.app.core.security.LoginRegisterPath;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationMessagePayload;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.function.Function;

@Service
@MailService(type = MailService.Type.SIMPLE)
public class SimpleEmailMessageService extends GenericEmailService<SimpleMailMessage> {
    protected SimpleEmailMessageService(JavaMailSender javaMailSender,
                                        ResourceLoader resourceLoader,
                                        @MailMessageSource(type = MailMessageSource.Type.TEXT)
                                                RegisterMailMessageSourceProvider registerMailMessageSourceProvider,
                                        LoginRegisterPath loginRegisterPath) {
        super(javaMailSender, resourceLoader, registerMailMessageSourceProvider, loginRegisterPath);
    }

    @Override
    Function<SimpleMailMessage, Optional<Exception>> send() {
        return (simpleMail) -> {
            javaMailSender.send(simpleMail);
            System.out.println("mail was send");
            return Optional.empty();
        };
    }

    @Override
    protected Optional<SimpleMailMessage> getMessage(RegistrationMessagePayload registrationMessagePayload) throws MessagingException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(registerMailMessageSourceProvider.getSender());
        mail.setSubject("Test message-Rent-APP Confirmation email");
        mail.setText("Test message" + registrationMessagePayload.getToken());
        mail.setTo(registrationMessagePayload.getEmail());
        return Optional.of(mail);
    }
}
