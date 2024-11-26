package com.tks.grocerymono.dto.request.auth;

import com.tks.grocerymono.base.dto.request.QueryRequestData;;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
public class GetUserInfoRequest extends QueryRequestData {
}
