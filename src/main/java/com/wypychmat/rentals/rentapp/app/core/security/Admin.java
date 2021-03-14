package com.wypychmat.rentals.rentapp.app.core.security;

class Admin {
    private final String adminName;
    private final String password;

    Admin(String adminName, String password) {
        this.adminName = adminName;
        this.password = password;
    }

    String getAdminName() {
        return adminName;
    }

    String getPassword() {
        return password;
    }
}
