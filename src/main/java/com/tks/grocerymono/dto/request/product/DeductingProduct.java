package com.tks.grocerymono.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeductingProduct {
    private Integer productId;
    private Integer deductingQuantity;
}
