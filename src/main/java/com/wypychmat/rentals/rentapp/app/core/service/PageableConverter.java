package com.wypychmat.rentals.rentapp.app.core.service;


import com.wypychmat.rentals.rentapp.app.core.util.page.PageParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.*;

public class PageableConverter {


    public Pageable getPageableFromParam(PageParam pageParam) {
        return PageRequest.of(
                pageParam.getPage(),
                pageParam.getSize(),
                by(getSortsWithOrderIgnoringCase(pageParam.getOrders())));
    }

    private List<Order> getSortsWithOrderIgnoringCase(String[] requestSorts) {
        return Arrays.stream(requestSorts).map(singleSort -> {
            if (singleSort.contains(".")) {
                String[] sorts = singleSort.split("\\.");
                return new Order(getDirection(sorts[1]), sorts[0]).ignoreCase();
            }
            return new Order(getDirection(Direction.ASC.name()), singleSort).ignoreCase();
        }).collect(Collectors.toList());
    }

    private Direction getDirection(String direction) {
        direction = direction.toLowerCase();
        if (direction.equals("desc")) {
            return Direction.DESC;
        }
        return Direction.ASC;
    }

}
