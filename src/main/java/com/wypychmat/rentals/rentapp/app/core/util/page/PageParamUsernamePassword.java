package com.wypychmat.rentals.rentapp.app.core.util.page;

public class PageParamUsername extends PageParam {
    private final String username;

    public PageParamUsername(int page, int size, String[] orders, String username) {
        super(page, size, orders);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
