package com.mksoft.shop.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.mksoft.shop.parameter.AddAdminMenuParameter;
import com.mksoft.shop.parameter.EditAdminMenuParameter;
import com.mksoft.shop.service.AdminMenuService;
import com.mksoft.shop.service.bo.ResultBo;
import com.mksoft.shop.util.CommonUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/menu")
@Api(value = "AdminMenuController", description = "菜单管理")
public class AdminMenuController {

    @Resource
    private AdminMenuService adminMenuService;

    /**
     * @Description:查询所有菜单
     * @author huangc
     * @date 2017-04-13
     */
    @RequestMapping(value = "/menuList", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取所有菜单", httpMethod = "GET", response = JSONObject.class, notes = "返回结果为list，list中元素定义见default的model")
    public ResultBo list() {
        return adminMenuService.list();
    }

    @ApiOperation(value = "查询所有菜单(admin)", httpMethod = "GET", response = JSONObject.class, notes = "")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResultBo listAdminMenu() {
        //前处理
        Map param = new HashMap();

        //主处理
        return adminMenuService.listAdminMenu(param);
    }

    /**
     * @Description:获取用户菜单
     * @author huangc
     * @date 2017-04-13
     */
    @ApiOperation(value = "获取用户菜单(admin)", httpMethod = "GET", response = JSONObject.class, notes = "")
    @RequestMapping(value = "/role", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResultBo listUserMenu() {
        //前处理
        Map param = new HashMap();

        //主处理
        return adminMenuService.listUserMenu(param);
    }
    /**
     * @Description:新增菜单(admin)
     *
     */
    @ApiOperation(value = "新增菜单(admin)", httpMethod = "POST", response = JSONObject.class, notes = "")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultBo insert(@ApiParam(value = "menu", required = true)
                           @RequestBody AddAdminMenuParameter menu)
            throws InvocationTargetException, IllegalAccessException {

        //主处理
        return adminMenuService.add(menu);
    }

    /**
     * @Description:修改菜单(admin)
     *
     */
    @ApiOperation(value = "修改菜单(admin)", httpMethod = "PUT", response = JSONObject.class, notes = "")
    @RequestMapping(value = "/{menuPkid}", method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public ResultBo update(@ApiParam(value = "menuPkid", required = true)@PathVariable Integer menuPkid,
                           @ApiParam(value = "menu", required = true) @RequestBody EditAdminMenuParameter menu)
            throws InvocationTargetException, IllegalAccessException {

        //主处理
        return adminMenuService.update(menuPkid,menu);
    }


    /**
     * @Description:删除菜单(admin)
     *
     */
    @ApiOperation(value = "删除菜单(admin)", httpMethod = "DELETE", response = JSONObject.class, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuPkid", value = "菜单pkid", dataType = "int", paramType = "path", required = false),})
    @RequestMapping(value = "/{menuPkid}", method = RequestMethod.DELETE, produces = {"application/json;charset=UTF-8"})
    public ResultBo delete(@PathVariable Integer menuPkid) throws InvocationTargetException, IllegalAccessException {

        //主处理
        return adminMenuService.delete(menuPkid);
    }

    /**
     * @Description:删除菜单数组(admin)
     *
     */
    @ApiOperation(value = "删除菜单数组(admin)", httpMethod = "POST", response = JSONObject.class, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuListPkid", value = "菜单数组pkid", dataType = "list", paramType = "body", required = true),})
    @RequestMapping(value = "/menuListPkid", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultBo deleteList(@RequestBody List<Integer> menuListPkid)
            throws InvocationTargetException, IllegalAccessException {

        //主处理
        return adminMenuService.deleteList(menuListPkid);
    }
}
