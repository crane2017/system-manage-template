package com.mksoft.shop.exception;

import com.mksoft.shop.ErrorCode;
import lombok.Data;

/**
 * Created by chawenming on 2017/5/9.
 */
@Data
public class NoSuchUserException extends BaseException {
    private ErrorCode errorCode = ErrorCode.NO_SUCH_USER;
    public NoSuchUserException(){
        super();
    }
    public NoSuchUserException(String msg){
        super(msg);
    }
}
