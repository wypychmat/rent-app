package com.wypychmat.rentals.rentapp.app.core.util.page.user;

public class PageParamUsernameEmailEnabled extends PageParamUsernameEmail {
    private final boolean enabled;

    PageParamUsernameEmailEnabled(int page, int size, String[] orders, String username, String email, boolean enabled) {
        super(page, size, orders, username, email);
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }


    static abstract class AbstractBuilder<T extends AbstractBuilder<T>> extends PageParamUsernameEmail.AbstractBuilder<T> {
        boolean enabled;

        public T setEnabled(boolean enabled) {
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
