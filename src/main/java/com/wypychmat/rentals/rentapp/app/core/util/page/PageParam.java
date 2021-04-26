package com.wypychmat.rentals.rentapp.app.core.util.page;


import com.wypychmat.rentals.rentapp.app.core.validation.pagable.ValidPage;
import com.wypychmat.rentals.rentapp.app.core.validation.pagable.ValidPageSize;
import com.wypychmat.rentals.rentapp.app.core.validation.pagable.ValidSort;

import java.lang.reflect.Constructor;

public class PageParam {

    @ValidPage
    private final String page;
    @ValidPageSize
    private final String size;
    @ValidSort
    private final String[] orders;

    protected PageParam(String page, String size, String[] orders) {
        this.page = page;
        this.size = size;
        this.orders = orders;
    }

    public String getPage() {
        return page;
    }

    public String getSize() {
        return size;
    }

    public String[] getOrders() {
        return orders;
    }


    public static <T extends AbstractBuilder<T>> T builder(Class<T> builderClass) {
        try {
            Constructor<?> constructor = builderClass.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            return builderClass.cast(constructor.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new PageBuilderException("CANNOT CAST TYPE BUILDER");
    }


    protected static abstract class AbstractBuilder<T extends AbstractBuilder<T>> {
        protected String page;
        protected String size;
        protected String[] orders;

        protected AbstractBuilder() {
        }

        public T setPage(String page) {
            this.page = page;
            return returnThis();
        }

        public T setSize(String size) {
            this.size = size;
            return returnThis();
        }

        public T setOrders(String[] orders) {
            this.orders = orders;
            return returnThis();
        }

        protected abstract T returnThis();

    }

    public static class Builder extends AbstractBuilder<Builder> {

        @Override
        protected Builder returnThis() {
            return this;
        }

        public PageParam build() {
            return new PageParam(page, size, orders);
        }

    }
}



