package com.tks.grocerymono.dto.request.auth;

import com.tks.grocerymono.base.dto.request.QueryRequestData;;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class StaffLoginRequest extends QueryRequestData {
    private String username;
    private String password;
}