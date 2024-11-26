package com.tks.grocerymono.dto.response.category;

import com.tks.grocerymono.base.dto.response.CommandResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryResponse extends CommandResponseData {
    private CategoryResponse categoryResponse;
}
