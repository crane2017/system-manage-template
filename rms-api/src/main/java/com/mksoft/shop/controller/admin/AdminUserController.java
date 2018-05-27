package com.mksoft.shop.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.mksoft.shop.parameter.EditAdminUserParameter;
import com.mksoft.shop.service.AdminUserService;
import com.mksoft.shop.service.bo.ResultBo;
import com.mksoft.shop.util.CommonUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
@Api(value = "AdminUserController", description = "后台用户管理")
public class AdminUserController {

    @Resource
    private AdminUserService adminUserService;

    private static final String INSERTUSER_PARAM_EXAMPLE = "{\n" +
            "    \"admin_user_pkid\":null,\n" +
            "    \"admin_user_login\":\"test\",\n" +
            "    \"admin_user_name\":\"test\",\n" +
            "    \"admin_user_email\":\"test@123.com\",\n" +
            "    \"start_date\":\"2017-05-15T05:33:48.297Z\",\n" +
            "    \"expire_date\":\"2017-05-31T05:33:50.829Z\",\n" +
            "    \"uid\":\"cfce5a6d-4cda-409a-a2c0-ba0803dc5143\"\n" +
            "}";

    private static final String UPDATEUSER_PARAM_EXAMPLE = "{\n" +
            "    \"admin_user_pkid\":13,\n" +
            "    \"admin_user_login\":\"test\",\n" +
            "    \"admin_user_name\":\"test\",\n" +
            "    \"admin_user_email\":\"test@123.com\",\n" +
            "    \"start_date\":1494826428000,\n" +
            "    \"expire_date\":1496208830000,\n" +
            "    \"uid\":\"cfce5a6d-4cda-409a-a2c0-ba0803dc5143\"\n" +
            "}";

    /**
     * @Description:按条件查询用户(admin)
     * @author huangc
     * @date 2017-04-13
     */
    @ApiOperation(value = "按条件查询用户(admin)", httpMethod = "GET", response = JSONObject.class, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "admin_user_login", value = "账号", dataType = "string", paramType = "query", required = false),
            @ApiImplicitParam(name = "admin_user_name", value = "姓名", dataType = "string", paramType = "query", required = false)
    })
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResultBo listUser(
            @RequestParam(value = "pageNo", required = true) int pageNo,
            @RequestParam(value = "pageSize", required = true) int pageSize,
            @RequestParam(value = "admin_user_login", required = false) String admin_user_login,
            @RequestParam(value = "admin_user_name", required = false) String admin_user_name
    ) {
        //前处理
        Map param = new HashMap();
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);
        param.put("admin_user_login", admin_user_login);
        param.put("admin_user_name", admin_user_name);

        param.put("startRow", CommonUtil.calcStartRow(param));
        //主处理
        return adminUserService.listUser(param);
    }

    /**
     * @Description:更新用户信息(admin)
     * @author huangc
     * @date 2017-04-13
     */
    @ApiOperation(value = "更新用户信息(admin)", httpMethod = "PUT", response = JSONObject.class, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = UPDATEUSER_PARAM_EXAMPLE, dataType = "Map", paramType = "body", required = false),
    })
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public ResultBo updateUser(
            @ApiParam(value = "user", required = true) @RequestBody Map user
    ) throws InvocationTargetException, IllegalAccessException {

        //主处理
        return adminUserService.updateUser(user);
    }

    /**
     * @Description:更新用户密码(admin)
     * @author huangc
     * @date 2017-04-13
     */
    @ApiOperation(value = "更新用户密码(admin)", httpMethod = "PUT", response = JSONObject.class, notes = "")
    @RequestMapping(value = "/change-password", method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public ResultBo updateUserPassword(
            @ApiParam(value = "user", required = true) @RequestBody EditAdminUserParameter user
    ) throws InvocationTargetException, IllegalAccessException {

        //主处理
        return adminUserService.updateUserPassword(user);
    }

    /**
     * @Description:更新用户密码(admin),无需旧密码
     * @author huangc
     * @date 2017-04-13
     */
    @ApiOperation(value = "更新用户密码(admin),无需旧密码", httpMethod = "PUT", response = JSONObject.class, notes = "")
    @RequestMapping(value = "/no-change-password", method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public ResultBo updateUserPassword2(
            @ApiParam(value = "user", required = true) @RequestBody EditAdminUserParameter user
    ) throws InvocationTargetException, IllegalAccessException {

        //主处理
        return adminUserService.updateNoUserPassword(user);
    }

    /**
     * @Description:新增用户(admin)
     * @author huangc
     * @date 2017-04-13
     */
    @ApiOperation(value = "新增会员(admin)", httpMethod = "POST", response = JSONObject.class, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = INSERTUSER_PARAM_EXAMPLE, dataType = "Map", paramType = "body", required = false),
    })
    @RequestMapping(value = "", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultBo insertUser(
            @ApiParam(value = "user", required = true) @RequestBody Map user
    ) throws InvocationTargetException, IllegalAccessException {

        //主处理
        return adminUserService.insertUser(user);
    }

    /**
     * @Description:删除用户(admin)
     * @author huangc
     * @date 2017-04-13
     */
    @ApiOperation(value = "删除会员(admin)", httpMethod = "DELETE", response = JSONObject.class, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "admin_user_pkid", value = "用户编号", dataType = "int", paramType = "path", required = false),
            @ApiImplicitParam(name = "uid", value = "更新者id", dataType = "string", paramType = "query", required = false),
    })
    @RequestMapping(value = "/{admin_user_pkid}", method = RequestMethod.DELETE, produces = {"application/json;charset=UTF-8"})
    public ResultBo deleteUser(
            @ApiParam(value = "admin_user_pkid", required = true)@PathVariable Integer admin_user_pkid,
            @ApiParam(required = true, name = "uid", value = "uid") @RequestParam(value = "uid", required = true) String uid
    ) throws InvocationTargetException, IllegalAccessException {
        //前处理
        Map param = new HashMap();
        param.put("admin_user_pkid", admin_user_pkid);
        param.put("uid", uid);

        //主处理
        return adminUserService.deleteUser(param);
    }

    /**
     * @Description:配置角色(admin)
     * @author huangc
     * @date 2017-04-13
     */
    @ApiOperation(value = "配置角色(admin)", httpMethod = "POST", response = JSONObject.class, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = INSERTUSER_PARAM_EXAMPLE, dataType = "Map", paramType = "body", required = false),
    })
    @RequestMapping(value = "/role", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultBo insertRole(
            @ApiParam(value = "user", required = true) @RequestBody Map user
    ) throws InvocationTargetException, IllegalAccessException {

        //主处理
        return adminUserService.insertRole(user);
    }

}
