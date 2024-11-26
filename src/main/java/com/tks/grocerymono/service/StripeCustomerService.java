package com.tks.grocerymono.service;

import com.tks.grocerymono.dto.request.stripe_customer.StripeConfirmPaymentRequest;
import com.tks.grocerymono.dto.response.stripe_customer.StripeConfirmPaymentResponse;

public interface StripeCustomerService {

    StripeConfirmPaymentResponse stripeConfirmPayment(StripeConfirmPaymentRequest requestData);
}
