package com.wypychmat.rentals.rentapp.app.core.exception.vehicle;

import java.util.NoSuchElementException;

public class NoSuchModelMembersException extends NoSuchElementException {

    public NoSuchModelMembersException() {
    }

    public NoSuchModelMembersException(String s) {
        super(s);
    }
}
