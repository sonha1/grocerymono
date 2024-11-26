package com.tks.grocerymono.dto.request.bill;

import com.tks.grocerymono.base.dto.request.QueryRequestData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GetBillByIdRequest extends QueryRequestData {
    private int id;
}
