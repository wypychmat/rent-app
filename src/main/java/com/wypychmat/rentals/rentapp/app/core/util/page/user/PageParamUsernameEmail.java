package com.wypychmat.rentals.rentapp.app.core.util.page.user;

public class PageParamUsernameEmail extends PageParamUsername {
    private final String email;

    PageParamUsernameEmail(int page, int size, String[] orders, String username, String email) {
        super(page, size, orders, username);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    static abstract class AbstractBuilder<T extends AbstractBuilder<T>> extends PageParamUsername.AbstractBuilder<T> {
        String email;

        public T setEmail(String email) {
            this.email = email.trim();
            return returnThis();
        }
    }

    public static class Builder extends AbstractBuilder<Builder> {

        @Override
       protected Builder returnThis() {
            return this;
        }

        public PageParamUsernameEmail build() {
            return new PageParamUsernameEmail(page, size, orders, username, email);
        }
    }

}

