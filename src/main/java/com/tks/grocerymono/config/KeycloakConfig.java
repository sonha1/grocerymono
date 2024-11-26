package com.tks.grocerymono.config;

import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class KeycloakConfig {

    private static final String keycloakRegistrationId = "keycloak-login";
    private static final String keycloakProviderId = "keycloak";

    @Bean
    public KeycloakSpringBootProperties keycloakSpringBootProperties() {
        return new KeycloakSpringBootProperties();
    }

    @Bean
    public ClientRegistration clientRegistration(ClientRegistrationRepository clientRegistrationRepository) {
        return clientRegistrationRepository.findByRegistrationId(keycloakRegistrationId);
    }

    @Bean
    public OAuth2ClientProperties.Provider clientProvider(OAuth2ClientProperties properties) {
        return properties.getProvider().get(keycloakProviderId);
    }

}
