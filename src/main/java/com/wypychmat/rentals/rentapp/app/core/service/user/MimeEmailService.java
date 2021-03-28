package com.wypychmat.rentals.rentapp.app.core.service.user;

import com.wypychmat.rentals.rentapp.app.core.security.LoginRegisterPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.function.Function;

@Service
class MimeEmailService extends EmailService<MimeMessage> {

    @Autowired
    MimeEmailService(JavaMailSender javaMailSender,
                     ResourceLoader resourceLoader,
                     RegisterMailMessageSourceProvider registerMailMessageSourceProvider,
                     LoginRegisterPath loginRegisterPath) {
        super(javaMailSender, resourceLoader, registerMailMessageSourceProvider, loginRegisterPath);
    }

    @Override
    protected Function<MimeMessage, Optional<Exception>> send() {
        return (mimeMessage -> {
            try {
                javaMailSender.send(mimeMessage);
            } catch (Exception e) {
                return Optional.of(e);
            }
            return Optional.empty();
        });
    }


    @Override
    protected Optional<MimeMessage> getMessage(RegistrationMessagePayload registrationMessagePayload, String confirmationPath)
            throws MessagingException {
        Optional<String> resourceString = getResourceString();
        if (resourceString.isPresent()) {
            String path = confirmationPath + registrationMessagePayload.getToken();
            MessageFormat formatter = new MessageFormat(resourceString.get());
            String format = formatter.format(new Object[]{
                    registrationMessagePayload.getUsername(),
                    path
            });
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(format, true);
            helper.setTo(registrationMessagePayload.getEmail());
            helper.setSubject("Rent-APP Confirmation email");
            helper.setFrom(registerMailMessageSourceProvider.getSender());
            return Optional.of(mimeMessage);
        }
        return Optional.empty();
    }
}
