package com.tks.grocerymono.dto.request.product;

import com.tks.grocerymono.base.dto.request.CommandRequestData;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class DeductQuantityListProductRequest extends CommandRequestData {
    private List<DeductingProduct> deductingProductList;
}
