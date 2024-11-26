package com.tks.grocerymono.client.keycloack.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.keycloak.representations.AccessTokenResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenClientResponse {
    private AccessTokenResponse accessTokenResponse;
}
