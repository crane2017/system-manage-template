package com.mksoft.shop.service;

import com.mksoft.shop.parameter.AddAdminDepartmentParameter;
import com.mksoft.shop.parameter.EditAdminDepartmentParameter;
import com.mksoft.shop.query.model.TSysAdminDepartmentExt;
import com.mksoft.shop.service.bo.ResultBo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface AdminDepartmentService {
    ResultBo<TSysAdminDepartmentExt> list();

    ResultBo<TSysAdminDepartmentExt> listAll();

    ResultBo add(AddAdminDepartmentParameter params) throws InvocationTargetException, IllegalAccessException;

    ResultBo update(Integer adminDptPkid, EditAdminDepartmentParameter params) throws InvocationTargetException, IllegalAccessException;

    ResultBo delete(List<Integer> adminDptPkid) throws InvocationTargetException, IllegalAccessException;

}
