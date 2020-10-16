package com.yc.common.exception;

import com.yc.common.utils.MessageUtils;

/**
 * 未登陆，未通过验证
 *
 * @author YC
 */
public class UnAuthorizationException extends RuntimeException {

    private static final long serialVersionUID = -4145119178879270040L;

    private int code;
    private String msg;

    public UnAuthorizationException(int code) {
        this.code = code;
        this.msg = MessageUtils.getMessage(code);
    }

    /*public UnAuthorizationException(int code,String msg){
        this.code = code;
        this.msg=msg;
    }*/
    public UnAuthorizationException(int code, String... params) {
        this.code = code;
        this.msg = MessageUtils.getMessage(code, params);
    }

    public UnAuthorizationException(int code, Throwable e) {
        super(e);
        this.code = code;
        this.msg = MessageUtils.getMessage(code);
    }
    public UnAuthorizationException(int code, String msg){
        this.code=code;
        this.msg=msg;
    }

    public UnAuthorizationException(int code, Throwable e, String... params) {
        super(e);
        this.code = code;
        this.msg = MessageUtils.getMessage(code, params);
    }

    public UnAuthorizationException(String msg) {
        super(msg);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

    public UnAuthorizationException(String msg, Throwable e) {
        super(msg, e);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
