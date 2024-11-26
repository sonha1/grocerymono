package com.tks.grocerymono.dto.request.cart;

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
public class GetAllItemInCartRequest extends QueryRequestData {
}
