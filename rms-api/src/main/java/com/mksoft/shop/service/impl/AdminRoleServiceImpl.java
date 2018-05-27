package com.mksoft.shop.service.impl;

import com.mksoft.shop.Constants;
import com.mksoft.shop.config.jwt.JwtHelper;
import com.mksoft.shop.model.TSysAdminMenuAuth;
import com.mksoft.shop.model.TSysAdminMenuAuthCriteria;
import com.mksoft.shop.model.TSysAdminRole;
import com.mksoft.shop.model.mapper.TSysAdminMenuAuthMapper;
import com.mksoft.shop.model.mapper.TSysAdminRoleMapper;
import com.mksoft.shop.query.TSysAdminRoleQuery;
import com.mksoft.shop.service.AdminRoleService;
import com.mksoft.shop.service.bo.ResultBo;
import com.mksoft.shop.util.BeanUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminRoleServiceImpl implements AdminRoleService {

    @Resource
    private JwtHelper jwtHelper;
    @Resource
    private TSysAdminRoleQuery tAdminRoleQuery;
    @Resource
    private TSysAdminRoleMapper tAdminRoleMapper;
    @Resource
    private TSysAdminMenuAuthMapper tAdminMenuAuthMapper;

    @Override
    public ResultBo listAdminRole(Map param) {
        List<Map> roleList = tAdminRoleQuery.search(param);
        Integer roleCount = tAdminRoleQuery.getRoleCount();

        ResultBo result = new ResultBo();
        result.setRows(roleList);
        result.setCount(roleCount);

        return result;
    }

    @Override
    public ResultBo insertAdminRole(Map param) throws InvocationTargetException, IllegalAccessException {

        ResultBo result = new ResultBo();
        TSysAdminRole tAdminRole = new TSysAdminRole();
        BeanUtil.map2ObjSnack2Camel(tAdminRole, param);
        tAdminRole.setUid(jwtHelper.getLoginUUID());
        tAdminRole.setCid(jwtHelper.getLoginUUID());
        Integer resultCode = tAdminRoleMapper.insertSelective(tAdminRole);
        result.setOpResult(resultCode);

        return result;
    }

    @Override
    public ResultBo updateAdminRole(Map param) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        TSysAdminRole tAdminRole = new TSysAdminRole();
        BeanUtil.map2ObjSnack2Camel(tAdminRole, param);
        tAdminRole.setUid(jwtHelper.getLoginUUID());
        Integer resultCode = tAdminRoleMapper.updateByPrimaryKeySelective(tAdminRole);
        result.setOpResult(resultCode);

        return result;
    }

    @Override
    public ResultBo deleteAdminRole(Map param) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        TSysAdminRole tAdminRole = new TSysAdminRole();
        BeanUtil.map2ObjSnack2Camel(tAdminRole, param);
        tAdminRole.setUid(jwtHelper.getLoginUUID());
        tAdminRole.setVer(Constants.VER_MINUS_1);
        Integer resultCode = tAdminRoleMapper.updateByPrimaryKeySelective(tAdminRole);
        result.setOpResult(resultCode);

        return result;
    }

    @Override
    public ResultBo setAdminRole(Map param) throws InvocationTargetException, IllegalAccessException {

        Boolean isExit = false;
        Integer resultCode = 0;
        ResultBo result = new ResultBo();
        Integer oldMenu = null;
        Integer newMenu = null;
        List deleteList = new ArrayList();
        TSysAdminMenuAuth tAdminMenuAuth = new TSysAdminMenuAuth();
        TSysAdminMenuAuthCriteria tAdminMenuAuthCriteria = new TSysAdminMenuAuthCriteria();
        BeanUtil.map2ObjSnack2Camel(tAdminMenuAuth, param);
        tAdminMenuAuth.setUid(jwtHelper.getLoginUUID());
        tAdminMenuAuth.setCid(jwtHelper.getLoginUUID());
        List oldMenuList = (List) param.get("oldMenuList");
        List newMenuList = (List) param.get("newMenuList");
        for (int i = 0; i < newMenuList.size(); i++) {
            newMenu = Integer.parseInt(newMenuList.get(i).toString());
            for (int j = 0; j < oldMenuList.size(); j++) {
                oldMenu = Integer.parseInt(oldMenuList.get(j).toString());
                if (newMenu == oldMenu) {
                    isExit = true;
                    break;
                }
            }
            if (!isExit) {
                tAdminMenuAuth.setMenuPkid(newMenu);
                resultCode = tAdminMenuAuthMapper.insertSelective(tAdminMenuAuth);
            }
            isExit = false;
        }
        isExit = false;
        oldMenu = null;
        newMenu = null;
        if (oldMenuList.size() > 0) {
            for (int m = 0; m < oldMenuList.size(); m++) {
                oldMenu = Integer.parseInt(oldMenuList.get(m).toString());
                for (int n = 0; n < newMenuList.size(); n++) {
                    newMenu = Integer.parseInt(newMenuList.get(n).toString());
                    if (newMenu == oldMenu) {
                        isExit = true;
                        break;
                    }
                }
                if (!isExit) {
                    deleteList.add(oldMenu);
                }
                isExit = false;
            }
            if (deleteList.size() > 0) {
                tAdminMenuAuthCriteria.createCriteria().andMenuPkidIn(deleteList).andAdminRolePkidEqualTo(tAdminMenuAuth.getAdminRolePkid());
                tAdminMenuAuth.setVer(Constants.VER_MINUS_1);
                resultCode = tAdminMenuAuthMapper.updateByExampleSelective(tAdminMenuAuth, tAdminMenuAuthCriteria);
            }
        }

        result.setOpResult(resultCode);

        return result;
    }
}