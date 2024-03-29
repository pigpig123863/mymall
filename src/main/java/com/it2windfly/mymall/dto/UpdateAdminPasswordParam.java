package com.it2windfly.mymall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 修改用户名密码参数
 */
@Getter
@Setter
public class UpdateAdminPasswordParam {
    @ApiModelProperty(value = "用户名", required = true)
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @ApiModelProperty(value = "旧密码", required = true)
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;
    @ApiModelProperty(value = "新密码", required = true)
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;
	public CharSequence getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
	public CharSequence getOldPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	public CharSequence getNewPassword() {
		// TODO Auto-generated method stub
		return null;
	}
}

