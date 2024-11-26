package com.tks.grocerymono.service.impl;

import com.tks.grocerymono.dto.request.cart.*;
import com.tks.grocerymono.dto.request.product.GetAvailableProductByIdRequest;
import com.tks.grocerymono.dto.request.product.GetProductByIdListRequest;
import com.tks.grocerymono.dto.response.cart.*;
import com.tks.grocerymono.dto.response.product.GetAvailableProductByIdResponse;
import com.tks.grocerymono.dto.response.product.GetProductByIdListResponse;
import com.tks.grocerymono.dto.response.product.ProductResponse;
import com.tks.grocerymono.entity.Cart;
import com.tks.grocerymono.exception.BaseException;
import com.tks.grocerymono.exception.CommonErrorCode;
import com.tks.grocerymono.repository.CartRepository;
import com.tks.grocerymono.service.CartService;
import com.tks.grocerymono.service.ProductService;
import com.tks.grocerymono.utils.JsonWebTokenUtil;
import com.tks.grocerymono.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    @Override
    public AddToCartResponse addToCart(AddToCartRequest requestData) {
        String userId = JsonWebTokenUtil.getUserId(requestData.getAccessToken());
        if (userId == null) return null;

        Optional<Cart> optionalOldCart = cartRepository.findByUserIdAndProductId(userId, requestData.getProductId());
        if (optionalOldCart.isEmpty()) {
            GetAvailableProductByIdRequest clientRequest = GetAvailableProductByIdRequest
                    .builder()
                    .productId(requestData.getProductId())
                    .quantity(requestData.getQuantity())
                    .build();
            GetAvailableProductByIdResponse clientResponse = productService.getAvailableProductById(clientRequest);
            if (clientResponse.isAvailable()) {
                ProductResponse productResponse = clientResponse.getProductResponse();
                Integer totalPrice = productResponse.getUnitPrice() * requestData.getQuantity();
                Cart cart = Cart.builder()
                        .id(UUID.randomUUID().toString())
                        .productId(requestData.getProductId())
                        .userId(userId)
                        .totalPrice(totalPrice)
                        .quantity(requestData.getQuantity())
                        .build();
                cart = cartRepository.save(cart);
                return Mapper.map(cart, AddToCartResponse.class);
            } else {
                throw new BaseException(CommonErrorCode.PRODUCT_OUT_OF_STOCK);
            }
        } else {
            Cart updatingCart = optionalOldCart.get();
            int newCartQuantity = updatingCart.getQuantity() + requestData.getQuantity();
            GetAvailableProductByIdRequest clientRequest = GetAvailableProductByIdRequest
                    .builder()
                    .productId(requestData.getProductId())
                    .quantity(newCartQuantity)
                    .build();
            GetAvailableProductByIdResponse clientResponse = productService.getAvailableProductById(clientRequest);
            if (clientResponse.isAvailable()) {
                int newCartTotalPrice = updatingCart.getTotalPrice() / updatingCart.getQuantity() * newCartQuantity;
                updatingCart.setQuantity(newCartQuantity);
                updatingCart.setTotalPrice(newCartTotalPrice);
                cartRepository.save(updatingCart);
                return Mapper.map(updatingCart, AddToCartResponse.class);
            } else {
                throw new BaseException(CommonErrorCode.PRODUCT_OUT_OF_STOCK);
            }
        }
    }

    @Transactional
    @Override
    public DeleteAndGetCartByIdListResponse deleteAndGetCartByIdList(DeleteAndGetCartByIdListRequest requestData) {
        List<Cart> cartList = cartRepository.findAllById(requestData.getIdList());
        List<InternalCartResponse> cartResponseList = Mapper.mapList(cartList, InternalCartResponse.class);
        cartRepository.deleteAllById(requestData.getIdList());
        return new DeleteAndGetCartByIdListResponse(cartResponseList);
    }

    @Override
    public DeleteCartItemByIdListResponse deleteCartItemByIdList(DeleteCartItemByIdListRequest requestData) {
        cartRepository.deleteAllById(requestData.getIdList());
        return new DeleteCartItemByIdListResponse(requestData.getIdList());
    }


    @Override
    public GetAllItemInCartResponse getAllItemInCart(GetAllItemInCartRequest requestData) {
        String userId = JsonWebTokenUtil.getUserId(requestData.getAccessToken());
        if (userId == null) return null;

        List<Cart> userCartList = cartRepository.findByUserId(
                userId,
                Sort.by(Sort.Direction.DESC, "createdDate")
        );
        List<Integer> productIdList = userCartList
                .stream()
                .map(Cart::getProductId)
                .toList();
        GetProductByIdListRequest getProductByIdListRequest = new GetProductByIdListRequest(productIdList);
        GetProductByIdListResponse getProductByIdListResponse = productService.getProductByIdList(getProductByIdListRequest);
        List<CartResponse> cartResponseList = userCartList
                .stream()
                .map(cart -> {
                    ProductResponse productResponse = getProductByIdListResponse.getProductResponseList()
                            .stream()
                            .filter(product -> Objects.equals(product.getId(), cart.getProductId()))
                            .findFirst()
                            .get();
                    return new CartResponse(cart.getId(), productResponse, cart.getQuantity());
                })
                .toList();
        return new GetAllItemInCartResponse(cartResponseList);
    }

    @Override
    public UpdateCartQuantityResponse updateCartQuantity(UpdateCartQuantityRequest requestData) {
        Cart cart = cartRepository.findById(requestData.getId()).get();
        Integer newTotalPrice = cart.getTotalPrice() / cart.getQuantity() * requestData.getNewQuantity();
        cart.setQuantity(requestData.getNewQuantity());
        cart.setTotalPrice(newTotalPrice);
        cartRepository.save(cart);
        return new UpdateCartQuantityResponse(Mapper.map(cart, CartResponse.class));
    }
}
