package com.mksoft.shop.parameter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by jianghe on 2017/6/14.
 */
@Data
@ApiModel("admin管理员登陆信息")
public class AdminLoginParameter {

    @ApiModelProperty("admin管理员登录用账号")
    private String adminUserLogin;

    @ApiModelProperty("admin管理员登录密码")
    private String adminUserPwd;

    public String getAdminUserLogin() {
        return adminUserLogin;
    }

    public void setAdminUserLogin(String adminUserLogin) {
        this.adminUserLogin = adminUserLogin;
    }

    public String getAdminUserPwd() {
        return adminUserPwd;
    }

    public void setAdminUserPwd(String adminUserPwd) {
        this.adminUserPwd = adminUserPwd;
    }
}
