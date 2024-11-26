package com.tks.grocerymono.client.keycloack;

import com.tks.grocerymono.client.keycloack.dto.request.RefreshTokenClientRequest;
import com.tks.grocerymono.client.keycloack.dto.response.RefreshTokenClientResponse;

public interface IKeycloakClient {
    RefreshTokenClientResponse refreshToken(RefreshTokenClientRequest request);
}
