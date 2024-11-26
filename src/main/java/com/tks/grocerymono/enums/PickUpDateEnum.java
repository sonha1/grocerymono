package com.tks.grocerymono.enums;

import lombok.Getter;

@Getter
public enum PickUpDateEnum {
    TODAY(0, "Hôm nay"),
    TOMORROW(1, "Ngày mai"),
    DAY_AFTER_TOMORROW(2, "Ngày kia");

    private final int arrivalDay;
    private final String description;

    PickUpDateEnum(int arrivalDay, String description) {
        this.arrivalDay = arrivalDay;
        this.description = description;
    }
}
