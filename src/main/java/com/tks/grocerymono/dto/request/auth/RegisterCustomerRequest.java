package com.tks.grocerymono.dto.request.auth;

import com.tks.grocerymono.base.dto.request.CommandRequestData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCustomerRequest extends CommandRequestData {
    private String phoneNumber;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
