package com.tks.grocerymono.dto.response.category;

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
public class GetAllCategoryResponse extends QueryResponseData {
    @JsonProperty("categoryList")
    private List<CategoryResponse> categoryResponseList;
}
