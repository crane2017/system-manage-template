package com.mksoft.shop.service;

import com.mksoft.shop.parameter.EditAdminUserParameter;
import com.mksoft.shop.service.bo.ResultBo;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface AdminUserService {
    ResultBo listUser(Map user);

    ResultBo insertUser(Map user) throws InvocationTargetException, IllegalAccessException;

    ResultBo updateUser(Map user) throws InvocationTargetException, IllegalAccessException;

    ResultBo updateUserPassword(EditAdminUserParameter user) throws InvocationTargetException, IllegalAccessException;

    ResultBo updateNoUserPassword(EditAdminUserParameter user) throws InvocationTargetException, IllegalAccessException;

    ResultBo deleteUser(Map user) throws InvocationTargetException, IllegalAccessException;

    ResultBo insertRole(Map user) throws InvocationTargetException, IllegalAccessException;

}
