package com.mksoft.shop.service.impl;

import com.mksoft.shop.Constants;
import com.mksoft.shop.config.jwt.JwtHelper;
import com.mksoft.shop.model.*;
import com.mksoft.shop.model.mapper.*;
import com.mksoft.shop.parameter.EditAdminUserParameter;
import com.mksoft.shop.query.TSysAdminUserQuery;
import com.mksoft.shop.service.AdminUserService;
import com.mksoft.shop.service.bo.ResultBo;
import com.mksoft.shop.util.BeanUtil;
import com.mksoft.shop.util.CommonUtil;
import com.mksoft.shop.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional(rollbackFor = Exception.class)
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private JwtHelper jwtHelper;

    @Resource
    private TSysAdminUserQuery adminUserQuery;

    @Resource
    private TSysAdminUserMapper tAdminUserMapper;

    @Resource
    private TSysAdminUserRoleMapper tAdminUserRoleMapper;

    @Resource
    private TSysAdminUserDptMapper tSysAdminUserDptMapper;

    @Resource
    private TSysAdminDepartmentMapper tSysAdminDepartmentMapper;

    @Resource
    private TSysAdminUserQuery tSysAdminUserQuery;

    @Override
    public ResultBo listUser(Map param) {
        ResultBo result = new ResultBo();
        List<Map> mapList = adminUserQuery.search(param);

        for (Map dpt : mapList) {
            if (dpt.get("admin_dpt_pkid") != null) {
                List<TSysAdminDepartment> childList = new ArrayList<>();
                List<TSysAdminDepartment> list = listParent(Integer.parseInt(dpt.get("admin_dpt_pkid").toString()), childList);
                dpt.put("children", list);
            }
        }

        result.setRows(mapList);
        result.setCount(adminUserQuery.count(param));
        return result;
    }

    public List<TSysAdminDepartment> listParent(Integer admin_dpt_pkid, List<TSysAdminDepartment> childList) {
        TSysAdminDepartment tSysAdminDepartment = tSysAdminDepartmentMapper.selectByPrimaryKey(admin_dpt_pkid);
        childList.add(tSysAdminDepartment);
        if (tSysAdminDepartment.getDptParentPkid() != null) {
            listParent(tSysAdminDepartment.getDptParentPkid(), childList);
        }
        return childList;
    }

    @Override
    public ResultBo insertUser(Map param) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        TSysAdminUser tAdminUser = new TSysAdminUser();
        BeanUtil.clearBlankInMap(param);
        BeanUtil.map2ObjSnack2Camel(tAdminUser, param);
        if (checkAdminUserLogin(tAdminUser) > 0) {
            result.setOpResult(-1);
            return result;
        }
        tAdminUser.setCid(jwtHelper.getLoginUUID());
        tAdminUser.setUid(jwtHelper.getLoginUUID());
        tAdminUser.setAdminUserUuid(CommonUtil.generateUUID36());
        tAdminUser.setAdminUserPwd(SecurityUtil.pwdEncode(tAdminUser.getAdminUserPwd()));
        Integer resultCode = tAdminUserMapper.insertSelective(tAdminUser);

        TSysAdminUserDpt tSysAdminUserDpt = new TSysAdminUserDpt();
        Integer count = 0;
        if (resultCode > 0) {
            tSysAdminUserDpt.setAdminUserPkid(tAdminUser.getAdminUserPkid());
            tSysAdminUserDpt.setAdminDptPkid(Integer.parseInt(param.get("admin_dpt_pkid").toString()));
            tSysAdminUserDpt.setCid(jwtHelper.getLoginUUID());
            tSysAdminUserDpt.setUid(jwtHelper.getLoginUUID());

            count = tSysAdminUserDptMapper.insertSelective(tSysAdminUserDpt);
            result.setOpResult(count);

        } else {
            result.setOpResult(resultCode);
        }

        return result;
    }

    @Override
    public ResultBo updateUser(Map param) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        TSysAdminUser tAdminUser = new TSysAdminUser();
        BeanUtil.map2ObjSnack2Camel(tAdminUser, param);
        tAdminUser.setUid(jwtHelper.getLoginUUID());
        Integer resultCode = tAdminUserMapper.updateByPrimaryKeySelective(tAdminUser);

        TSysAdminUserDpt tSysAdminUserDpt = new TSysAdminUserDpt();
        Integer count = 0;
        if (resultCode > 0) {
            if (param.get("admin_user_dpt_pkid") != null) {
                tSysAdminUserDpt.setAdminUserDptPkid(Integer.parseInt(param.get("admin_user_dpt_pkid").toString()));
                tSysAdminUserDpt.setAdminDptPkid(Integer.parseInt(param.get("admin_dpt_pkid").toString()));
                tSysAdminUserDpt.setUid(jwtHelper.getLoginUUID());

                count = tSysAdminUserDptMapper.updateByPrimaryKeySelective(tSysAdminUserDpt);
            } else {
                tSysAdminUserDpt.setAdminUserPkid(tAdminUser.getAdminUserPkid());
                tSysAdminUserDpt.setAdminDptPkid(Integer.parseInt(param.get("admin_dpt_pkid").toString()));
                tSysAdminUserDpt.setCid(jwtHelper.getLoginUUID());
                tSysAdminUserDpt.setUid(jwtHelper.getLoginUUID());

                count = tSysAdminUserDptMapper.insertSelective(tSysAdminUserDpt);
            }
            result.setOpResult(count);

        } else {
            result.setOpResult(resultCode);
        }
        return result;
    }

    @Override
    public ResultBo updateUserPassword(EditAdminUserParameter param) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        Map params = new HashMap();
        params.put("adminUserLogin", param.getAdminUserLogin());
        List<TSysAdminUser> tSysAdminUserList = tSysAdminUserQuery.getLoginAdminUser(params);
        if (tSysAdminUserList.get(0).getAdminUserPwd().equals(SecurityUtil.pwdEncode(param.getOldPassword()))) {
            tSysAdminUserList.get(0).setAdminUserPwd(SecurityUtil.pwdEncode(param.getNewPassword()));
            result.setOpResult(tAdminUserMapper.updateByPrimaryKeySelective(tSysAdminUserList.get(0)));
        } else {
            result.setOpResult(-1);
        }

        return result;
    }

    @Override
    public ResultBo updateNoUserPassword(EditAdminUserParameter param) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        Map params = new HashMap();
        params.put("adminUserLogin", param.getAdminUserLogin());
        List<TSysAdminUser> tSysAdminUserList = tSysAdminUserQuery.getLoginAdminUser(params);
        tSysAdminUserList.get(0).setAdminUserPwd(SecurityUtil.pwdEncode(param.getNewPassword()));
        result.setOpResult(tAdminUserMapper.updateByPrimaryKeySelective(tSysAdminUserList.get(0)));

        return result;
    }

    @Override
    public ResultBo deleteUser(Map param) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        TSysAdminUser tAdminUser = new TSysAdminUser();
        BeanUtil.map2ObjSnack2Camel(tAdminUser, param);
        tAdminUser.setUid(jwtHelper.getLoginUUID());
        tAdminUser.setVer(Constants.VER_MINUS_1);
        Integer resultCode = tAdminUserMapper.updateByPrimaryKeySelective(tAdminUser);
        result.setOpResult(resultCode);

        return result;
    }

    public Integer checkAdminUserLogin(TSysAdminUser tAdminUser) {
        Map map = new HashMap();
        map.put("adminUserLogin", tAdminUser.getAdminUserLogin());
        Integer resultCount = adminUserQuery.count(map);
        return resultCount;
    }

    @Override
    public ResultBo insertRole(Map param) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        Integer resultCode = null;
        TSysAdminUserRole tAdminUserRole = new TSysAdminUserRole();
        BeanUtil.map2ObjSnack2Camel(tAdminUserRole, param);
        tAdminUserRole.setUid(jwtHelper.getLoginUUID());
        if (tAdminUserRole.getAdminUserRolePkid() != null) {
            resultCode = tAdminUserRoleMapper.updateByPrimaryKeySelective(tAdminUserRole);
        } else {
            tAdminUserRole.setCid(tAdminUserRole.getUid());
            resultCode = tAdminUserRoleMapper.insertSelective(tAdminUserRole);
        }
        result.setOpResult(resultCode);

        return result;
    }

}
