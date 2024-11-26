package com.tks.grocerymono.dto.request.bill;

import com.tks.grocerymono.base.dto.request.QueryRequestData;
import com.tks.grocerymono.enums.BillStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GetAllBillRequest extends QueryRequestData {
    private List<BillStatus> billStatusList;
    private Integer pageNumber;
    private Integer pageSize;
}
