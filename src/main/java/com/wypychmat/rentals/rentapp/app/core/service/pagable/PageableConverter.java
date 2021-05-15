package com.wypychmat.rentals.rentapp.app.core.service.pagable;


import com.wypychmat.rentals.rentapp.app.core.util.page.PageParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.*;

@Service
public class PageableConverter {

    private final PageableValidatorService pageableValidatorService;

    public PageableConverter(Validator validator) {
        this.pageableValidatorService = new PageableValidatorService(validator);
    }


    public Pageable getPageableFromParam(PageParam pageParam) {
        pageableValidatorService.validatePageRequestOrThrow(pageParam);
        return PageRequest.of(
                Integer.parseInt(pageParam.getPage()),
                Integer.parseInt(pageParam.getSize()),
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
