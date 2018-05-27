package com.mksoft.shop.query.model;

import com.mksoft.shop.model.TSysAdminMenu;
import lombok.Data;

import java.util.List;

@Data
public class TSysAdminMenuExt extends TSysAdminMenu {
    private List<TSysAdminMenuExt> children;

    public List<TSysAdminMenuExt> getChildren() {
        return children;
    }

    public void setChildren(List<TSysAdminMenuExt> children) {
        this.children = children;
    }
}