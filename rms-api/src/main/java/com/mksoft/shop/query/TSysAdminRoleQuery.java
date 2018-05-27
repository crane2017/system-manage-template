package com.mksoft.shop.query;

import java.util.List;
import java.util.Map;

public interface TSysAdminRoleQuery {
    List<Map> search(Map param);

    Integer getRoleCount();
}