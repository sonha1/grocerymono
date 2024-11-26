package com.tks.grocerymono.dto.request.auth;


import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest extends com.tks.grocerymono.base.dto.request.CommandRequestData {
    private String oldPassword;
    private String newPassword;
}
