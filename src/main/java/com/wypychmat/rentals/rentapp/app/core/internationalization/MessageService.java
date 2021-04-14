package com.wypychmat.rentals.rentapp.app.core.internationalization;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageService extends MessageProviderCenter{
    public MessageService(MessageSource messageSource) {
        super(messageSource);
    }
}
