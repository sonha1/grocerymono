package com.tks.grocerymono.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum CommonErrorCode implements AbstractError {
    PRODUCT_OUT_OF_STOCK(
            422,
            "Product is out of stock",
            HttpStatus.BAD_REQUEST),

    KEYCLOAK_OPERATION_ERROR(
            400,
            "Keycloak operation error",
            HttpStatus.BAD_REQUEST
    ),

    STRIPE_PAYMENT_ERROR(
            500,
            "Stripe payment error",
            HttpStatus.INTERNAL_SERVER_ERROR
    ),

    USER_NOT_FOUND(
            422,
            "User not found",
            HttpStatus.BAD_REQUEST),

    UPLOAD_IMAGE_FAILED(
            422,
            "Upload image failed!",
            HttpStatus.BAD_REQUEST
    );


    private final int code;

    private final String message;

    private final HttpStatus httpStatus;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
