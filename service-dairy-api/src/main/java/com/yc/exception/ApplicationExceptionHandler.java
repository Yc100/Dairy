/**
 * Copyright (c) 2020
 */

package com.yc.exception;

import com.yc.common.exception.ErrorCode;
import com.yc.common.exception.ApplicationException;
//import com.yc.common.api.log.entity.SysLogErrorEntity;
//import com.yc.common.api.log.service.SysLogErrorService;
import com.yc.common.exception.UnAuthorizationException;
import com.yc.common.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description: 异常处理器
 * @author YC
 * @date 2019-3-28 17:06
 */
@RestControllerAdvice(value = {"com.yc"})
public class ApplicationExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

	//@Autowired
	//private SysLogErrorService sysLogErrorService;

	@ExceptionHandler(UnAuthorizationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public Result unAuthorization(UnAuthorizationException ex) {
		Result result = new Result();
		result.setCode(ex.getCode());
		result.setMsg(ex.getMsg());

		return result;
	}

	/**
	 * 处理断言自定义异常返回
	 */
	@ExceptionHandler(ApplicationException.class)
	public Result handleApplicationException(ApplicationException ex){
		Result result = new Result();
		result.error(ex.getCode(), ex.getMsg());

		return result;
	}

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public Result handleIllegalArgumentException(IllegalArgumentException ex){
		Result result = new Result();
		result.error(-1, ex.getMessage());

		return result;
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public Result handleDuplicateKeyException(DuplicateKeyException ex){
		Result result = new Result();
		result.error(ErrorCode.DB_RECORD_EXISTS);

		return result;
	}

	@ExceptionHandler(Exception.class)
	public Result handleException(Exception ex){
		logger.error(ex.getMessage(), ex);

		saveLog(ex);

		return new Result().error();
	}

	/**
	 * 保存异常日志
	 */
	private void saveLog(Exception ex){
		/*
	    SysLogErrorEntity log = new SysLogErrorEntity();

		//请求相关信息
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		log.setIp(IpUtils.getIpAddr(request));
		log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
		log.setRequestUri(request.getRequestURI());
		log.setRequestMethod(request.getMethod());
		Map<String, String> params = HttpContextUtils.getParameterMap(request);
		if(MapUtil.isNotEmpty(params)){
			log.setRequestParams(JSON.toJSONString(params));
		}
		if(request.getParameter("type")!=null){

		}
		if(request.getParameter("userId")!=null){
			log.setCreator(request.getParameter("userId"));
		}
		log.setCreateDate(new Date());
		//异常信息
		log.setErrorInfo(ExceptionUtils.getErrorStackTrace(ex));
		//保存
		sysLogErrorService.save(log);
		*/
	}
}