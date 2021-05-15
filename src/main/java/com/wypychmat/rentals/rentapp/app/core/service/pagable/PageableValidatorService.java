package com.wypychmat.rentals.rentapp.app.core.service.pagable;

import com.wypychmat.rentals.rentapp.app.core.exception.global.InvalidPageRequestException;
import com.wypychmat.rentals.rentapp.app.core.util.page.PageParam;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

class PageableValidatorService {

    private final Validator validator;

    PageableValidatorService(Validator validator) {
        this.validator = validator;
    }


    void validatePageRequestOrThrow(PageParam pageParam) {
        Set<ConstraintViolation<PageParam>> validate = validator.validate(pageParam);
        if (!validate.isEmpty()) {
            throw new InvalidPageRequestException();
        }
    }
}
