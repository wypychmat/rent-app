package com.wypychmat.rentals.rentapp.app.core.service.user;

abstract class RegisterMailMessageSourceProvider {
    private final String messageSource;
    private final String sender;

    RegisterMailMessageSourceProvider(String messageSource, String sender) {
        this.messageSource = messageSource;
        this.sender = sender;
    }

    String getMessageSource() {
        return messageSource;
    }

    String getSender() {
        return sender;
    }
}
