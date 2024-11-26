package com.tks.grocerymono.dto.request.bill;

import com.tks.grocerymono.base.dto.request.CommandRequestData;
import com.tks.grocerymono.enums.BillStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBillStatusRequest extends CommandRequestData {
    private int billId;
    private BillStatus billStatus;
}
