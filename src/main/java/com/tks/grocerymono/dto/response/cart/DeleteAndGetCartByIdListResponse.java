package com.tks.grocerymono.dto.response.cart;

import com.tks.grocerymono.base.dto.response.CommandResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class DeleteAndGetCartByIdListResponse extends CommandResponseData {
    private List<InternalCartResponse> internalCartResponseList;
}