package com.tks.grocerymono.dto.response.product;

import com.tks.grocerymono.base.dto.response.CommandResponseData;
import com.tks.grocerymono.dto.request.product.DeductingProduct;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class DeductQuantityListProductResponse extends CommandResponseData {
    private List<DeductingProduct> deductingProductList;
}
