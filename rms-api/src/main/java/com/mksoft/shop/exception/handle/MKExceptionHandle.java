package com.mksoft.shop.exception.handle;

import com.mksoft.shop.ErrorCode;
import com.mksoft.shop.exception.BaseException;
import com.mksoft.shop.exception.SysException;
import com.mksoft.shop.service.bo.ResultBo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by chawenming on 2017/5/12.
 */
@ControllerAdvice
@ResponseBody
public class MKExceptionHandle {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    protected HttpServletResponse response;

    @ExceptionHandler(BaseException.class)
    protected ResultBo processError(BaseException ex) {
        logger.error("MKExceptionHandle→BaseException:" + ex.getMessage());
        return setError(ex.getHttpStatus(), ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(SysException.class)
    protected ResultBo processError(SysException ex) {
        logger.error("MKExceptionHandle→SysException:" + ex.getMessage());
        ex.printStackTrace();
        return setError(ex.getHttpStatus(), ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler
    protected ResultBo processError(Exception ex) {
        logger.error("MKExceptionHandle→Exception:" + ex.getMessage());
        ex.printStackTrace();
        return setError(ex);
    }

    private ResultBo setError(HttpStatus httpStatus, ErrorCode errorCode, String msg) {
        ResultBo model = new ResultBo();

        model.setMsgCode(errorCode.value());

        if(StringUtils.isNotEmpty(msg)){
            model.setMsg(msg);
        } else {
            model.setMsg(errorCode.getMessage());
        }

        response.setStatus(httpStatus.value());

        return model;
    }

    private ResultBo setError(Exception ex) {
        ResultBo model = new ResultBo();

        model.setMsgCode(ErrorCode.DEFAULT_ERROR.value());
        model.setMsg(ex.getMessage());

        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return model;
    }
}
