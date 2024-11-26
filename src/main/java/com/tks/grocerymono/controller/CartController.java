package com.tks.grocerymono.controller;

import com.tks.grocerymono.base.dto.response.BaseResponse;
import com.tks.grocerymono.base.dto.response.ResponseFactory;
import com.tks.grocerymono.dto.request.cart.*;
import com.tks.grocerymono.dto.response.cart.*;
import com.tks.grocerymono.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final ResponseFactory responseFactory;


    @PostMapping("/add")
    ResponseEntity<BaseResponse<AddToCartResponse>> addToCart(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestBody AddToCartRequest request
    ) {
        request.setAccessToken(token.substring(7));
        return responseFactory.success(cartService.addToCart(request));
    }

    @DeleteMapping("/deleteAndGet")
    ResponseEntity<BaseResponse<DeleteAndGetCartByIdListResponse>> deleteAndGetCartByIdList(
            @RequestParam("idList") List<String> idList
    ) {
        return responseFactory.success(cartService.deleteAndGetCartByIdList(new DeleteAndGetCartByIdListRequest(idList)));
    }


    @DeleteMapping
    ResponseEntity<BaseResponse<DeleteCartItemByIdListResponse>> deleteCartItemByIdList(
            @RequestParam("idList") List<String> idList
    ) {
        return responseFactory.success(cartService.deleteCartItemByIdList(new DeleteCartItemByIdListRequest(idList)));
    }

    @GetMapping("/all")
    ResponseEntity<BaseResponse<GetAllItemInCartResponse>> getAllItemInCart(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {
        GetAllItemInCartRequest request = new GetAllItemInCartRequest();
        request.setAccessToken(token.substring(7));
        return responseFactory.success(cartService.getAllItemInCart(request));
    }

    @PutMapping("/quantity")
    ResponseEntity<BaseResponse<UpdateCartQuantityResponse>> updateCartQuantity(
            @RequestBody @Valid UpdateCartQuantityRequest request
    ) {
        return responseFactory.success(cartService.updateCartQuantity(request));
    }
}
