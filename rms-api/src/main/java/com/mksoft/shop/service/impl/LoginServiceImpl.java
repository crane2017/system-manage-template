package com.mksoft.shop.service.impl;

import com.mksoft.shop.Constants;
import com.mksoft.shop.config.jwt.JwtHelper;
import com.mksoft.shop.config.sms.CLSmsHelper;
import com.mksoft.shop.exception.LoginFailedException;
import com.mksoft.shop.exception.NoSuchUserException;
import com.mksoft.shop.model.TSysAdminUser;
import com.mksoft.shop.model.TSysAdminUserCriteria;
import com.mksoft.shop.model.mapper.TSysAdminUserMapper;
import com.mksoft.shop.parameter.AdminLoginParameter;
import com.mksoft.shop.service.AdminLogService;
import com.mksoft.shop.service.LoginService;
import com.mksoft.shop.service.UserService;
import com.mksoft.shop.service.bo.ResultBo;
import com.mksoft.shop.util.RandomCodeCacheUtil;
import com.mksoft.shop.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Service(value = "loginService")
@Transactional(rollbackFor = Exception.class)
public class LoginServiceImpl implements LoginService {

    @Resource
    private JwtHelper jwtHelper;
    @Resource
    private TSysAdminUserMapper tAdminUserMapper;
    @Resource
    private UserService userService;
    @Resource
    private CLSmsHelper smsHelper;
    @Resource
    private AdminLogService adminLogService;

    @Override
    public ResultBo loginAdmin(AdminLoginParameter param) throws LoginFailedException {
        ResultBo bo = new ResultBo();

        String login = param.getAdminUserLogin();
        String password = param.getAdminUserPwd();

        TSysAdminUser tAdminUser = userService.getLoginAdminUser(login, password);

        if (tAdminUser == null) {
            throw new LoginFailedException();
        }

        Map userForReturn = new HashMap();
        Map userForCreateJwtHeader = new HashMap();

        Integer loginPkid = tAdminUser.getAdminUserPkid();
        String loginUUID = tAdminUser.getAdminUserUuid();

        adminLogService.logAccess(loginPkid,loginUUID);

        //TODO 存入token的用户信息项目，在此手动设置
        userForCreateJwtHeader.put(Constants.LOGIN_PKID_KEY, loginPkid);
        userForCreateJwtHeader.put(Constants.LOGIN_UUID_KEY, loginUUID);
        userForCreateJwtHeader.put(Constants.LOGIN_LOGIN_KEY, login);

        //TODO 需要返回的用户信息项目，在此手动设置
        userForReturn.put("adminUserLogin", login);
        userForReturn.put(Constants.LOGIN_UUID_KEY, loginUUID);
        userForReturn.put("adminUserName", tAdminUser.getAdminUserName());

        Map.Entry<String, String> headerEntry = jwtHelper.createJWTHeader(userForCreateJwtHeader);
        userForReturn.put(headerEntry.getKey(), headerEntry.getValue());

        bo.setRow(userForReturn);
        return bo;
    }

    @Override
    public ResultBo resetAdminPwd(Map param) throws InvocationTargetException, IllegalAccessException, NoSuchUserException {
        ResultBo bo = new ResultBo();
        TSysAdminUser tAdminUser = new TSysAdminUser();

        tAdminUser.setAdminUserLogin((String) param.get("login"));
        tAdminUser.setAdminUserPwd((String) param.get("password"));

        String encodePwd = SecurityUtil.pwdEncode(tAdminUser.getAdminUserPwd());
        String loginUUID = (String) param.get(Constants.LOGIN_UUID_KEY);

        tAdminUser.setAdminUserLogin(tAdminUser.getAdminUserLogin());
        tAdminUser.setAdminUserPwd(encodePwd);
        tAdminUser.setUid(loginUUID);

        if (userService.getLoginAdminUser(tAdminUser.getAdminUserLogin()) == null) {
            throw new NoSuchUserException();
        }

        TSysAdminUserCriteria tAdminUserCriteria = new TSysAdminUserCriteria();
        tAdminUserCriteria.createCriteria()
                .andVerEqualTo(Constants.VER_0)
                .andAdminUserLoginEqualTo(tAdminUser.getAdminUserLogin());
        bo.setOpResult(tAdminUserMapper.updateByExampleSelective(tAdminUser, tAdminUserCriteria));

        return bo;
    }

    @Override
    public ResultBo sendSmsVerificationCode(Map param) {
        ResultBo bo = new ResultBo();

        String phone = (String) param.get("phone");
        String smsVerificationCode = smsHelper.getRandomSmsVerificationCode();

        Boolean sendSuccess = smsHelper.sendMessage(phone, smsVerificationCode);

        if (sendSuccess) {
            RandomCodeCacheUtil.getInstance().put(phone, smsVerificationCode);
        }

        return bo;
    }
}
