package com.mksoft.shop.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.mksoft.shop.parameter.AddAdminDepartmentParameter;
import com.mksoft.shop.parameter.EditAdminDepartmentParameter;
import com.mksoft.shop.service.AdminDepartmentService;
import com.mksoft.shop.service.bo.ResultBo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/admin/department")
@Api(value = "AdminDepartmentController", description = "组织部门管理")
public class AdminDepartmentController {

    @Resource
    private AdminDepartmentService departmentService;

    /**
     * @Description:查询所有部门
     * @author huangc
     * @date 2017-04-13
     */
    @RequestMapping(value = "/departmentList/all", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取所有部门", httpMethod = "GET", response = JSONObject.class, notes = "返回结果为list，list中元素定义见default的model")
    public ResultBo listAll() {
        return departmentService.listAll();
    }

    /**
     * @Description:查询所有部门
     * @author huangc
     * @date 2017-04-13
     */
    @RequestMapping(value = "/departmentList", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "获取所有部门", httpMethod = "GET", response = JSONObject.class, notes = "返回结果为list，list中元素定义见default的model")
    public ResultBo list() {
        return departmentService.list();
    }

    /**
     * @Description:新增部门(admin)
     *
     */
    @ApiOperation(value = "新增部门(admin)", httpMethod = "POST", response = JSONObject.class, notes = "")
    @RequestMapping(value = "", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultBo insert(@ApiParam(value = "department", required = true)
                           @RequestBody AddAdminDepartmentParameter department)
            throws InvocationTargetException, IllegalAccessException {

        //主处理
        return departmentService.add(department);
    }

    /**
     * @Description:修改部门(admin)
     *
     */
    @ApiOperation(value = "修改部门(admin)", httpMethod = "PUT", response = JSONObject.class, notes = "")
    @RequestMapping(value = "/{adminDptPkid}", method = RequestMethod.PUT, produces = {"application/json;charset=UTF-8"})
    public ResultBo update(@ApiParam(value = "adminDptPkid", required = true)@PathVariable Integer adminDptPkid,
                           @ApiParam(value = "department", required = true) @RequestBody EditAdminDepartmentParameter department)
            throws InvocationTargetException, IllegalAccessException {

        //主处理
        return departmentService.update(adminDptPkid,department);
    }


    /**
     * @Description:删除部门(admin)
     *
     */
    @ApiOperation(value = "删除部门(admin)", httpMethod = "POST", response = JSONObject.class, notes = "")
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public ResultBo delete(@ApiParam(value = "department", required = true)
                               @RequestBody List<Integer> adminDptPkid) throws InvocationTargetException, IllegalAccessException {

        //主处理
        return departmentService.delete(adminDptPkid);
    }

}
