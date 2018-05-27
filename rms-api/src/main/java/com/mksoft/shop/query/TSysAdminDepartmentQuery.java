package com.mksoft.shop.query;
import com.mksoft.shop.query.model.TSysAdminDepartmentExt;

import java.util.List;
import java.util.Map;

public interface TSysAdminDepartmentQuery {
    List<TSysAdminDepartmentExt> list();
    List<Map> search();
}