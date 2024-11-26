package com.tks.grocerymono.dto.response.bill;

import com.tks.grocerymono.base.dto.response.CommandResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBillStatusResponse extends CommandResponseData {
    private BillResponse billResponse;
}
