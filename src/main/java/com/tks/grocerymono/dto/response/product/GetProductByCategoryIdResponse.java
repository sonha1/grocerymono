package com.tks.grocerymono.dto.response.product;

import com.tks.grocerymono.base.dto.response.QueryResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GetProductByCategoryIdResponse extends QueryResponseData {
    private List<ProductResponse> productResponseList;
}
