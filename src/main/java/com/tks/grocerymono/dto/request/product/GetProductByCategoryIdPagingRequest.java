package com.tks.grocerymono.dto.request.product;

import com.tks.grocerymono.base.dto.request.QueryRequestData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GetProductByCategoryIdPagingRequest extends QueryRequestData {
    private Integer categoryId;
}