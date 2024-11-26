package com.tks.grocerymono.dto.response.statistic;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatisticResponse {
    @SerializedName("time")
    private String time;

    @SerializedName("revenue")
    private int revenue;
}
