package com.tks.grocerymono.config;

import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain permitAllFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> registry.requestMatchers(
                        "/socket.io/**",
                        "/api/auth/adminLogin",
                        "/api/auth/login",
                        "/api/auth/staffLogin",
                        "/api/auth/registerCustomer",
                        "/api/auth/refreshToken").permitAll())
                .build();

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers(
                                "/api/cart/**",
                                "/api/payment/**",
                                "/api/auth/password",
                                "/api/auth/userInfo",
                                "/api/notify/device/**").hasRole("CUSTOMER")
                        .requestMatchers("/api/staff/bill/status").hasRole("STAFF")
                        .requestMatchers(
                                "/api/statistic/**",
                                "/api/auth/admin/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/api/product/**",
                                "/api/category/**").hasAnyRole("CUSTOMER", "ADMIN")
                        .requestMatchers("/api/bill/**").hasAnyRole("CUSTOMER", "STAFF")
                        .anyRequest().denyAll()
                )
                .oauth2ResourceServer(oAuth2 -> oAuth2
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(jwtConverter())))
                .build();
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://your-jwk-set-uri").build();
    }

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new CustomRolesConverter());
        return jwtAuthenticationConverter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = new KeycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(keycloakAuthenticationProvider)
                .build();
    }
}
