package com.tks.grocerymono.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PageSupport<T>(List<T> content, int pageNumber, int pageSize, long totalElements) {
    private static final String FIRST_PAGE_NUM = "1";
    private static final String DEFAULT_PAGE_SIZE = "20";

    @JsonProperty
    public long totalPages() {
        return pageSize > 0 ? (totalElements - 1) / pageSize + 1 : 0;
    }

    @JsonProperty
    public boolean first() {
        return pageNumber == Integer.parseInt(FIRST_PAGE_NUM);
    }

    @JsonProperty
    public boolean last() {
        return pageNumber * pageSize >= totalElements;
    }
}
