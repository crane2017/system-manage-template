package com.mksoft.shop.exception;

import com.mksoft.shop.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by chawenming on 2017/5/9.
 */
@Data
public class BaseException extends Exception {
    private ErrorCode errorCode = ErrorCode.DEFAULT_ERROR;
    private HttpStatus httpStatus = HttpStatus.OK;
    public BaseException(){
        super();
    }
    public BaseException(String msg){
        super(msg);
    }
}
