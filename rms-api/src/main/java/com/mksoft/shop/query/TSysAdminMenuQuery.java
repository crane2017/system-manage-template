package com.mksoft.shop.query;
import com.mksoft.shop.query.model.TSysAdminMenuExt;

import java.util.List;
import java.util.Map;

public interface TSysAdminMenuQuery {
    List<TSysAdminMenuExt> list();
    List<TSysAdminMenuExt> search();
    List<TSysAdminMenuExt> searchMenu(Map param);
}