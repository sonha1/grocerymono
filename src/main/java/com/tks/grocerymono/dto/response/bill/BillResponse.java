package com.tks.grocerymono.dto.response.bill;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tks.grocerymono.enums.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillResponse {
    private Integer id;
    private Integer totalPrice;
    private String customerId;
    private String staffId;
    private BillStatus status;
    private String pickUpTime;
    private String createdDate;
    @JsonProperty("billItems")
    private List<BillItemResponse> billItemResponseList;
}
