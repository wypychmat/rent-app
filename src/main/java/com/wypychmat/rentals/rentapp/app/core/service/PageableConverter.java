package com.wypychmat.rentals.rentapp.app.core.service;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PageableCreatorService {


    public Pageable getPageableFromParam(int page, int size, String[] orders) {
        return PageRequest.of(page, size, Sort.by(getSortsWithOrderIgnoringCase(orders)));
    }


    private List<Sort.Order> getSortsWithOrderIgnoringCase(String[] requestSorts) {
        return Arrays.stream(requestSorts).map(singleSort -> {
            if (singleSort.contains(".")) {
                String[] sorts = singleSort.split("\\.");
                return new Sort.Order(getDirection(sorts[1]), sorts[0]).ignoreCase();
            }
            return new Sort.Order(getDirection(Sort.Direction.ASC.name()), singleSort).ignoreCase();
        }).collect(Collectors.toList());
    }


    private Sort.Direction getDirection(String direction) {
        direction = direction.toLowerCase();
        if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

}
