package com.wypychmat.rentals.rentapp.app.core.util.page;

public class PageParamUsernamePassword extends PageParamUsername {
    private final String password;

    PageParamUsernamePassword(int page, int size, String[] orders, String username, String password) {
        super(page, size, orders, username);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    static abstract class AbstractBuilder<T extends AbstractBuilder<T>> extends PageParamUsername.AbstractBuilder<T> {
        String password;

        public T setPassword(String password) {
            this.password = password;
            return returnThis();
        }
    }

    public static class Builder extends AbstractBuilder<Builder> {

        @Override
        Builder returnThis() {
            return this;
        }

        public PageParamUsernamePassword build() {
            return new PageParamUsernamePassword(page, size, orders, username, password);
        }
    }

}
