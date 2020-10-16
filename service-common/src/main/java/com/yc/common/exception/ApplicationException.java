/**
 * Copyright (c) 2020
 */

package com.yc.common.exception;


import com.yc.common.utils.MessageUtils;

/**
 * 自定义异常
 *
 * @author YC
 * @createDate 2020/10/16 14:34
 **/
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private int code;
	private String msg;

	public ApplicationException(int code) {
		this.code = code;
		this.msg = MessageUtils.getMessage(code);
	}

	/*public ApplicationException(int code,String msg){
		this.code = code;
		this.msg=msg;
	}*/
	public ApplicationException(int code, String... params) {
		this.code = code;
		this.msg = MessageUtils.getMessage(code, params);
	}

	public ApplicationException(int code, Throwable e) {
		super(e);
		this.code = code;
		this.msg = MessageUtils.getMessage(code);
	}
	public ApplicationException(int code, String msg){
		this.code=code;
		this.msg=msg;
	}

	public ApplicationException(int code, Throwable e, String... params) {
		super(e);
		this.code = code;
		this.msg = MessageUtils.getMessage(code, params);
	}

	public ApplicationException(String msg) {
		super(msg);
		this.code = ErrorCode.INTERNAL_SERVER_ERROR;
		this.msg = msg;
	}

	public ApplicationException(String msg, Throwable e) {
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