package com.tks.grocerymono.dto.response.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tks.grocerymono.dto.response.product.ProductResponse;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    private String id;
    @JsonProperty("product")
    private ProductResponse productResponse;
    private Integer quantity;
}
