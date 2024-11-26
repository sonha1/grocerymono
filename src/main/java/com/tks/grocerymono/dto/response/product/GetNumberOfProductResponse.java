package com.tks.grocerymono.dto.response.product;

import com.tks.grocerymono.base.dto.response.QueryResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GetNumberOfProductResponse extends QueryResponseData {
    private long numberOfProduct;
}
