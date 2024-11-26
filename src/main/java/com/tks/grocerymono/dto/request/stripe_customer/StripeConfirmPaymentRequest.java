package com.tks.grocerymono.dto.request.stripe_customer;

import com.tks.grocerymono.base.dto.request.CommandRequestData;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class StripeConfirmPaymentRequest extends CommandRequestData {
    private Integer totalPrice;
}
