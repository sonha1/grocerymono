package com.tks.grocerymono.dto.response.cart;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternalCartResponse {
    private String id;
    private String userId;
    private Integer productId;
    private Integer quantity;
    private Integer totalPrice;
}
