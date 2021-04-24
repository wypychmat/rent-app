package com.wypychmat.rentals.rentapp.app.core.controller;


public class PageParam {
    private final int page;
    private final int size;
    private final String[] orders;

    protected PageParam(int page, int size, String[] orders) {
        this.page = page;
        this.size = size;
        this.orders = orders;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public String[] getOrders() {
        return orders;
    }

    public static BuilderP pageParamBuilder() {
        return new BuilderP();
    }

    public static class BuilderP extends PageParamBuilder<BuilderP> {

        public BuilderP() {
        }

        @Override
        public BuilderP returnThis() {
            return this;
        }

        PageParam build() {
            return new PageParam(page, size, orders);
        }
    }


}



