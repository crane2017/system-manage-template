package com.mksoft.shop.service.impl;

import com.mksoft.shop.Constants;
import com.mksoft.shop.config.jwt.JwtHelper;
import com.mksoft.shop.model.*;
import com.mksoft.shop.model.mapper.TSysAdminMenuAuthMapper;
import com.mksoft.shop.model.mapper.TSysAdminMenuMapper;
import com.mksoft.shop.model.mapper.TSysAdminUserRoleMapper;
import com.mksoft.shop.parameter.AddAdminMenuParameter;
import com.mksoft.shop.parameter.EditAdminMenuParameter;
import com.mksoft.shop.query.TSysAdminMenuQuery;
import com.mksoft.shop.query.model.TSysAdminMenuExt;
import com.mksoft.shop.service.AdminMenuService;
import com.mksoft.shop.service.bo.ResultBo;
import com.mksoft.shop.util.BeanUtil;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminMenuServiceImpl implements AdminMenuService {
    @Resource
    private TSysAdminMenuMapper adminMenuMapper;
    @Resource
    private TSysAdminMenuQuery tAdminMenuQuery;
    @Resource
    private JwtHelper jwtHelper;
    @Resource
    private TSysAdminMenuAuthMapper tSysAdminMenuAuthMapper;
    @Resource
    private TSysAdminUserRoleMapper tSysAdminUserRoleMapper;

    @Override
    public ResultBo list() {
        ResultBo<TSysAdminMenuExt> result = new ResultBo();

        result.setRows(tAdminMenuQuery.list());

        return result;
    }

    @Override
    public ResultBo listAdminMenu(Map param) {
        List<TSysAdminMenuExt> menuList = new ArrayList<TSysAdminMenuExt>();
        List<TSysAdminMenuExt> rootMenu = tAdminMenuQuery.search();
        // 先找到所有的一级菜单
        for (int i = 0; i < rootMenu.size(); i++) {
            if (rootMenu.get(i).getParentPkid() == null || "".equals(rootMenu.get(i).getParentPkid())) {
                menuList.add(rootMenu.get(i));
            }
        }
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (TSysAdminMenuExt menu : menuList) {
            int parentPkid = menu.getMenuPkid();
            List<TSysAdminMenuExt> childMenu = listChild(parentPkid, rootMenu);
            menu.setChildren(childMenu);
        }

        ResultBo result = new ResultBo();
        result.setRows(menuList.stream().sorted(Comparator.comparing(TSysAdminMenuExt::getMenuOrder))
                .collect(Collectors.toList()));
        return result;
    }

    @Override
    public ResultBo listUserMenu(Map param) {
        param.put("adminUserPkid", jwtHelper.getLoginPkid());
        List<TSysAdminMenuExt> menuList = new ArrayList<TSysAdminMenuExt>();
        List<TSysAdminMenuExt> subList = new ArrayList<TSysAdminMenuExt>();
        List<TSysAdminMenuExt> rootMenu = tAdminMenuQuery.search();
        List<TSysAdminMenuExt> userMenu = tAdminMenuQuery.searchMenu(param);

        Boolean flag = false;
        for (int i = 0; i < userMenu.size(); i++) {
            for (int j = 0; j < rootMenu.size(); j++) {
                flag = false;
                if ((userMenu.get(i).getMenuPkid()).equals(rootMenu.get(j).getMenuPkid())
                        || (rootMenu.get(j).getMenuPkid()).equals(userMenu.get(i).getParentPkid())) {
                    for (int k = 0; k < subList.size(); k++) {
                        if ((subList.get(k).getMenuPkid()).equals(rootMenu.get(j).getMenuPkid())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        subList.add(rootMenu.get(j));
                    }
                }
            }
        }

        // 先找到所有的一级菜单
        for (int i = 0; i < rootMenu.size(); i++) {
            if (rootMenu.get(i).getParentPkid() == null || "".equals(rootMenu.get(i).getParentPkid())) {
                for (int j = 0; j < subList.size(); j++) {
                    if (subList.get(j).getParentPkid() != null && !("".equals(subList.get(j).getParentPkid()))) {
                        if (rootMenu.get(i).getMenuPkid().toString().equals(subList.get(j).getParentPkid().toString())) {
                            menuList.add(rootMenu.get(i));
                            break;
                        }
                    }
                }
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (TSysAdminMenuExt menu : menuList) {
            int parentPkid = menu.getMenuPkid();
            List<TSysAdminMenuExt> childMenu = listChild(parentPkid, subList);
            menu.setChildren(childMenu.stream().sorted(Comparator.comparing(TSysAdminMenuExt::getMenuOrder))
                    .collect(Collectors.toList()));
        }

        ResultBo result = new ResultBo();
        result.setRows(menuList.stream().sorted(Comparator.comparing(TSysAdminMenuExt::getMenuOrder))
                .collect(Collectors.toList()));
        return result;
    }

    @Override
    public ResultBo add(AddAdminMenuParameter params) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        TSysAdminMenu adminMenu = new TSysAdminMenu();
        String loginUuid = jwtHelper.getLoginUUID();
        TSysAdminMenuAuth tSysAdminMenuAuth = new TSysAdminMenuAuth();

        BeanUtil.copyProperties(adminMenu, params);
        adminMenu.setCid(loginUuid);
        adminMenu.setUid(loginUuid);

        Integer resultCode = adminMenuMapper.insertSelective(adminMenu);

        TSysAdminUserRoleCriteria tSysAdminUserRoleCriteria = new TSysAdminUserRoleCriteria();
        tSysAdminUserRoleCriteria.createCriteria().andAdminUserPkidEqualTo(jwtHelper.getLoginPkid()).andVerEqualTo(Constants.VER_0);
        List<TSysAdminUserRole> tSysAdminUserRoleList = tSysAdminUserRoleMapper.selectByExample(tSysAdminUserRoleCriteria);

        tSysAdminMenuAuth.setAdminRolePkid(tSysAdminUserRoleList.get(0).getAdminRolePkid());
        tSysAdminMenuAuth.setMenuPkid(adminMenu.getMenuPkid());
        tSysAdminMenuAuth.setCid(loginUuid);
        tSysAdminMenuAuth.setUid(loginUuid);

        tSysAdminMenuAuthMapper.insertSelective(tSysAdminMenuAuth);

        result.setOpResult(resultCode);

        return result;
    }

    @Override
    public ResultBo update(Integer menuPkid, EditAdminMenuParameter params) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        TSysAdminMenu adminMenu = new TSysAdminMenu();
        String loginUuid = jwtHelper.getLoginUUID();

        BeanUtil.copyProperties(adminMenu, params);
        adminMenu.setMenuPkid(menuPkid);
        adminMenu.setUid(loginUuid);

        Integer resultCode = adminMenuMapper.updateByPrimaryKeySelective(adminMenu);
        result.setOpResult(resultCode);

        return result;

    }

    @Override
    public ResultBo delete(Integer menuPkid) throws InvocationTargetException, IllegalAccessException {
        ResultBo result = new ResultBo();
        TSysAdminMenu adminMenu = new TSysAdminMenu();
        String loginUuid = jwtHelper.getLoginUUID();

        adminMenu.setMenuPkid(menuPkid);
        adminMenu.setVer(Constants.VER_MINUS_1);
        adminMenu.setUid(loginUuid);

        Integer resultCode = adminMenuMapper.updateByPrimaryKeySelective(adminMenu);
        result.setOpResult(resultCode);

        return result;

    }

    @Override
    public ResultBo deleteList(List<Integer> menuListPkid) {
        ResultBo result = new ResultBo();
        TSysAdminMenu adminMenu = new TSysAdminMenu();
        String loginUuid = jwtHelper.getLoginUUID();
        Integer resultCode = 0;
        for (Integer menuPkid : menuListPkid) {
            adminMenu.setMenuPkid(menuPkid);
            adminMenu.setVer(Constants.VER_MINUS_1);
            adminMenu.setUid(loginUuid);
            resultCode = adminMenuMapper.updateByPrimaryKeySelective(adminMenu);
        }

        result.setOpResult(resultCode);

        return result;
    }

    /**
     * 递归查找子菜单
     *
     * @param id       当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    private List<TSysAdminMenuExt> listChild(int id, List<TSysAdminMenuExt> rootMenu) {
        // 子菜单
        List<TSysAdminMenuExt> childList = new ArrayList<>();
        for (TSysAdminMenuExt menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentPkid()!= null && !("".equals(menu.getParentPkid()))) {
                if (menu.getParentPkid().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 递归退出条件 无子菜单则退出
        if (childList.size() == 0) {
            return null;
        }
        // 把子菜单的子菜单再循环一遍
        for (TSysAdminMenuExt menu : childList) {
            // 没有url子菜单还有子菜单
            // 2017.12.2子菜单增加按钮资源
            //if (menu.getUrl() == null || "".equals(menu.getUrl())) {
                Integer menuPkid = menu.getMenuPkid();
                // 递归
                List<TSysAdminMenuExt> tSysAdminMenuExtList = listChild(menuPkid, rootMenu);
                menu.setChildren(tSysAdminMenuExtList);
            //}
        }
        return childList.stream().sorted(Comparator.comparing(TSysAdminMenuExt::getMenuOrder))
                .collect(Collectors.toList());
    }

}
