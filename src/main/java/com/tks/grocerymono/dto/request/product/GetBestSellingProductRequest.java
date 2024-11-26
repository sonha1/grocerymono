package com.tks.grocerymono.dto.request.product;

import com.tks.grocerymono.base.dto.request.QueryRequestData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class GetBestSellingProductRequest extends QueryRequestData {
}
