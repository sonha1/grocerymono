package com.tks.grocerymono.dto.response.category;

import com.tks.grocerymono.base.dto.response.QueryResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GetNumberOfCategoryResponse extends QueryResponseData {
    private long numberOfCategory;
}
