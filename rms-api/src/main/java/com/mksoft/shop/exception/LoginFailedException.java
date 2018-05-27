package com.mksoft.shop.exception;

import com.mksoft.shop.ErrorCode;
import lombok.Data;

/**
 * Created by chawenming on 2017/5/9.
 */
@Data
public class LoginFailedException extends BaseException {
    private ErrorCode errorCode = ErrorCode.LOGIN_FAILED;
    public LoginFailedException(){
        super();
    }
    public LoginFailedException(String msg){
        super(msg);
    }
}
