package com.tks.grocerymono.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tks.grocerymono.enums.RealmRoles;
import com.tks.grocerymono.base.dto.response.QueryResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class GetUserInfoResponse extends QueryResponseData {
    private String id;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    @JsonProperty("role")
    private RealmRoles realmRoles;
}
