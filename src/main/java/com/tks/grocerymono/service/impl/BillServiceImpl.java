package com.tks.grocerymono.service.impl;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import com.tks.grocerymono.base.constant.AccountRoles;
import com.tks.grocerymono.config.properties.StripeProperties;
import com.tks.grocerymono.dto.request.bill.*;
import com.tks.grocerymono.dto.request.cart.DeleteAndGetCartByIdListRequest;
import com.tks.grocerymono.dto.request.product.DeductQuantityListProductRequest;
import com.tks.grocerymono.dto.request.product.DeductingProduct;
import com.tks.grocerymono.dto.response.bill.*;
import com.tks.grocerymono.dto.response.cart.DeleteAndGetCartByIdListResponse;
import com.tks.grocerymono.dto.response.cart.InternalCartResponse;
import com.tks.grocerymono.dto.response.product.DeductQuantityListProductResponse;
import com.tks.grocerymono.entity.Bill;
import com.tks.grocerymono.entity.BillItem;
import com.tks.grocerymono.enums.BillStatus;
import com.tks.grocerymono.enums.PickUpDateEnum;
import com.tks.grocerymono.exception.BaseException;
import com.tks.grocerymono.exception.CommonErrorCode;
import com.tks.grocerymono.repository.BillRepository;
import com.tks.grocerymono.repository.StatisticRepository;
import com.tks.grocerymono.service.BillService;
import com.tks.grocerymono.service.CartService;
import com.tks.grocerymono.service.ProductService;
import com.tks.grocerymono.utils.BillUtil;
import com.tks.grocerymono.utils.JsonWebTokenUtil;
import com.tks.grocerymono.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final StatisticRepository statisticRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final BillUtil billUtil;
    private final StripeProperties stripeProperties;

    @Override
    public GetBestSellingProductIdListResponse getBestSellingProductIdList(GetBestSellingProductIdListRequest requestData) {
        List<Integer> productIdList = billRepository.getBestSellingProductIdList(
                requestData.getRecentDays(),
                requestData.getSize()
        );
        return new GetBestSellingProductIdListResponse(productIdList);
    }

    @Override
    public GetRecommendedProductIdListResponse getRecommendedProductIdList(GetRecommendedProductIdListRequest requestData) {
        String customerId = JsonWebTokenUtil.getUserId(requestData.getAccessToken());
        List<Integer> productIdList = billRepository.getRecommendedProductIdList(
                requestData.getRecentDays(),
                customerId,
                requestData.getSize()
        );
        return new GetRecommendedProductIdListResponse(productIdList);
    }

    @Override
    public GetBestSellingProductQuantityListResponse getBestSellingProductQuantityList(GetBestSellingProductQuantityListRequest requestData) {
        List<BestSellingProductQuantityResponse> productQuantityResponseList = statisticRepository.getBestSellingProductQuantityList(
                requestData.getRecentDays(),
                requestData.getSize()
        );
        return new GetBestSellingProductQuantityListResponse(productQuantityResponseList);
    }

    @Override
    public GetBillByIdResponse getBillById(GetBillByIdRequest requestData) {
        Bill bill = billRepository.findById(requestData.getId()).orElseThrow(NoSuchElementException::new);
        BillResponse billResponse = billUtil.mapBillListToBillResponseList(List.of(bill)).get(0);
        return new GetBillByIdResponse(billResponse);
    }


    @Transactional(
            rollbackFor = {Exception.class},
            propagation = Propagation.REQUIRED
    )
    @Override
    public CreateBillResponse createBill(CreateBillRequest requestData) {

        // DELETE cart item
        DeleteAndGetCartByIdListRequest deleteCartItemByIdListRequest = new DeleteAndGetCartByIdListRequest(requestData.getCartIdList());
        DeleteAndGetCartByIdListResponse deleteAndGetCartByIdListResponse = cartService.deleteAndGetCartByIdList(deleteCartItemByIdListRequest);

        // DEDUCT product quantity
        List<DeductingProduct> deductingProductList = deleteAndGetCartByIdListResponse.getInternalCartResponseList()
                .stream()
                .map(internalCartResponse -> DeductingProduct
                        .builder()
                        .productId(internalCartResponse.getProductId())
                        .deductingQuantity(internalCartResponse.getQuantity())
                        .build())
                .toList();
        DeductQuantityListProductRequest productClientRequest = new DeductQuantityListProductRequest(deductingProductList);
        DeductQuantityListProductResponse deductQuantityListProductResponse = productService.deductQuantityListProduct(productClientRequest);

        // PREPARE bill
        int billTotalPrice = deleteAndGetCartByIdListResponse.getInternalCartResponseList()
                .stream()
                .mapToInt(InternalCartResponse::getTotalPrice)
                .sum();
        Bill bill = Bill.builder()
                .customerId(JsonWebTokenUtil.getUserId(requestData.getAccessToken()))
                .status(BillStatus.PAID)
                .totalPrice(billTotalPrice)
                .pickUpTime(getPickUpTime(requestData))
                .stripePaymentId(requestData.getPaymentIntentId())
                .build();
        List<BillItem> billItemList = deleteAndGetCartByIdListResponse.getInternalCartResponseList()
                .stream()
                .map(internalCartResponse -> BillItem.builder()
                        .productId(internalCartResponse.getProductId())
                        .quantity(internalCartResponse.getQuantity())
                        .price(internalCartResponse.getTotalPrice())
                        .bill(bill)
                        .build())
                .toList();
        bill.setBillItemList(billItemList);

        // SAVE bill
        Bill savedBill = billRepository.save(bill);

        // CREATE bill response
        List<BillItemResponse> billItemResponseList = Mapper.mapList(savedBill.getBillItemList(), BillItemResponse.class);
        BillResponse billResponse = Mapper.map(savedBill, BillResponse.class);
        billResponse.setBillItemResponseList(billItemResponseList);
        return new CreateBillResponse(billResponse);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    private LocalDateTime getPickUpTime(CreateBillRequest requestData) {
        int arrivalDay = Arrays.stream(PickUpDateEnum.values())
                .filter(pickUpDateEnum -> pickUpDateEnum.getDescription().equals(requestData.getPickUpDate()))
                .mapToInt(PickUpDateEnum::getArrivalDay)
                .findFirst()
                .getAsInt();
        LocalDate pickUpDate = LocalDate.now().plusDays(arrivalDay);

        int indexOfColon = requestData.getPickUpTime().indexOf(":");
        int pickUpHour = Integer.parseInt(requestData.getPickUpTime().substring(0, indexOfColon));
        int pickUpMinute = Integer.parseInt(requestData.getPickUpTime().substring(indexOfColon + 1));
        LocalTime pickUpTime = LocalTime.of(pickUpHour, pickUpMinute);

        return LocalDateTime.of(pickUpDate, pickUpTime);
    }


    @Override
    public GetAllBillResponse getAllBill(GetAllBillRequest requestData) {
        String userId = JsonWebTokenUtil.getUserId(requestData.getAccessToken());
        boolean isCustomerRole = JsonWebTokenUtil.getRoleList(requestData.getAccessToken())
                .stream()
                .anyMatch(role -> role.equals(AccountRoles.CUSTOMER));
        int pageIndex = requestData.getPageNumber() - 1;
        List<Bill> billList;
        if (isCustomerRole) {
            Pageable pageable = PageRequest.of(
                    pageIndex,
                    requestData.getPageSize(),
                    Sort.by(Sort.Direction.DESC, "createdDate")
            );
            if (requestData.getBillStatusList() == null) {
                billList = billRepository.findByCustomerId(userId, pageable);
            } else {
                Pageable statusPageable = requestData.getBillStatusList().contains(BillStatus.PAID)
                        ?
                        pageable
                        :
                        PageRequest.of(
                                pageIndex,
                                requestData.getPageSize(),
                                Sort.by(Sort.Direction.DESC, "lastModifiedDate")
                        );
                billList = billRepository.findByCustomerIdAndStatusIn(userId, requestData.getBillStatusList(), statusPageable);
            }
        } else {
            Sort sort = requestData.getBillStatusList().contains(BillStatus.PAID)
                    ?
                    Sort.by(Sort.Direction.ASC, "pickUpTime")
                    :
                    Sort.by(Sort.Direction.DESC, "lastModifiedDate");
            Pageable pageable = PageRequest.of(pageIndex, requestData.getPageSize(), sort);
            billList = billRepository.findByStatusIn(requestData.getBillStatusList(), pageable);
        }
        List<BillResponse> billResponseList = billUtil.mapBillListToBillResponseList(billList);
        return new GetAllBillResponse(billResponseList);
    }

    @Transactional
    @Override
    public UpdateBillStatusResponse updateBillStatus(UpdateBillStatusRequest requestData) {
        try {
            String staffId = JsonWebTokenUtil.getUserId(requestData.getAccessToken());
            Bill bill = billRepository.findById(requestData.getBillId()).get();
            if (bill.getStripePaymentId() != null && requestData.getBillStatus() == BillStatus.CANCELLED) {
                refundStripePayment(bill.getStripePaymentId());
            }
            bill.setStatus(requestData.getBillStatus());
            bill.setStaffId(staffId);
            bill = billRepository.save(bill);
            BillResponse billResponse = billUtil.mapBillToBillResponse(bill);
            return new UpdateBillStatusResponse(billResponse);
        } catch (StripeException exception) {
            throw new BaseException(CommonErrorCode.STRIPE_PAYMENT_ERROR);
        }
    }

    private void refundStripePayment(String paymentIntentId) throws StripeException {
        Stripe.apiKey = stripeProperties.getApiKey();
        RefundCreateParams params = RefundCreateParams.builder().setPaymentIntent(paymentIntentId).build();
        Refund.create(params);
    }
}
