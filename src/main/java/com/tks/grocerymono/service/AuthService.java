package com.tks.grocerymono.service;

import com.tks.grocerymono.dto.request.auth.*;
import com.tks.grocerymono.dto.response.auth.*;

public interface AuthService {
    AdminLoginResponse adminLogin(AdminLoginRequest requestData);
    ChangePasswordResponse changePassword(ChangePasswordRequest requestData);
    CreateUserResponse createUser(CreateUserRequest requestData);
    CustomerLoginResponse customerLogin(CustomerLoginRequest requestData);
    DeleteUserByIdResponse deleteUserById(DeleteUserByIdRequest requestData);
    GetUserInfoResponse getUserInfo(GetUserInfoRequest requestData);
    GetUserListByRoleResponse getUserListByRole(GetUserListByRoleRequest requestData);
    RefreshTokenResponse refreshToken(RefreshTokenRequest requestData);
    RegisterCustomerResponse registerCustomer(RegisterCustomerRequest requestData);
    StaffLoginResponse staffLogin(StaffLoginRequest requestData);
    UpdateUserResponse updateUser(UpdateUserRequest requestData);
    UpdateUserInfoResponse updateUserInfo(UpdateUserInfoRequest requestData);
}
