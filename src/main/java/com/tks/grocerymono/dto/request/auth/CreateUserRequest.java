package com.tks.grocerymono.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tks.grocerymono.enums.RealmRoles;
import com.tks.grocerymono.base.dto.request.CommandRequestData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest extends CommandRequestData {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    @JsonProperty("role")
    private RealmRoles realmRoles;
}
