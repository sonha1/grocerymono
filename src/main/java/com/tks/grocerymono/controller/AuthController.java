package com.tks.grocerymono.controller;


import com.tks.grocerymono.base.dto.response.BaseResponse;
import com.tks.grocerymono.base.dto.response.ResponseFactory;
import com.tks.grocerymono.dto.request.auth.*;
import com.tks.grocerymono.dto.response.auth.*;
import com.tks.grocerymono.enums.RealmRoles;
import com.tks.grocerymono.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final ResponseFactory responseFactory;


    @PostMapping("/admin-login")
    ResponseEntity<BaseResponse<AdminLoginResponse>> adminLogin(@RequestBody AdminLoginRequest request) {
        return responseFactory.success(authService.adminLogin(request));
    }

    @PutMapping("/password")
    ResponseEntity<BaseResponse<ChangePasswordResponse>> changePassword(
            @RequestBody ChangePasswordRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {
        request.setAccessToken(token.substring(7));
        return responseFactory.success(authService.changePassword(request));
    }

    @PostMapping("/admin/user")
    ResponseEntity<BaseResponse<CreateUserResponse>> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return responseFactory.success(authService.createUser(createUserRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<CustomerLoginResponse>> login(@RequestBody CustomerLoginRequest customerLoginRequest) {
        return responseFactory.success(authService.customerLogin(customerLoginRequest));
    }

    @DeleteMapping("/admin/user/{id}")
    ResponseEntity<BaseResponse<DeleteUserByIdResponse>> deleteUserById(@PathVariable("id") String id) {
        return responseFactory.success(authService.deleteUserById(new DeleteUserByIdRequest(id)));
    }

    @GetMapping("/userInfo")
    public ResponseEntity<BaseResponse<GetUserInfoResponse>> getUserInfo(@RequestHeader(name = "Authorization") String authorizationHeaderValue) {
        GetUserInfoRequest request = new GetUserInfoRequest();
        request.setAccessToken(authorizationHeaderValue.substring(7));
        return responseFactory.success(authService.getUserInfo(request));
    }

    @GetMapping("/admin/user")
    ResponseEntity<BaseResponse<GetUserListByRoleResponse>> getUserListByRole(
            @RequestParam("role") RealmRoles role
    ) {
        return responseFactory.success(authService.getUserListByRole(new GetUserListByRoleRequest(role)));
    }

    @PostMapping("/refreshToken")
    ResponseEntity<BaseResponse<RefreshTokenResponse>> refreshToken(
            @RequestHeader("refresh_token") String refreshToken
    ) {
        return responseFactory.success(authService.refreshToken(new RefreshTokenRequest(refreshToken)));
    }

    @PostMapping("/registerCustomer")
    public ResponseEntity<BaseResponse<RegisterCustomerResponse>> registerCustomer(@RequestBody RegisterCustomerRequest request) {
        return responseFactory.success(authService.registerCustomer(request));
    }

    @PostMapping("/staffLogin")
    ResponseEntity<BaseResponse<StaffLoginResponse>> staffLogin(@RequestBody StaffLoginRequest request) {
        return responseFactory.success(authService.staffLogin(request));
    }

    @PutMapping("/admin/user")
    ResponseEntity<BaseResponse<UpdateUserResponse>> updateUser(@RequestBody UpdateUserRequest request) {
        return responseFactory.success(authService.updateUser(request));
    }

    @PutMapping("/userInfo")
    ResponseEntity<BaseResponse<UpdateUserInfoResponse>> updateUserInfo(
            @RequestBody UpdateUserInfoRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {
        request.setAccessToken(token.substring(7));
        return responseFactory.success(authService.updateUserInfo(request));
    }
}
