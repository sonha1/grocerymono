package com.tks.grocerymono.base.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    /**
     * The result of the response, true if successful and false otherwise
     */
    private boolean success;

    /**
     * Custom response code
     */
    private int code;

    /**
     * Response data
     */
    private String message;

    /**
     * Response data
     */
    private T data;



}
