package com.wypychmat.rentals.rentapp.app.core.service.user;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;


@Target({
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.TYPE,
        ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@interface MailMessageSource {
    MessageType type() default MessageType.DEFAULT_HTML;

    enum MessageType {
        DEFAULT_HTML, OTHER_HTML;
    }

}
