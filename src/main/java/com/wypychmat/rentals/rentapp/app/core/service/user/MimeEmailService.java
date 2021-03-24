package com.wypychmat.rentals.rentapp.app.core.service.user;

import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;

@Service
public class MimeEmailService implements EmailService {
    private final static Logger LOGGER = LoggerFactory.getLogger(MimeEmailService.class);
    private final JavaMailSender javaMailSender;
    private final ResourceLoader resourceLoader;

    public MimeEmailService(JavaMailSender javaMailSender, ResourceLoader resourceLoader) {
        this.javaMailSender = javaMailSender;
        this.resourceLoader = resourceLoader;
    }


    @Override
    public void sendEmail(RegistrationToken registrationToken) {
        CompletableFuture.runAsync(() -> {
            try (InputStream inputStream =
                         resourceLoader.getResource("classpath:email/registrationEmail.html").getInputStream()) {
                byte[] byteArrayData = FileCopyUtils.copyToByteArray(inputStream);
                MessageFormat formatter = new MessageFormat(new String(byteArrayData, StandardCharsets.UTF_8));
                String format = formatter.format(new Object[]{registrationToken.getUsername(), registrationToken.getRegistrationToken()});
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper =
                        new MimeMessageHelper(mimeMessage, "utf-8");
                helper.setText(format, true);
                helper.setTo(registrationToken.getEmail());
                helper.setSubject("Confirm your email");
                helper.setFrom("fmatspotify@gmail.com");
                javaMailSender.send(mimeMessage);
            } catch (IOException | MessagingException e) {
                LOGGER.error("Email wasn't sent to: " + registrationToken.getEmail() + "!!!", e);
                e.printStackTrace();
            }
        });
    }
}
