package com.tks.grocerymono.service;


import com.tks.grocerymono.dto.request.product.*;
import com.tks.grocerymono.dto.response.product.*;
import com.tks.grocerymono.utils.PageSupport;

public interface ProductService {
    CreateProductResponse createProduct(CreateProductRequest requestData);

    DeductQuantityListProductResponse deductQuantityListProduct(DeductQuantityListProductRequest requestData);

    DeleteProductByIdResponse deleteProductById(DeleteProductByIdRequest requestData);

    GetAvailableProductByIdResponse getAvailableProductById(GetAvailableProductByIdRequest requestData);

    GetAvailableProductListResponse getAvailableProductList(GetAvailableProductListRequest requestData);

    GetNumberOfProductResponse getNumberOfProduct(GetNumberOfProductRequest requestData);

    GetProductByCategoryIdResponse getProductByCategoryId(GetProductByCategoryIdRequest requestData);

    PageSupport<ProductResponse> getProductByCategoryIdPaging(GetProductByCategoryIdPagingRequest requestData);

    ProductResponse getProductById(GetProductByIdRequest requestData);

    GetProductByIdListResponse getProductByIdList(GetProductByIdListRequest requestData);

    SearchProductResponse searchProduct(SearchProductRequest requestData);

    UpdateProductResponse updateProduct(UpdateProductRequest requestData);

    GetBestSellingProductResponse getBestSellingProduct(GetBestSellingProductRequest requestData);

    GetBestSellingProductQuantityResponse getBestSellingProductQuantity(GetBestSellingProductQuantityRequest requestData);

    GetRecommendedProductResponse getRecommendedProduct(GetRecommendedProductRequest requestData);

    GetSimilarProductListByIdResponse getSimilarProductListById(GetSimilarProductListByIdRequest requestData);
}
