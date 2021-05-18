package com.wypychmat.rentals.rentapp.app.core.exception.vehicle;

import java.util.NoSuchElementException;

// TODO: 16.05.2021 add handler
public class NoSuchModelMembersException extends NoSuchElementException {

    public NoSuchModelMembersException() {
    }

    public NoSuchModelMembersException(String s) {
        super(s);
    }
}
