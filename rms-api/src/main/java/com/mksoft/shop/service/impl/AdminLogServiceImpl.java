package com.mksoft.shop.service.impl;

import com.mksoft.shop.config.jwt.JwtHelper;
import com.mksoft.shop.model.TSysAdminLog;
import com.mksoft.shop.model.mapper.TSysAdminLogMapper;
import com.mksoft.shop.service.AdminLogService;
import com.mksoft.shop.service.bo.ResultBo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by YANGL on 2017/6/15.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AdminLogServiceImpl implements AdminLogService {

    @Resource
    private TSysAdminLogMapper tSysAdminLogMapper;

    @Resource
    private JwtHelper jwtHelper;

    @Resource
    private HttpServletRequest request;

    @Override
    public ResultBo logAccess() {
        ResultBo result = new ResultBo();
        Integer loginPkid = jwtHelper.getLoginPkid();
        String loginUUID = jwtHelper.getLoginUUID();
        TSysAdminLog tSysAdminLog = new TSysAdminLog();

        tSysAdminLog.setAdminUserPkid(loginPkid);
        tSysAdminLog.setLogIp(request.getRemoteAddr());
        tSysAdminLog.setLogUrl(request.getRequestURL().toString());
        tSysAdminLog.setCid(loginUUID);
        tSysAdminLog.setUid(loginUUID);

        result.setOpResult(tSysAdminLogMapper.insertSelective(tSysAdminLog));

        return result;
    }

    @Override
    public ResultBo logAccess(Integer loginPkid,String loginUUID) {
        ResultBo result = new ResultBo();
        TSysAdminLog tSysAdminLog = new TSysAdminLog();

        tSysAdminLog.setAdminUserPkid(loginPkid);
        tSysAdminLog.setLogIp(request.getRemoteAddr());
        tSysAdminLog.setLogUrl(request.getRequestURL().toString());
        tSysAdminLog.setCid(loginUUID);
        tSysAdminLog.setUid(loginUUID);

        result.setOpResult(tSysAdminLogMapper.insertSelective(tSysAdminLog));

        return result;
    }

    @Override
    public ResultBo logAccess(TSysAdminLog tSysAdminLog) {
        ResultBo result = new ResultBo();
        Integer loginPkid = jwtHelper.getLoginPkid();
        String loginUUID = jwtHelper.getLoginUUID();

        if (tSysAdminLog.getAdminUserPkid() == null) {
            tSysAdminLog.setAdminUserPkid(loginPkid);
        }

        if (StringUtils.isEmpty(tSysAdminLog.getLogIp())) {
            tSysAdminLog.setLogIp(request.getRemoteAddr());
        }

        if (StringUtils.isEmpty(tSysAdminLog.getLogUrl())) {
            tSysAdminLog.setLogUrl(request.getRequestURL().toString());
        }

        if (StringUtils.isEmpty(tSysAdminLog.getCid())) {
            tSysAdminLog.setCid(loginUUID);
        }

        if (StringUtils.isEmpty(tSysAdminLog.getUid())) {
            tSysAdminLog.setUid(loginUUID);
        }

        result.setOpResult(tSysAdminLogMapper.insertSelective(tSysAdminLog));

        return result;
    }
}
