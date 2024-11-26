package com.tks.grocerymono.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BestSellingProductResponse {
    private int id;
    private String name;
    private int quantity;
}
