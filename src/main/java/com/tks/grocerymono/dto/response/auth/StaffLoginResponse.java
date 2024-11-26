package com.tks.grocerymono.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tks.grocerymono.base.dto.response.QueryResponseData;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class StaffLoginResponse extends QueryResponseData {
    private long refreshExpiresIn;
    private String refreshToken;
    @JsonProperty("accessToken")
    private String token;
    private long expiresIn;
    private String tokenType;
}
