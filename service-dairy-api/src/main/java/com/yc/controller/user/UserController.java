/**
 * Copyright (c) 2020
 */

package com.yc.controller.user;

import com.yc.auth.Authorization;
import com.yc.common.constant.Constant;
import com.yc.common.entity.sys.SysUserEntity;
import com.yc.common.exception.ApplicationException;
import com.yc.common.exception.ErrorCode;
import com.yc.common.page.PageData;
import com.yc.common.utils.Result;
import com.yc.controller.BasicController;
import com.yc.security.PasswordUtils;
import com.yc.service.security.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/**
 * 用户
 * 
 * @author YC
 */
@RestController
@RequestMapping("/user")
@Api(tags="API用户管理管理")
public class UserController extends BasicController {
	@Autowired
	private SysUserService sysUserService;

	@PostMapping("/create")
	@ApiOperation(value = "创建用户")
	public Result login(HttpServletRequest request, @RequestBody SysUserEntity sysUserEntity) {
		if(ObjectUtils.isEmpty(sysUserEntity)){
			throw new ApplicationException("弄啥嘞 怎么能传空呢？");
		}
		sysUserEntity.setCreator(getUser().getId());
		sysUserEntity.setCreateDate(new Date());
		sysUserEntity.setStatus(1);
		sysUserEntity.setSuperAdmin(0);
		if(!StringUtils.isNotEmpty(sysUserEntity.getHeadUrl())){
			sysUserEntity.setHeadUrl("http://ec.aiyangniu.net/files/file/20201019/1603086392324.jpg");
		}
		sysUserService.saveUser(sysUserEntity);
		return new Result().ok("用户创建成功");
	}

	@ApiOperation(value = "所有用户列表",notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = Constant.OFFSET, value = "偏移量，从0开始", paramType = "query", required = true, dataType="int") ,
			@ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
			@ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
			@ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String"),
			@ApiImplicitParam(name = "nickName", value = "匿名 模糊查询", paramType = "query", dataType="String"),
	})
	@GetMapping("/listPage")
	public Result<PageData<SysUserEntity>> getVaccinationListByLivestockId(@ApiIgnore @RequestParam Map<String, Object> params) {

		Integer offset = Integer.valueOf(params.get(Constant.OFFSET).toString());
		Integer limit = Integer.valueOf(params.get(Constant.LIMIT).toString());
		Integer pageNo = offset / (limit<=0 ? 1 : limit) + 1;
		params.put(Constant.PAGE,pageNo.toString());
		PageData<SysUserEntity> page = sysUserService.getListPage(params);

		return new Result<PageData<SysUserEntity>>().ok(page);
	}

	@PostMapping("/delete")
	public Result deleteUser(@RequestBody Map<String,Long> params){
		SysUserEntity sysUserEntity = new SysUserEntity();
		sysUserEntity.setId(params.get("userId"));
		sysUserEntity.setDeleted("1");
		sysUserService.updateById(sysUserEntity);
		return new Result().ok("删除成功");
	}



	@ApiOperation(value = "所有用户列表",notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = Constant.OFFSET, value = "偏移量，从0开始", paramType = "query", required = true, dataType="int") ,
			@ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
			@ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
			@ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String"),
			@ApiImplicitParam(name = "nickName", value = "匿名 模糊查询", paramType = "query", dataType="String"),
	})
	@GetMapping("/myFocusListPage")
	public Result<PageData<SysUserEntity>> myFocusListPage(@ApiIgnore @RequestParam Map<String, Object> params) {

		Integer offset = Integer.valueOf(params.get(Constant.OFFSET).toString());
		Integer limit = Integer.valueOf(params.get(Constant.LIMIT).toString());
		Integer pageNo = offset / (limit<=0 ? 1 : limit) + 1;
		params.put(Constant.PAGE,pageNo.toString());
		params.put("userId",getUser().getId());
		PageData<SysUserEntity> page = sysUserService.myFocusListPage(params);

		return new Result<PageData<SysUserEntity>>().ok(page);
	}


}