package com.tks.grocerymono.dto.request.cart;

import com.tks.grocerymono.base.dto.request.CommandRequestData;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartQuantityRequest extends CommandRequestData {
    @NotEmpty
    @Size(min = 36, max = 36)
    private String id;
    @Min(1)
    private Integer newQuantity;
}
