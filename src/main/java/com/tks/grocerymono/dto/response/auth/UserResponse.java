package com.tks.grocerymono.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tks.grocerymono.enums.RealmRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    @JsonProperty("role")
    private RealmRoles realmRoles;
}
