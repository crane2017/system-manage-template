package com.mksoft.shop;

/**
 * Created by chawenming on 2017/5/9.
 */
public enum ErrorCode {
    USER_HAS_APPLIED("1002", "该用户已提交过申请"),
    USER_EXISTS("1003", "用户已存在"),
    NO_PRIVILEGES("1004", "用户没有该操作权限"),
    LOGIN_FAILED("1005", "用户名或密码错误"),
    NO_SUCH_USER("1006", "用户不存在"),
    SMS_VERIFICATION_CODE_INVALID("1007", "SMS验证码不正确"),
    DUPLICATE_NEW_PHONE("1011", "新电话号码已存在"),
    DEFAULT_ERROR("0000", "系统错误");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String value() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
