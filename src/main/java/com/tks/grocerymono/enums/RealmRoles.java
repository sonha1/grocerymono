package com.tks.grocerymono.enums;

import lombok.Getter;

@Getter
public enum RealmRoles {
    ADMIN("admin"),
    STAFF("staff"),
    CUSTOMER("customer");

    private final String role;

    RealmRoles(String role) {
        this.role = role;
    }
}
