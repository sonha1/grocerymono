package com.tks.grocerymono.controller;


import com.tks.grocerymono.base.dto.response.BaseResponse;
import com.tks.grocerymono.base.dto.response.ResponseFactory;
import com.tks.grocerymono.dto.request.stripe_customer.StripeConfirmPaymentRequest;
import com.tks.grocerymono.dto.response.stripe_customer.StripeConfirmPaymentResponse;
import com.tks.grocerymono.service.StripeCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class StripeCustomerController {
    private  final StripeCustomerService stripeCustomerService;
    private final ResponseFactory responseFactory;
    @PostMapping("/stripe")
    ResponseEntity<BaseResponse<StripeConfirmPaymentResponse>> stripePayment(
            @RequestBody StripeConfirmPaymentRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        request.setAccessToken(token.substring(7));
        return responseFactory.success(stripeCustomerService.stripeConfirmPayment(request));
    }
}

