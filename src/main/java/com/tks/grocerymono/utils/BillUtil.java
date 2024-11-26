package com.tks.grocerymono.utils;


import com.tks.grocerymono.dto.request.product.GetProductByIdListRequest;
import com.tks.grocerymono.dto.response.bill.BillItemResponse;
import com.tks.grocerymono.dto.response.bill.BillResponse;
import com.tks.grocerymono.dto.response.product.GetProductByIdListResponse;
import com.tks.grocerymono.dto.response.product.ProductResponse;
import com.tks.grocerymono.entity.Bill;
import com.tks.grocerymono.entity.BillItem;
import com.tks.grocerymono.repository.BillRepository;
import com.tks.grocerymono.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BillUtil {

    private final BillRepository billRepository;
    private final ProductService productService;

    public List<BillResponse> mapBillListToBillResponseList(List<Bill> billList) {
        List<Integer> productIdList = new ArrayList<>();
        billList.forEach(bill -> {
            List<Integer> productIdBillItemList = bill.getBillItemList()
                    .stream()
                    .map(BillItem::getProductId)
                    .toList();
            productIdList.addAll(productIdBillItemList);
        });
        List<Integer> distinctProductIdList = productIdList.stream()
                .distinct()
                .toList();
        GetProductByIdListRequest clientRequest = new GetProductByIdListRequest(distinctProductIdList);
        GetProductByIdListResponse clientResponse = productService.getProductByIdList(clientRequest);

        return billList
                .stream()
                .map(bill -> {
                    BillResponse billResponse = Mapper.map(bill, BillResponse.class);
                    billResponse.setPickUpTime(DateTimeFormatUtil.format(
                            bill.getPickUpTime(),
                            DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm_COLON_ss
                    ));
                    billResponse.setCreatedDate(DateTimeFormatUtil.format(
                            bill.getCreatedDate(),
                            DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm_COLON_ss
                    ));
                    List<BillItemResponse> billItemResponseList = bill.getBillItemList()
                            .stream()
                            .map(billItem -> {
                                ProductResponse productResponse = clientResponse.getProductResponseList()
                                        .stream()
                                        .filter(product -> Objects.equals(product.getId(), billItem.getProductId()))
                                        .findFirst()
                                        .orElse(new ProductResponse());
                                return BillItemResponse.builder()
                                        .quantity(billItem.getQuantity())
                                        .price(billItem.getPrice())
                                        .productResponse(productResponse)
                                        .build();
                            })
                            .toList();
                    billResponse.setBillItemResponseList(billItemResponseList);
                    return billResponse;
                })
                .toList();
    }

    public BillResponse mapBillToBillResponse(Bill bill) {
        BillResponse billResponse = Mapper.map(bill, BillResponse.class);
        billResponse.setPickUpTime(DateTimeFormatUtil.format(
                bill.getPickUpTime(),
                DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm_COLON_ss
        ));
        billResponse.setCreatedDate(DateTimeFormatUtil.format(
                bill.getCreatedDate(),
                DateTimeFormat.dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm_COLON_ss
        ));
        return billResponse;
    }
}
