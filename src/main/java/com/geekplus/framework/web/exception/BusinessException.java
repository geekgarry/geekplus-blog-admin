package com.geekplus.framework.web.exception;

import com.geekplus.common.enums.ApiExceptionEnum;
import org.apache.commons.lang.StringUtils;

/**
 * 自定义通用异常
 */
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;
    private Object data;
    //异常模块
    private String module;

    public BusinessException(){
        ApiExceptionEnum exceptionEnum = ApiExceptionEnum.getByEClass(this.getClass());
        if (exceptionEnum != null) {
            code = exceptionEnum.getCode();
            msg = exceptionEnum.getMsg();
        }
    }

    public BusinessException(Integer code, String msg, Object data, String module) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.module = module;
    }

    public BusinessException(String format, Object... objects) {
        this(null, format+StringUtils.join(objects), null, null);
    }

    public BusinessException(ApiExceptionEnum apiEnum, Object data) {
        this(apiEnum.getCode(), apiEnum.getMsg(), data, null);
    }

    public BusinessException(ApiExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg(), null, null);
    }

    public BusinessException(Integer code,String message) {
        this(code, message, null, null);
    }

    public BusinessException(String message) {
        this(null, message, null, null);
    }

    public BusinessException(Exception e) {
        new BusinessException(e.getMessage());
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
