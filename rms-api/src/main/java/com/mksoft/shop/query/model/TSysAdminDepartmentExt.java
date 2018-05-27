package com.mksoft.shop.query.model;

import com.mksoft.shop.model.TSysAdminDepartment;
import lombok.Data;

import java.util.List;

@Data
public class TSysAdminDepartmentExt extends TSysAdminDepartment {
    private String value;

    private String label;

    private List<TSysAdminDepartmentExt> children;

    public List<TSysAdminDepartmentExt> getChildren() {
        return children;
    }

    public void setChildren(List<TSysAdminDepartmentExt> children) {
        this.children = children;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}