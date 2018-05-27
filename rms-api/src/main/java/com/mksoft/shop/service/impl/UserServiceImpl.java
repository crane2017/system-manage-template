package com.mksoft.shop.service.impl;

import com.mksoft.shop.Constants;
import com.mksoft.shop.exception.NoSuchUserException;
import com.mksoft.shop.model.TMCustomer;
import com.mksoft.shop.model.TMCustomerCriteria;
import com.mksoft.shop.model.TSysAdminUser;
import com.mksoft.shop.model.TSysAdminUserCriteria;
import com.mksoft.shop.model.mapper.TMCustomerMapper;
import com.mksoft.shop.model.mapper.TSysAdminUserMapper;
import com.mksoft.shop.query.TSysAdminUserQuery;
import com.mksoft.shop.service.UserService;
import com.mksoft.shop.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Resource
    private TSysAdminUserMapper tAdminUserMapper;
    @Resource
    private TMCustomerMapper userMapper;

    @Resource
    private TSysAdminUserQuery tSysAdminUserQuery;

    public TSysAdminUser getLoginAdminUser(String login) {
        TSysAdminUser tAdminUser = null;

        if (StringUtils.isNotEmpty(login)) {
//            TSysAdminUserCriteria tAdminUserCriteria = new TSysAdminUserCriteria();
//            tAdminUserCriteria.createCriteria()
//                    .andAdminUserLoginEqualTo(login)
//                    .andVerEqualTo(Constants.VER_0)
//                    .andStartDateLessThan(new Date())
//                    .andExpireDateGreaterThan(new Date());
//
//            List<TSysAdminUser> rows = tAdminUserMapper.selectByExample(tAdminUserCriteria);

            Map params = new HashMap();
            params.put("adminUserLogin", login);
            List<TSysAdminUser> rows = tSysAdminUserQuery.getLoginAdminUser(params);

            if (rows != null && rows.size() > 0) {
                tAdminUser = rows.get(0);
            }
        }

        return tAdminUser;
    }

    public TSysAdminUser getLoginAdminUser(String login, String password) {
        TSysAdminUser tAdminUser = null;

        if (StringUtils.isNotEmpty(login) && StringUtils.isNotEmpty(password)) {
//            TSysAdminUserCriteria tAdminUserCriteria = new TSysAdminUserCriteria();
//            tAdminUserCriteria.createCriteria()
//                    .andAdminUserLoginEqualTo(login)
//                    .andAdminUserPwdEqualTo(SecurityUtil.pwdEncode(password))
//                    .andVerEqualTo(Constants.VER_0)
//                    .andStartDateLessThan(new Date())
//                    .andExpireDateGreaterThan(new Date());
//
//            List<TSysAdminUser> rows = tAdminUserMapper.selectByExample(tAdminUserCriteria);
            Map params = new HashMap();
            params.put("adminUserLogin", login);
            params.put("adminUserPwd", SecurityUtil.pwdEncode(password));
            List<TSysAdminUser> rows = tSysAdminUserQuery.getLoginAdminUser(params);

            if (rows != null && rows.size() > 0) {
                tAdminUser = rows.get(0);
            }
        }

        return tAdminUser;
    }

    public TMCustomer getLoginAppUser(Integer customerPkid) {
        TMCustomer user = null;

        if (customerPkid != null) {
            TMCustomerCriteria userCriteria = new TMCustomerCriteria();
            userCriteria.createCriteria()
                    .andCustomerPkidEqualTo(customerPkid)
                    .andVerEqualTo(Constants.VER_0);

            List<TMCustomer> rows = userMapper.selectByExample(userCriteria);

            if (rows != null && rows.size() > 0) {
                user = rows.get(0);
            }
        }

        return user;
    }

    public TMCustomer getLoginAppUser(String login) {
        TMCustomer user = null;

        if (StringUtils.isNotEmpty(login)) {
            TMCustomerCriteria userCriteria = new TMCustomerCriteria();
            userCriteria.createCriteria()
                    .andCustomerLoginEqualTo(login)
                    .andVerEqualTo(Constants.VER_0);

            List<TMCustomer> rows = userMapper.selectByExample(userCriteria);

            if (rows != null && rows.size() > 0) {
                user = rows.get(0);
            }
        }

        return user;
    }

    public TMCustomer getLoginAppUser(String login, String password, int loginType) throws NoSuchUserException {
        TMCustomer user = null;
        TMCustomerCriteria userCriteria = new TMCustomerCriteria();
        List<TMCustomer> rows = new ArrayList<>();
        if (loginType == 1) {
            if (StringUtils.isNotEmpty(login) && StringUtils.isNotEmpty(password)) {
                userCriteria.createCriteria()
                        .andCustomerMobileEqualTo(login)
                        .andCustomerPwdEqualTo(SecurityUtil.pwdEncode(password))
                        .andVerEqualTo(Constants.VER_0);
                rows = userMapper.selectByExample(userCriteria);
            }
        }

//        if (loginType == 2) {
//            if (StringUtils.isNotEmpty(login)) {
//                userCriteria.createCriteria()
//                        .andWechatUnionidEqualTo(login)
//                        .andVerEqualTo(Constants.VER_0);
//                rows = userMapper.selectByExample(userCriteria);
//                if(rows == null || rows.size() == 0){
//                    throw new NoSuchUserException();
//                }
//            }
//        }

        if (rows != null && rows.size() > 0) {
            user = rows.get(0);
        }

        return user;
    }



    public Boolean checkUserExist(String account) {
        Boolean userExsit = false;

        TMCustomerCriteria userCriteria = new TMCustomerCriteria();
        userCriteria.createCriteria()
                .andCustomerLoginEqualTo(account)
                .andVerEqualTo(Constants.VER_0);

        List<TMCustomer> rows = userMapper.selectByExample(userCriteria);

        if (rows != null && rows.size() > 0) {
            userExsit = true;
        }

        return userExsit;
    }

//    public Boolean checkWxUserExist(String account) {
//        Boolean userExsit = false;
//
//        TMCustomerCriteria userCriteria = new TMCustomerCriteria();
//        userCriteria.createCriteria()
//                .andWechatUnionidEqualTo(account)
//                .andVerEqualTo(Constants.VER_0);
//
//        List<TMCustomerExt> rows = userMapper.selectByExample(userCriteria);
//
//        if (rows != null && rows.size() > 0) {
//            userExsit = true;
//        }
//
//        return userExsit;
//    }

    @Override
    public boolean checkMobileExist(String phone) {
        Boolean userExsit = false;

        TMCustomerCriteria userCriteria = new TMCustomerCriteria();
        userCriteria.createCriteria()
                .andCustomerMobileEqualTo(phone)
                .andVerEqualTo(Constants.VER_0);

        List<TMCustomer> rows = userMapper.selectByExample(userCriteria);

        if (rows != null && rows.size() > 0) {
            userExsit = true;
        }

        return userExsit;
    }
}
