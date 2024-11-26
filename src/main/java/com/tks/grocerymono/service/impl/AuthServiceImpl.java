package com.tks.grocerymono.service.impl;

import com.tks.grocerymono.base.constant.AccountRoles;
import com.tks.grocerymono.client.keycloack.IKeycloakClient;
import com.tks.grocerymono.client.keycloack.dto.request.RefreshTokenClientRequest;
import com.tks.grocerymono.client.keycloack.dto.response.RefreshTokenClientResponse;
import com.tks.grocerymono.dto.request.auth.*;
import com.tks.grocerymono.dto.response.auth.*;
import com.tks.grocerymono.enums.RealmRoles;
import com.tks.grocerymono.exception.BaseException;
import com.tks.grocerymono.exception.CommonErrorCode;
import com.tks.grocerymono.service.AuthService;
import com.tks.grocerymono.utils.JsonMapperUtil;
import com.tks.grocerymono.utils.JsonWebTokenUtil;
import com.tks.grocerymono.utils.KeycloakUtil;
import com.tks.grocerymono.utils.Mapper;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final KeycloakSpringBootProperties keycloakSpringBootProperties;
    private final ClientRegistration clientRegistration;
    private final KeycloakUtil keycloakUtil;
    private final IKeycloakClient keycloakClient;

    @Override
    public AdminLoginResponse adminLogin(AdminLoginRequest requestData) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakSpringBootProperties.getAuthServerUrl())
                .realm(keycloakSpringBootProperties.getRealm())
                .clientId(clientRegistration.getClientId())
                .clientSecret(clientRegistration.getClientSecret())
                .grantType(clientRegistration.getAuthorizationGrantType().getValue())
                .username(requestData.getUsername())
                .password(requestData.getPassword())
                .build();
        AccessTokenResponse accessTokenResponse = keycloak.tokenManager().grantToken();
        keycloak.close();
        List<String> roleList = JsonWebTokenUtil.getRoleList(accessTokenResponse.getToken());
        log.info("roles: {}", JsonMapperUtil.toString(roleList));
        boolean isStaffRole = roleList.stream().anyMatch(role -> role.equals(AccountRoles.ADMIN));
        if (isStaffRole) {
            return Mapper.map(accessTokenResponse, AdminLoginResponse.class);
        } else {
            return null;
        }
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest requestData) {
        String phoneNumber = JsonWebTokenUtil.getPhoneNumber(requestData.getAccessToken());
        this.customerLogin(new CustomerLoginRequest(phoneNumber, requestData.getOldPassword()));
        try (Keycloak adminKeycloak = keycloakUtil.getAdminKeycloakInstance()) {
            UserRepresentation userRepresentation = adminKeycloak
                    .realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .searchByUsername(phoneNumber, false)
                    .get(0);
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(requestData.getNewPassword());
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setCreatedDate(System.currentTimeMillis());

            userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

            adminKeycloak.realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .get(userRepresentation.getId())
                    .update(userRepresentation);
            return new ChangePasswordResponse(true);
        }
    }

    @Override
    public CustomerLoginResponse customerLogin(CustomerLoginRequest requestData) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakSpringBootProperties.getAuthServerUrl())
                .realm(keycloakSpringBootProperties.getRealm())
                .clientId(clientRegistration.getClientId())
                .clientSecret(clientRegistration.getClientSecret())
                .grantType(clientRegistration.getAuthorizationGrantType().getValue())
                .username(requestData.getPhoneNumber())
                .password(requestData.getPassword())
                .build();
        AccessTokenResponse accessTokenResponse = keycloak.tokenManager().getAccessToken();
        keycloak.close();
        return Mapper.map(accessTokenResponse, CustomerLoginResponse.class);
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest requestData) {
        try (Keycloak adminKeycloak = keycloakUtil.getAdminKeycloakInstance()) {
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(requestData.getPassword());
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setCreatedDate(System.currentTimeMillis());

            UserRepresentation userRepresentation = Mapper.map(requestData, UserRepresentation.class);
            userRepresentation.setUsername(requestData.getUsername());
            userRepresentation.setEnabled(true);
            userRepresentation.setEmailVerified(true);
            userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
            userRepresentation.setCreatedTimestamp(System.currentTimeMillis());

            try (Response response = adminKeycloak.realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .create(userRepresentation)) {
                log.info("Keycloak register user response: {}", response.getStatusInfo().getReasonPhrase());
                if (response.getStatus() == HttpStatus.CREATED.value()) {
                    UserRepresentation createdUserRepresentation = adminKeycloak.realm(keycloakSpringBootProperties.getRealm())
                            .users()
                            .searchByUsername(requestData.getUsername(), true)
                            .get(0);
                    adminKeycloak.realm(keycloakSpringBootProperties.getRealm())
                            .users()
                            .get(createdUserRepresentation.getId())
                            .roles()
                            .realmLevel()
                            .add(Collections.singletonList(adminKeycloak.realm(keycloakSpringBootProperties.getRealm())
                                    .roles()
                                    .get(requestData.getRealmRoles().getRole())
                                    .toRepresentation()));
                    UserResponse userResponse = Mapper.map(createdUserRepresentation, UserResponse.class);
                    userResponse.setRealmRoles(requestData.getRealmRoles());
                    return new CreateUserResponse(userResponse);
                } else {
                    throw new BaseException(CommonErrorCode.KEYCLOAK_OPERATION_ERROR);
                }
            }
        }
    }

    @Override
    public DeleteUserByIdResponse deleteUserById(DeleteUserByIdRequest requestData) {
        try (Keycloak adminKeycloak = keycloakUtil.getAdminKeycloakInstance()) {
            try (Response response = adminKeycloak.realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .delete(requestData.getId())) {
                log.info(String.valueOf(response.getStatus()));
                if (response.getStatus() == HttpStatus.NO_CONTENT.value()) {
                    return new DeleteUserByIdResponse(requestData.getId());
                } else {
                    throw new BaseException(CommonErrorCode.KEYCLOAK_OPERATION_ERROR);
                }
            }
        }
    }

    @Override
    public GetUserInfoResponse getUserInfo(GetUserInfoRequest requestData) {
        String phoneNumber = JsonWebTokenUtil.getPhoneNumber(requestData.getAccessToken());
        try (Keycloak adminKeycloak = keycloakUtil.getAdminKeycloakInstance()) {
            UserRepresentation userRepresentation = adminKeycloak
                    .realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .searchByUsername(phoneNumber, false)
                    .get(0);

            if (userRepresentation.getId() == null) {
                throw new BaseException(CommonErrorCode.USER_NOT_FOUND);
            }

            List<RoleRepresentation> userRoleRepresentationList = adminKeycloak
                    .realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .get(userRepresentation.getId())
                    .roles()
                    .realmLevel()
                    .listAll();
            RealmRoles userRole = userRoleRepresentationList
                    .stream()
                    .filter(roleRepresentation -> Arrays
                            .stream(RealmRoles.values())
                            .anyMatch(realmRoles -> realmRoles
                                    .getRole()
                                    .equals(roleRepresentation.getName())
                            ))
                    .map(roleRepresentation -> RealmRoles
                            .valueOf(roleRepresentation
                                    .getName()
                                    .toUpperCase())
                    )
                    .findAny()
                    .get();

            GetUserInfoResponse response = Mapper.map(userRepresentation, GetUserInfoResponse.class);
            response.setPhoneNumber(phoneNumber);
            response.setRealmRoles(userRole);
            return response;
        }
    }

    @Override
    public GetUserListByRoleResponse getUserListByRole(GetUserListByRoleRequest requestData) {
        try (Keycloak adminKeycloak = keycloakUtil.getAdminKeycloakInstance()) {
            List<UserRepresentation> userRepresentationList = adminKeycloak
                    .realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .list();
            List<UserResponse> userResponseList = userRepresentationList.stream()
                    .map(userRepresentation -> {
                        List<RoleRepresentation> userRoleRepresentationList = adminKeycloak
                                .realm(keycloakSpringBootProperties.getRealm())
                                .users()
                                .get(userRepresentation.getId())
                                .roles()
                                .realmLevel()
                                .listAll();
                        RealmRoles userRole = userRoleRepresentationList
                                .stream()
                                .filter(roleRepresentation -> Arrays
                                        .stream(RealmRoles.values())
                                        .anyMatch(realmRoles -> realmRoles
                                                .getRole()
                                                .equals(roleRepresentation.getName())
                                        ))
                                .map(roleRepresentation -> RealmRoles
                                        .valueOf(roleRepresentation
                                                .getName()
                                                .toUpperCase())
                                )
                                .findAny()
                                .get();
                        UserResponse userResponse = Mapper.map(userRepresentation, UserResponse.class);
                        userResponse.setRealmRoles(userRole);
                        return userResponse;
                    })
                    .filter(userResponse -> userResponse.getRealmRoles().equals(requestData.getRole()))
                    .toList();
            return new GetUserListByRoleResponse(userResponseList);
        }
    }


    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest requestData) {
        RefreshTokenClientRequest clientRequest = new RefreshTokenClientRequest(requestData.getRefreshToken());
        RefreshTokenClientResponse clientResponse = keycloakClient.refreshToken(clientRequest);
        AccessTokenResponse accessTokenResponse = clientResponse.getAccessTokenResponse();
        return Mapper.map(accessTokenResponse, RefreshTokenResponse.class);
    }

    @Override
    public RegisterCustomerResponse registerCustomer(RegisterCustomerRequest requestData) {
        try (Keycloak adminKeycloak = keycloakUtil.getAdminKeycloakInstance()) {
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(requestData.getPassword());
            credentialRepresentation.setTemporary(false);
            credentialRepresentation.setCreatedDate(System.currentTimeMillis());

            UserRepresentation userRepresentation = Mapper.map(requestData, UserRepresentation.class);
            userRepresentation.setUsername(requestData.getPhoneNumber());
            userRepresentation.setEnabled(true);
            userRepresentation.setEmailVerified(true);
            userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
            userRepresentation.setCreatedTimestamp(System.currentTimeMillis());

            try (Response response = adminKeycloak.realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .create(userRepresentation)) {
                log.info("Keycloak register user response status: {}", response.getStatus());
                String createdUserId = adminKeycloak.realm(keycloakSpringBootProperties.getRealm())
                        .users()
                        .searchByUsername(requestData.getPhoneNumber(), true)
                        .get(0)
                        .getId();
                adminKeycloak.realm(keycloakSpringBootProperties.getRealm())
                        .users()
                        .get(createdUserId)
                        .roles()
                        .realmLevel()
                        .add(Collections.singletonList(adminKeycloak.realm(keycloakSpringBootProperties.getRealm())
                                .roles()
                                .get(RealmRoles.CUSTOMER.getRole())
                                .toRepresentation()));
                if (response.getStatus() == HttpStatus.CREATED.value()) {
                    return RegisterCustomerResponse.builder()
                            .registeredPhoneNumber(requestData.getPhoneNumber())
                            .build();
                }
            }
        }
        return null;
    }

    @Override
    public StaffLoginResponse staffLogin(StaffLoginRequest requestData) {
        Keycloak keycloak = KeycloakBuilder.builder()
                .serverUrl(keycloakSpringBootProperties.getAuthServerUrl())
                .realm(keycloakSpringBootProperties.getRealm())
                .clientId(clientRegistration.getClientId())
                .clientSecret(clientRegistration.getClientSecret())
                .grantType(clientRegistration.getAuthorizationGrantType().getValue())
                .username(requestData.getUsername())
                .password(requestData.getPassword())
                .build();
        AccessTokenResponse accessTokenResponse = keycloak.tokenManager().grantToken();
        keycloak.close();
        List<String> roleList = JsonWebTokenUtil.getRoleList(accessTokenResponse.getToken());
        log.info("roles: {}", JsonMapperUtil.toString(roleList));
        boolean isStaffRole = roleList.stream().anyMatch(role -> role.equals(AccountRoles.STAFF));
        if (isStaffRole) {
            return Mapper.map(accessTokenResponse, StaffLoginResponse.class);
        } else {
            return null;
        }
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest requestData) {
        try (Keycloak adminKeycloak = keycloakUtil.getAdminKeycloakInstance()) {

            UserRepresentation userRepresentation = adminKeycloak
                    .realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .get(requestData.getId())
                    .toRepresentation();

            userRepresentation.setFirstName(requestData.getFirstName());
            userRepresentation.setLastName(requestData.getLastName());
            userRepresentation.setEmail(requestData.getEmail());
            adminKeycloak.realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .get(userRepresentation.getId())
                    .update(userRepresentation);

            List<RoleRepresentation> oldRoleRepresentationList = adminKeycloak
                    .realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .get(userRepresentation.getId())
                    .roles()
                    .realmLevel()
                    .listAll();

            adminKeycloak
                    .realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .get(userRepresentation.getId())
                    .roles()
                    .realmLevel()
                    .remove(oldRoleRepresentationList);

            oldRoleRepresentationList.removeIf(roleRepresentation -> Arrays
                    .stream(RealmRoles.values())
                    .anyMatch(realmRoles -> realmRoles
                            .getRole()
                            .equals(roleRepresentation.getName())
                    ));

            oldRoleRepresentationList.add(adminKeycloak.realm(keycloakSpringBootProperties.getRealm())
                    .roles()
                    .get(requestData.getRealmRoles().getRole())
                    .toRepresentation());

            adminKeycloak
                    .realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .get(userRepresentation.getId())
                    .roles()
                    .realmLevel()
                    .add(oldRoleRepresentationList);

            UserResponse userResponse = Mapper.map(userRepresentation, UserResponse.class);
            userResponse.setRealmRoles(requestData.getRealmRoles());
            return new UpdateUserResponse(userResponse);
        }
    }

    @Override
    public UpdateUserInfoResponse updateUserInfo(UpdateUserInfoRequest requestData) {
        String phoneNumber = JsonWebTokenUtil.getPhoneNumber(requestData.getAccessToken());
        try (Keycloak adminKeycloak = keycloakUtil.getAdminKeycloakInstance()) {

            UserRepresentation userRepresentation = adminKeycloak
                    .realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .searchByUsername(phoneNumber, false)
                    .get(0);
            userRepresentation.setFirstName(requestData.getFirstName());
            userRepresentation.setLastName(requestData.getLastName());
            userRepresentation.setEmail(requestData.getEmail());

            adminKeycloak.realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .get(userRepresentation.getId())
                    .update(userRepresentation);
            UserRepresentation updatedUserRepresentation = adminKeycloak
                    .realm(keycloakSpringBootProperties.getRealm())
                    .users()
                    .get(userRepresentation.getId())
                    .toRepresentation();
            return Mapper.map(updatedUserRepresentation, UpdateUserInfoResponse.class);
        }
    }
}
