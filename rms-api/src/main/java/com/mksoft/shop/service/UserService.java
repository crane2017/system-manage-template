package com.mksoft.shop.service;

import com.mksoft.shop.exception.NoSuchUserException;
import com.mksoft.shop.model.TMCustomer;
import com.mksoft.shop.model.TSysAdminUser;

public interface UserService {
    public TSysAdminUser getLoginAdminUser(String login);

    public TSysAdminUser getLoginAdminUser(String login, String password);

    public TMCustomer getLoginAppUser(Integer userPkid);

    public TMCustomer getLoginAppUser(String login);

    public TMCustomer getLoginAppUser(String login, String password, int loginType) throws NoSuchUserException;

    public Boolean checkUserExist(String account);

//    public Boolean checkWxUserExist(String account);

    boolean checkMobileExist(String phone);
}
