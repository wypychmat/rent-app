package com.wypychmat.rentals.rentapp.app.core.service.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@MailMessageSource(type = MailMessageSource.Type.TEXT)
@Component
class TextRegisterMailSourceSource extends RegisterMailMessageSourceProvider {

    public TextRegisterMailSourceSource(@Value("${mail.message.text.source}") String message, @Value("${mail.message.text.sender}") String sender) {
        super(message, sender);
    }

}
