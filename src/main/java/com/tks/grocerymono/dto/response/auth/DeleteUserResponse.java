package com.tks.grocerymono.dto.response.auth;

import com.tks.grocerymono.base.dto.response.CommandResponseData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class DeleteUserResponse extends CommandResponseData {
}
