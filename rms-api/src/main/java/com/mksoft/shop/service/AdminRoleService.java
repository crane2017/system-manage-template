package com.mksoft.shop.service;

import com.mksoft.shop.service.bo.ResultBo;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface AdminRoleService {
    ResultBo listAdminRole(Map param);

    ResultBo insertAdminRole(Map param) throws InvocationTargetException, IllegalAccessException;

    ResultBo updateAdminRole(Map param) throws InvocationTargetException, IllegalAccessException;

    ResultBo deleteAdminRole(Map param) throws InvocationTargetException, IllegalAccessException;

    ResultBo setAdminRole(Map param) throws InvocationTargetException, IllegalAccessException;
}
