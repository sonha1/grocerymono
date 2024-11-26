package com.tks.grocerymono.dto.request.statistic;

import com.tks.grocerymono.base.dto.request.QueryRequestData;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GetMonthlyStatisticByYearRequest extends QueryRequestData {
    private int year;
}
