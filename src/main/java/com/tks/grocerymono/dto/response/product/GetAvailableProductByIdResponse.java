package com.tks.grocerymono.dto.response.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tks.grocerymono.base.dto.response.QueryResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetAvailableProductByIdResponse extends QueryResponseData {
    private boolean isAvailable;
    @JsonProperty("product")
    private ProductResponse productResponse;
}
