package com.wypychmat.rentals.rentapp.app.core.service.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@MailMessageSource
@Component
class HtmlRegisterMailSourceSource extends RegisterMailMessageSourceProvider {

    public HtmlRegisterMailSourceSource(@Value("${mail.message.html.source}") String message, @Value("${mail.message.html.sender}") String sender) {
        super(message, sender);
    }

}
