package com.tks.grocerymono.client.keycloack;

import com.tks.grocerymono.client.keycloack.dto.request.RefreshTokenClientRequest;
import com.tks.grocerymono.client.keycloack.dto.response.RefreshTokenClientResponse;
import com.tks.grocerymono.utils.JsonMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeycloakClient implements IKeycloakClient {

    private final RestClient.Builder restClientBuilder;
    private final OAuth2ClientProperties.Provider clientProvider;
    private final ClientRegistration clientRegistration;

    @Override
    public RefreshTokenClientResponse refreshToken(RefreshTokenClientRequest request) {
        String keycloakTokenUri = clientProvider.getTokenUri();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientRegistration.getClientId());
        map.add("client_secret", clientRegistration.getClientSecret());
        map.add("grant_type", "refresh_token");
        map.add("refresh_token", request.getRefreshToken());
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(keycloakTokenUri)
                .build();
        log.info("REQUEST to uri {}", uriComponents.toUri());
        AccessTokenResponse accessTokenResponse = restClientBuilder
                .build()
                .post()
                .uri(uriComponents.toUri())
                .body(map)
                .retrieve()
                .body(AccessTokenResponse.class);
        log.info("RESPONSE from uri {} with body: {}", uriComponents.toUri(), JsonMapperUtil.toString(accessTokenResponse));
        return new RefreshTokenClientResponse(accessTokenResponse);
    }
}
