package com.tks.grocerymono.dto.response.auth;

import com.tks.grocerymono.base.dto.response.CommandResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInfoResponse extends CommandResponseData {
    private String id;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
}
