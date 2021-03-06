package com.wypychmat.rentals.rentapp.app.core.service.mail;

import com.wypychmat.rentals.rentapp.app.core.security.LoginRegisterPath;
import com.wypychmat.rentals.rentapp.app.core.dto.registration.RegistrationMessagePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@MailService
class MimeEmailService extends GenericEmailService<MimeMessage> {

    @Value("${config.email.hostAndPort}")
    String hostAndPort;

    @Autowired
    MimeEmailService(JavaMailSender javaMailSender,
                     ResourceLoader resourceLoader,
                     @MailMessageSource RegisterMailMessageSourceProvider registerMailMessageSourceProvider,
                     LoginRegisterPath loginRegisterPath) {
        super(javaMailSender, resourceLoader, registerMailMessageSourceProvider, loginRegisterPath);
    }

    @Override
    Function<MimeMessage, Optional<Exception>> send() {
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
    protected Optional<MimeMessage> getMessage(RegistrationMessagePayload registrationMessagePayload)
            throws MessagingException {
        Optional<String> resourceString = getResourceString();
        if (resourceString.isPresent()) {
            String path = hostAndPort + getConfirmationPath() + registrationMessagePayload.getToken();
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
