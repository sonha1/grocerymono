package com.tks.grocerymono.dto.response.bill;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BestSellingProductQuantityResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("quantity")
    private int quantity;
}
