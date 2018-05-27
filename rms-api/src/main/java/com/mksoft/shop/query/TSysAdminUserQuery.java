package com.mksoft.shop.query;

import com.mksoft.shop.model.TSysAdminUser;

import java.util.List;
import java.util.Map;

public interface TSysAdminUserQuery {
    List<Map> search(Map param);
    Integer count(Map param);
    Integer isRoleExit(Map param);
    List<TSysAdminUser> getLoginAdminUser(Map param);
}