package com.tks.grocerymono.dto.response.product;

import com.tks.grocerymono.base.dto.response.QueryResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetSimilarProductListByIdResponse extends QueryResponseData {
    private List<ProductResponse> productResponseList;
}
