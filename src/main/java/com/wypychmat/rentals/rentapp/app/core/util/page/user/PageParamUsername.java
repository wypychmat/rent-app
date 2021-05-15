package com.wypychmat.rentals.rentapp.app.core.util.page.user;

import com.wypychmat.rentals.rentapp.app.core.util.page.PageParam;

public class PageParamUsername extends PageParam {
    private final String username;

    PageParamUsername(String page, String size, String[] orders, String username) {
        super(page, size, orders);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    static abstract class AbstractBuilder<T extends AbstractBuilder<T>> extends PageParam.AbstractBuilder<T> {

        String username;

        public T setUsername(String username) {
            this.username = username.trim();
            return returnThis();
        }
    }

    public static class Builder extends AbstractBuilder<Builder> {

        @Override
       protected Builder returnThis() {
            return this;
        }

        public PageParamUsername build() {
            return new PageParamUsername(page, size, orders, username);
        }
    }
}
