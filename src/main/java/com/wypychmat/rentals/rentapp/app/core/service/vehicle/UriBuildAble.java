package com.wypychmat.rentals.rentapp.app.core.service.vehicle;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.function.Supplier;

interface UriBuildAble <T>{

    default URI getUri(Supplier<T> save, String pathVariable) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(pathVariable)
                .buildAndExpand(save.get())
                .toUri();
    }
}
