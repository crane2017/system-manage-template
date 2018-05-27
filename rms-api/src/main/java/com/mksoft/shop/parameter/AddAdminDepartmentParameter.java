package com.mksoft.shop.parameter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by lichu on 2017/6/26.
 */
@Data
@ApiModel("添加部门参数")
public class AddAdminDepartmentParameter {

    @ApiModelProperty("父级部门")
    private Integer dptParentPkid;

    @ApiModelProperty("门店id")
    private Integer storeId;

    @ApiModelProperty("部门代号")
    private String adminDptKey;

    @ApiModelProperty("部门名称")
    private String adminDptName;

    @ApiModelProperty("部门描述")
    private String note;

    public Integer getDptParentPkid() {
        return dptParentPkid;
    }

    public void setDptParentPkid(Integer dptParentPkid) {
        this.dptParentPkid = dptParentPkid;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getAdminDptKey() {
        return adminDptKey;
    }

    public void setAdminDptKey(String adminDptKey) {
        this.adminDptKey = adminDptKey;
    }

    public String getAdminDptName() {
        return adminDptName;
    }

    public void setAdminDptName(String adminDptName) {
        this.adminDptName = adminDptName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
