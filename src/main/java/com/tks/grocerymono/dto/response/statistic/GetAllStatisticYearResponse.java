package com.tks.grocerymono.dto.response.statistic;

import com.tks.grocerymono.base.dto.response.QueryResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GetAllStatisticYearResponse extends QueryResponseData {
    private List<Integer> yearList;
}
