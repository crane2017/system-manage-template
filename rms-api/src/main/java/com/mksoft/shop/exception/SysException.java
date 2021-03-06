package com.mksoft.shop.exception;

import com.mksoft.shop.ErrorCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Created by chawenming on 2017/5/9.
 */
@Data
public class SysException extends Exception {
    private ErrorCode errorCode = ErrorCode.DEFAULT_ERROR;
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    public SysException(){
        super();
    }
    public SysException(String msg){
        super(msg);
    }
}
