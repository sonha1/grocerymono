package com.tks.grocerymono.dto.response.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class GetAvailableProductListResponse extends QueryResponseData {
    @JsonProperty("isAvailable")
    private boolean isAvailable;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductResponse> unavailableProductResponseList;
}
