package com.mksoft.shop.service;

import com.mksoft.shop.model.TSysAdminLog;
import com.mksoft.shop.service.bo.ResultBo;

/**
 * Created by YANGL on 2017/6/15.
 */
public interface AdminLogService {
    ResultBo logAccess();

    ResultBo logAccess(Integer loginPkid, String loginUUID);

    ResultBo logAccess(TSysAdminLog tSysAdminLog);
}
