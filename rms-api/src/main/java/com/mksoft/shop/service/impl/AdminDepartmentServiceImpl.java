package com.mksoft.shop.service.impl;

import com.mksoft.shop.Constants;
import com.mksoft.shop.config.jwt.JwtHelper;
import com.mksoft.shop.model.TSysAdminDepartment;
import com.mksoft.shop.model.mapper.TSysAdminDepartmentMapper;
import com.mksoft.shop.parameter.AddAdminDepartmentParameter;
import com.mksoft.shop.parameter.EditAdminDepartmentParameter;
import com.mksoft.shop.query.TSysAdminDepartmentQuery;
import com.mksoft.shop.query.model.TSysAdminDepartmentExt;
import com.mksoft.shop.service.AdminDepartmentService;
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
public class AdminDepartmentServiceImpl implements AdminDepartmentService {
    @Resource
    private TSysAdminDepartmentMapper adminDepartmentMapper;
    @Resource
    private TSysAdminDepartmentQuery tAdminDepartmentQuery;
    @Resource
    private JwtHelper jwtHelper;

    @Override
    public ResultBo list() {
        ResultBo<TSysAdminDepartmentExt> result = new ResultBo();

        List<TSysAdminDepartmentExt> tSysAdminDepartmentExtList = new ArrayList<TSysAdminDepartmentExt>();
        List<TSysAdminDepartmentExt> tSysAdminDepartmentList = tAdminDepartmentQuery.list();

        // 先找到所有的一级菜单
        for (int i = 0; i < tSysAdminDepartmentList.size(); i++) {
            if (tSysAdminDepartmentList.get(i).getDptParentPkid() == null) {
                TSysAdminDepartmentExt tSysAdminDepartmentExt = tSysAdminDepartmentList.get(i);
                tSysAdminDepartmentExtList.add(tSysAdminDepartmentExt);
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (TSysAdminDepartmentExt departmentExt : tSysAdminDepartmentExtList) {
            int parentPkid = departmentExt.getAdminDptPkid();
            List<TSysAdminDepartmentExt> childDepartment = listChild(parentPkid, tSysAdminDepartmentList);
            departmentExt.setChildren(childDepartment);
        }

        result.setRows(tSysAdminDepartmentExtList);

        return result;
    }

    @Override
    public ResultBo listAll() {
        ResultBo<TSysAdminDepartmentExt> result = new ResultBo();

        result.setRows(tAdminDepartmentQuery.list());

        return result;
    }

    /**
     * 递归查找子菜单
     *
     * @param id                      当前菜单id
     * @param tSysAdminDepartmentList 要查找的列表
     * @return
     */
    private List<TSysAdminDepartmentExt> listChild(int id, List<TSysAdminDepartmentExt> tSysAdminDepartmentList) {
        // 子菜单
        List<TSysAdminDepartmentExt> childList = new ArrayList<>();
        for (TSysAdminDepartmentExt departmentExt : tSysAdminDepartmentList) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (departmentExt.getDptParentPkid() != null && !("".equals(departmentExt.getDptParentPkid()))) {
                if (departmentExt.getDptParentPkid().equals(id)) {
                    childList.add(departmentExt);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (TSysAdminDepartmentExt departmentExt : childList) {// 没有url子菜单还有子菜单
            Integer adminDptPkid = departmentExt.getAdminDptPkid();
            // 递归
            departmentExt.setChildren(listChild(adminDptPkid, tSysAdminDepartmentList));
        } // 递归退出条件

        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    @Override
    public ResultBo add(AddAdminDepartmentParameter params) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        TSysAdminDepartment adminDepartment = new TSysAdminDepartment();
        String loginUuid = jwtHelper.getLoginUUID();

        BeanUtil.copyProperties(adminDepartment, params);
        adminDepartment.setCid(loginUuid);
        adminDepartment.setUid(loginUuid);

        Integer resultCode = adminDepartmentMapper.insertSelective(adminDepartment);
        result.setOpResult(resultCode);

        return result;
    }

    @Override
    public ResultBo update(Integer adminDptPkid, EditAdminDepartmentParameter params) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        TSysAdminDepartment adminDepartment = new TSysAdminDepartment();
        String loginUuid = jwtHelper.getLoginUUID();

        BeanUtil.copyProperties(adminDepartment, params);
        adminDepartment.setAdminDptPkid(adminDptPkid);
        adminDepartment.setUid(loginUuid);

        Integer resultCode = adminDepartmentMapper.updateByPrimaryKeySelective(adminDepartment);
        result.setOpResult(resultCode);

        return result;
    }

    @Override
    public ResultBo delete(List<Integer> adminDptPkid) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        TSysAdminDepartment adminDepartment = new TSysAdminDepartment();
        String loginUuid = jwtHelper.getLoginUUID();
        adminDptPkid.forEach(row -> {
            adminDepartment.setAdminDptPkid(row);
            adminDepartment.setVer(Constants.VER_MINUS_1);
            adminDepartment.setUid(loginUuid);

            Integer resultCode = adminDepartmentMapper.updateByPrimaryKeySelective(adminDepartment);
            result.setOpResult(resultCode);
        });

        return result;
    }

}
