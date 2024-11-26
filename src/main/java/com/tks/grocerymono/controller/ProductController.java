package com.tks.grocerymono.controller;

import com.tks.grocerymono.base.dto.response.BaseResponse;
import com.tks.grocerymono.base.dto.response.ResponseFactory;
import com.tks.grocerymono.dto.request.product.*;
import com.tks.grocerymono.dto.response.product.*;
import com.tks.grocerymono.service.ProductService;
import com.tks.grocerymono.utils.JsonMapperUtil;
import com.tks.grocerymono.utils.PageSupport;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ResponseFactory responseFactory;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BaseResponse<CreateProductResponse>> createProduct(
            @RequestParam("files") MultipartFile[] fileList,
            @RequestParam("product") String productJson
    ) {
        CreateProductRequest request = JsonMapperUtil.parseJsonToObject(productJson, CreateProductRequest.class);
        request.setFileList(List.of(fileList));
        return responseFactory.success(productService.createProduct(request));
    }


    @PutMapping("/deductQuantity")
    ResponseEntity<BaseResponse<DeductQuantityListProductResponse>> deductQuantityListProduct(
            @RequestBody DeductQuantityListProductRequest request
    ) {
        return responseFactory.success(productService.deductQuantityListProduct(request));
    }


    @DeleteMapping("/{id}")
    ResponseEntity<BaseResponse<DeleteProductByIdResponse>> deleteProductById(@PathVariable("id") int id) {
        return responseFactory.success(productService.deleteProductById(new DeleteProductByIdRequest(id)));
    }

    @GetMapping("/internal/available")
    ResponseEntity<BaseResponse<GetAvailableProductByIdResponse>> getAvailableProductById(
            @RequestParam("productId") Integer productId,
            @RequestParam("quantity") Integer quantity
    ) {
        return responseFactory.success(productService.getAvailableProductById(new GetAvailableProductByIdRequest(productId, quantity)));
    }

    @PostMapping("/available")
    ResponseEntity<BaseResponse<GetAvailableProductListResponse>> getAvailableProductList(@RequestBody GetAvailableProductListRequest request) {
        return responseFactory.success(productService.getAvailableProductList(request));
    }


    @GetMapping("/bestSelling")
    ResponseEntity<BaseResponse<GetBestSellingProductResponse>> getBestSellingProduct() {
        return responseFactory.success(productService.getBestSellingProduct(new GetBestSellingProductRequest()));
    }

    @GetMapping("/bestSellingQuantity")
    ResponseEntity<BaseResponse<GetBestSellingProductQuantityResponse>> getBestSellingProductQuantity() {
        return responseFactory.success(productService.getBestSellingProductQuantity(new GetBestSellingProductQuantityRequest()));
    }


    @GetMapping("/numberOfProduct")
    ResponseEntity<BaseResponse<GetNumberOfProductResponse>> getNumberOfProduct() {
        return responseFactory.success(productService.getNumberOfProduct(new GetNumberOfProductRequest()));
    }

    @GetMapping("/category")
    ResponseEntity<BaseResponse<GetProductByCategoryIdResponse>> getProductByCategoryId(
            @RequestParam("categoryId") int categoryId
    ) {
        return responseFactory.success(productService.getProductByCategoryId(new GetProductByCategoryIdRequest(categoryId)));
    }

    @GetMapping
    ResponseEntity<BaseResponse<PageSupport<ProductResponse>>> getProductByCategoryIdPaging(
            @RequestParam(value = "categoryId") Integer categoryId,
            @RequestParam(value = "page") @Min(1) int pageNumber,
            @RequestParam(value = "size") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        GetProductByCategoryIdPagingRequest request = GetProductByCategoryIdPagingRequest.builder()
                .categoryId(categoryId)
                .pageable(pageable)
                .build();
        return responseFactory.success(productService.getProductByCategoryIdPaging(request));
    }

    @GetMapping("/{id}")
    ResponseEntity<BaseResponse<ProductResponse>> getProductById(@PathVariable("id") Integer id) {
        return responseFactory.success(productService.getProductById(new GetProductByIdRequest(id)));
    }

    @GetMapping
    ResponseEntity<BaseResponse<GetProductByIdListResponse>> getProductByIdList(
            @RequestParam("idList") List<Integer> productIdList
    ) {
        return responseFactory.success(productService.getProductByIdList(new GetProductByIdListRequest(productIdList)));
    }

    @GetMapping("/recommended")
    ResponseEntity<BaseResponse<GetRecommendedProductResponse>> getRecommendedProduct(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {
        GetRecommendedProductRequest request = GetRecommendedProductRequest.builder()
                .accessToken(token)
                .build();
        return responseFactory.success(productService.getRecommendedProduct(request));
    }

    @GetMapping("/similar")
    ResponseEntity<BaseResponse<GetSimilarProductListByIdResponse>> getSimilarProductListById(@RequestParam("id") int id) {
        return responseFactory.success(productService.getSimilarProductListById(new GetSimilarProductListByIdRequest(id)));
    }

    @GetMapping("/search")
    ResponseEntity<BaseResponse<SearchProductResponse>> searchProduct(@RequestParam("query") String query) {
        return responseFactory.success(productService.searchProduct(new SearchProductRequest(query)));
    }

    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BaseResponse<UpdateProductResponse>> updateProduct(
            @RequestParam(name = "files", required = false) MultipartFile[] fileList,
            @RequestParam("product") String productJson
    ) {
        UpdateProductRequest request = JsonMapperUtil.parseJsonToObject(productJson, UpdateProductRequest.class);
        if (fileList != null) {
            request.setFileList(List.of(fileList));
        }
        return responseFactory.success(productService.updateProduct(request));
    }
}
