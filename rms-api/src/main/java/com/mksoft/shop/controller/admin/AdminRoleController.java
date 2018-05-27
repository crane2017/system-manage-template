package com.mksoft.shop.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.mksoft.shop.service.AdminRoleService;
import com.mksoft.shop.service.bo.ResultBo;
import com.mksoft.shop.util.CommonUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/role")
@Api(value = "AdminRoleController", description = "角色管理")
public class AdminRoleController {

    @Resource
    private AdminRoleService adminRoleService;

    private static final String INSERTADMINROLE_PARAM_EXAMPLE = "{\n" +
            "    \"admin_role_pkid\":null,\n" +
            "    \"admin_role_key\":\"test\",\n" +
            "    \"admin_role_name\":\"测试\",\n" +
            "    \"uid\":\"ASDCFVGBHNJMK<L>:AZSXDCFVGBHNJMK\"\n" +
            "}";

    private static final String UPDATEADMINROLE_PARAM_EXAMPLE = "{\n" +
            "    \"admin_role_pkid\":11,\n" +
            "    \"admin_role_key\":\"test\",\n" +
            "    \"admin_role_name\":\"test\",\n" +
            "    \"uid\":\"ASDCFVGBHNJMK<L>:AZSXDCFVGBHNJMK\"\n" +
            "}";

    /**
     * @Description:按条件查找角色
     * @author huangc
     * @date 2017-04-13
     */
    @ApiOperation(value = "按条件查找角色", httpMethod = "GET", response = JSONObject.class, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "页码", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数据量", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "admin_role_name", value = "角色名", dataType = "string", paramType = "query", required = false)
    })
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResultBo listAdminRole(
            @RequestParam(value = "pageNo", required = true) int pageNo,
            @RequestParam(value = "pageSize", required = true) int pageSize,
            @RequestParam(value = "admin_role_name", required = false) String admin_role_name
    ) {
        //前处理
        Map param = new HashMap();
        param.put("pageNo", pageNo);
        param.put("pageSize", pageSize);
        param.put("admin_role_name", admin_role_name);
        param.put("startRow", CommonUtil.calcStartRow(param));

        //主处理
        return adminRoleService.listAdminRole(param);
    }

    /**
     * @Description:新增角色(admin)
     * @author huangc
     * @date 2017-04-13
     */
    @ApiOperation(value = "新增角色(admin)", httpMethod = "POST", response = JSONObject.class, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = INSERTADMINROLE_PARAM_EXAMPLE, dataType = "Map", paramType = "body", required = false),
    })
    @RequestMapping(value = "", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultBo insertAdminRole(
            @ApiParam(value = "", required = true) @RequestBody Map role
    ) throws InvocationTargetException, IllegalAccessException {

        //主处理
        return adminRoleService.insertAdminRole(role);
    }

    /**
     * @Description:更新角色(admin)
     * @author huangc
     * @date 2017-04-13
     */
    @ApiOperation(value = "更新角色(admin)", httpMethod = "PUT", response = JSONObject.class, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = UPDATEADMINROLE_PARAM_EXAMPLE, dataType = "Map", paramType = "body", required = false),
    })
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public ResultBo updateAdminRole(
            @ApiParam(value = "", required = true) @RequestBody Map role
    ) throws InvocationTargetException, IllegalAccessException {

        //主处理
        return adminRoleService.updateAdminRole(role);
    }

    /**
     * @Description:删除角色(admin)
     * @author huangc
     * @date 2017-04-13
     */
    @ApiOperation(value = "删除角色(admin)", httpMethod = "DELETE", response = JSONObject.class, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "admin_role_pkid", value = "角色编号", dataType = "int", paramType = "path", required = true),
            @ApiImplicitParam(name = "uid", value = "更新者id", dataType = "string", paramType = "query", required = true),
    })
    @RequestMapping(value = "/{admin_role_pkid}", method = RequestMethod.DELETE, produces = {"application/json;charset=UTF-8"})
    public ResultBo deleteTask(
            @ApiParam(value = "admin_role_pkid", required = true)@PathVariable Integer admin_role_pkid,
            @ApiParam(required = true, name = "uid", value = "uid") @RequestParam(value = "uid", required = true) String uid
    ) throws InvocationTargetException, IllegalAccessException {
        //前处理
        Map param = new HashMap();
        param.put("admin_role_pkid", admin_role_pkid);
        param.put("uid", uid);

        //主处理
        return adminRoleService.deleteAdminRole(param);
    }

    /**
     * @Description:配置资源(admin)
     * @author huangc
     * @date 2017-04-13
     */
    @ApiOperation(value = "配置资源(admin)", httpMethod = "POST", response = JSONObject.class, notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role", value = INSERTADMINROLE_PARAM_EXAMPLE, dataType = "Map", paramType = "body", required = false),
    })
    @RequestMapping(value = "/resource", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultBo setAdminRole(
            @ApiParam(value = "", required = true) @RequestBody Map role
    ) throws InvocationTargetException, IllegalAccessException {

        //主处理
        return adminRoleService.setAdminRole(role);
    }
}
