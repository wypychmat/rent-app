package com.wypychmat.rentals.rentapp.app.core.util.page.user;

public class PageParamUsernameEmailEnabled extends PageParamUsernameEmail {
    private final String enabled;

    PageParamUsernameEmailEnabled(String page, String size, String[] orders, String username, String email, String enabled) {
        super(page, size, orders, username, email);
        this.enabled = enabled;
    }

    public String isEnabled() {
        return enabled;
    }


    static abstract class AbstractBuilder<T extends AbstractBuilder<T>> extends PageParamUsernameEmail.AbstractBuilder<T> {
        String enabled;

        public T setEnabled(String enabled) {
            this.enabled = enabled;
            return returnThis();
        }
    }

    public static class Builder extends AbstractBuilder<Builder> {
        @Override
        protected Builder returnThis() {
            return this;
        }

        public PageParamUsernameEmailEnabled build(){
            return new PageParamUsernameEmailEnabled(page,size,orders,username,email,enabled);
        }
    }

}
