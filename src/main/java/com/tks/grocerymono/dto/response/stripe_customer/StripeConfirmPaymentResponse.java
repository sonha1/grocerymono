package com.tks.grocerymono.dto.response.stripe_customer;

import com.tks.grocerymono.base.dto.response.CommandResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StripeConfirmPaymentResponse extends CommandResponseData {
    private String paymentIntentId;
    private String paymentIntentClientSecret;
    private String ephemeralKey;
    private String customer;
    private String publishableKey;
}
