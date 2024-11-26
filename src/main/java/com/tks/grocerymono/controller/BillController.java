package com.tks.grocerymono.controller;


import com.tks.grocerymono.base.dto.response.BaseResponse;
import com.tks.grocerymono.base.dto.response.ResponseFactory;
import com.tks.grocerymono.dto.request.bill.*;
import com.tks.grocerymono.dto.response.bill.*;
import com.tks.grocerymono.enums.BillStatus;
import com.tks.grocerymono.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/bill")
@RequiredArgsConstructor
@Slf4j
public class BillController {
    private final BillService billService;
    private final ResponseFactory responseFactory;

    @PostMapping
    ResponseEntity<BaseResponse<CreateBillResponse>> createBill(
            @RequestBody @Valid CreateBillRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {
        request.setAccessToken(token.substring(7));
        return responseFactory.success(billService.createBill(request));
    }

    @GetMapping("/all")
    ResponseEntity<BaseResponse<GetAllBillResponse>> getAllBill(
            @RequestParam(name = "status", required = false) List<BillStatus> billStatusList,
            @RequestParam(name = "page", required = false) Integer pageNumber,
            @RequestParam(name = "size", required = false) Integer pageSize,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {
        GetAllBillRequest request = GetAllBillRequest.builder()
                .accessToken(token.substring(7))
                .billStatusList(billStatusList)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();
        return responseFactory.success(billService.getAllBill(request));
    }

    @GetMapping("/bestSellingProduct")
    ResponseEntity<BaseResponse<GetBestSellingProductIdListResponse>> getBestSellingProductIdList(
            @RequestParam("recentDays") int recentDays,
            @RequestParam("size") int size
    ) {
        return responseFactory.success(billService.getBestSellingProductIdList(new GetBestSellingProductIdListRequest(recentDays, size)));
    }

    @GetMapping("/bestSellingProductQuantity")
    ResponseEntity<BaseResponse<GetBestSellingProductQuantityListResponse>> getBestSellingProductQuantityList(
            @RequestParam("recentDays") int recentDays,
            @RequestParam("size") int size
    ) {
        return responseFactory.success(billService.getBestSellingProductQuantityList(new GetBestSellingProductQuantityListRequest(recentDays, size)));
    }


    @GetMapping("/{id}")
    ResponseEntity<BaseResponse<GetBillByIdResponse>> getBillById(@PathVariable("id") int id) {
        return responseFactory.success(billService.getBillById(new GetBillByIdRequest(id)));
    }

    @GetMapping("/recommendedProduct")
    ResponseEntity<BaseResponse<GetRecommendedProductIdListResponse>> getRecommendedProductIdList(
            @RequestParam("recentDays") int recentDays,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestParam("size") int size
    ) {
        GetRecommendedProductIdListRequest request = GetRecommendedProductIdListRequest.builder()
                .recentDays(recentDays)
                .size(size)
                .accessToken(token.substring(7))
                .build();
        return responseFactory.success(billService.getRecommendedProductIdList(request));
    }


    @PutMapping("/status")
    ResponseEntity<BaseResponse<UpdateBillStatusResponse>> updateBillStatus(
            @RequestBody UpdateBillStatusRequest request,

            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {
        request.setAccessToken(token.substring(7));
        return responseFactory.success(billService.updateBillStatus(request));
    }
}
