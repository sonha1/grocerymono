package com.tks.grocerymono.enums;

import lombok.Getter;

@Getter
public enum BillStatus {
    PAID("Đã thanh toán"),
    COMPLETED("Đã hoàn thành"),
    PREPARED("Đã chuẩn bị"),
    CANCELLED("Đã hủy");

    private final String description;

    BillStatus(String description) {
        this.description = description;
    }
}
