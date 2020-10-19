/**
 * Copyright (c) 2020
 */

package com.yc.controller.security;

import com.yc.auth.Authorization;
import com.yc.common.constant.Constant;
import com.yc.common.entity.sys.SysUserEntity;
import com.yc.common.exception.ApplicationException;
import com.yc.common.exception.ErrorCode;
import com.yc.common.utils.Result;
import com.yc.controller.BasicController;
import com.yc.security.PasswordUtils;
import com.yc.service.security.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * API登录
 * 
 * @author YC
 */
@RestController
@RequestMapping("/api")
@Api(tags="API登录管理")
public class ApiLoginController extends BasicController {
	@Autowired
	private SysUserService sysUserService;

	@PostMapping("/login")
	@ApiOperation(value = "登录")
	public Result login(HttpServletRequest request, @RequestBody SysUserEntity login) {
		if (StringUtils.isBlank(login.getUsername())) {
			throw new ApplicationException("用户名不能为空");
		}
		if (StringUtils.isBlank(login.getPassword())) {
			throw new ApplicationException("密码不能为空");
		}

		//用户信息
		SysUserEntity user = sysUserService.getByUsername(login.getUsername());

		//用户不存在
		if(user == null){

			throw new ApplicationException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
		}

		//密码错误
		if(!PasswordUtils.matches(login.getPassword(), user.getPassword())){

			throw new ApplicationException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
		}

		//账号停用
		if(user.getStatus() == 0){

			throw new ApplicationException(ErrorCode.ACCOUNT_DISABLE);
		}

		user.setPassword("");
		getSession().setAttribute(Constant.SYSTEM_USER_KEYWORD, user);

		return new Result().ok(getSession().getId());
	}

	@Authorization
	@GetMapping("/userInfo")
	@ApiOperation("查询登录用户信息")
	public Result<SysUserEntity> userInfo(){
		return new Result<SysUserEntity>().ok(getUser());
	}

	@Authorization
	@ApiOperation(value = "用户退出接口", notes = "用户退出", httpMethod = "GET")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public Result logout(@ApiIgnore HttpSession session) {
		session.invalidate();
		return new Result().message("退出成功");
	}

	@ApiOperation(value = "获取用户信息", notes = "获取用户信息")
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public Result<SysUserEntity> getUserInfo() {
		SysUserEntity userEntity = sysUserService.getUserInfo(getUserId());
		return new Result<SysUserEntity>().message("获取用户信息成功").ok(userEntity);
	}

}