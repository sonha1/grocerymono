package com.tks.grocerymono.service;

import com.tks.grocerymono.dto.request.bill.*;
import com.tks.grocerymono.dto.response.bill.*;

public interface BillService {
    GetBestSellingProductIdListResponse getBestSellingProductIdList(GetBestSellingProductIdListRequest requestData);

    public GetBestSellingProductQuantityListResponse getBestSellingProductQuantityList(GetBestSellingProductQuantityListRequest requestData);

    public GetRecommendedProductIdListResponse getRecommendedProductIdList(GetRecommendedProductIdListRequest requestData);

    UpdateBillStatusResponse updateBillStatus(UpdateBillStatusRequest requestData);

    public GetAllBillResponse getAllBill(GetAllBillRequest requestData);

    public CreateBillResponse createBill(CreateBillRequest requestData);

    GetBillByIdResponse getBillById(GetBillByIdRequest requestData);
}

