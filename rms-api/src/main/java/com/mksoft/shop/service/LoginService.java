package com.mksoft.shop.service;

import com.mksoft.shop.exception.LoginFailedException;
import com.mksoft.shop.exception.NoSuchUserException;
import com.mksoft.shop.parameter.AdminLoginParameter;
import com.mksoft.shop.service.bo.ResultBo;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface LoginService {
    ResultBo loginAdmin(AdminLoginParameter param) throws LoginFailedException;
    ResultBo resetAdminPwd(Map param) throws InvocationTargetException, IllegalAccessException, NoSuchUserException;
    ResultBo sendSmsVerificationCode(Map param);

}
