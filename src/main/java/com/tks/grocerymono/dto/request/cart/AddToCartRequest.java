package com.tks.grocerymono.dto.request.cart;

import com.tks.grocerymono.base.dto.request.CommandRequestData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequest extends CommandRequestData {
    private Integer productId;
    private Integer quantity;
}
