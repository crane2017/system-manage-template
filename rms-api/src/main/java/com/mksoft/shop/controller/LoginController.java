package com.mksoft.shop.controller;

import com.alibaba.fastjson.JSONObject;
import com.mksoft.shop.exception.LoginFailedException;
import com.mksoft.shop.exception.NoSuchUserException;
import com.mksoft.shop.parameter.AdminLoginParameter;
import com.mksoft.shop.service.LoginService;
import com.mksoft.shop.service.bo.ResultBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@RestController
@RequestMapping("/login")
@Api(tags = " LoginController", description = "系统登录")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "后台管理登录", httpMethod = "POST", response = JSONObject.class, notes = "")
    @RequestMapping(value = "admin", method = RequestMethod.POST)
    public ResultBo loginAdmin(@RequestBody AdminLoginParameter param) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, LoginFailedException {
        return loginService.loginAdmin(param);
}

    @ApiOperation(value = "重置Admin密码", httpMethod = "POST", response = JSONObject.class, notes = "")
    @RequestMapping(value = "admin/reset-pwd", method = RequestMethod.POST)
    public ResultBo resetAdminPwd(@RequestBody final Map param) throws InvocationTargetException, IllegalAccessException, NoSuchUserException {
        return loginService.resetAdminPwd(param);
    }


    @ApiOperation(value = "发送SMS验证码", httpMethod = "POST", response = JSONObject.class, notes = "")
    @RequestMapping(value = "app/send-sms-verification-code", method = RequestMethod.POST)
    public ResultBo sendSmsVerificationCode(@RequestBody final Map param) {
        return loginService.sendSmsVerificationCode(param);
    }
}
