package com.tks.grocerymono.dto.response.bill;

import com.tks.grocerymono.base.dto.response.QueryResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GetBillByIdResponse extends QueryResponseData {
    private BillResponse billResponse;
}
