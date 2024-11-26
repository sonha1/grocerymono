package com.tks.grocerymono.service;

import com.tks.grocerymono.dto.request.cart.*;
import com.tks.grocerymono.dto.response.cart.*;

public interface CartService {
    AddToCartResponse addToCart(AddToCartRequest requestData);
    DeleteAndGetCartByIdListResponse deleteAndGetCartByIdList(DeleteAndGetCartByIdListRequest requestData);
    GetAllItemInCartResponse getAllItemInCart(GetAllItemInCartRequest requestData);
    DeleteCartItemByIdListResponse deleteCartItemByIdList(DeleteCartItemByIdListRequest requestData);
    UpdateCartQuantityResponse updateCartQuantity(UpdateCartQuantityRequest requestData);
}
