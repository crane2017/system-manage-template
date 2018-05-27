package com.mksoft.shop.service;

import com.mksoft.shop.parameter.AddAdminMenuParameter;
import com.mksoft.shop.parameter.EditAdminMenuParameter;
import com.mksoft.shop.query.model.TSysAdminMenuExt;
import com.mksoft.shop.service.bo.ResultBo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface AdminMenuService {
    ResultBo<TSysAdminMenuExt> list();

    ResultBo listAdminMenu(Map param);

    ResultBo listUserMenu(Map param);

    ResultBo add(AddAdminMenuParameter params) throws InvocationTargetException, IllegalAccessException;

    ResultBo update(Integer menuPkid, EditAdminMenuParameter params) throws InvocationTargetException, IllegalAccessException;

    ResultBo delete(Integer menuPkid) throws InvocationTargetException, IllegalAccessException;

    ResultBo deleteList(List<Integer> menuListPkid);
}
