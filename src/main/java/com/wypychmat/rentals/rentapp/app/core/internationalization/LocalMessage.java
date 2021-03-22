package com.wypychmat.rentals.rentapp.app.core.internationalization;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class LocalMessage {
    private final MessageSource messageSource;

    public LocalMessage(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getLocalizedMessage(String messageKey) {
        return messageSource.getMessage(messageKey, null, getLocale());
    }

    private Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
