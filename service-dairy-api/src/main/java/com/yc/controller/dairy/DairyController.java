/**
 * Copyright (c) 2020
 */

package com.yc.controller.dairy;

import com.yc.common.constant.Constant;
import com.yc.common.entity.dairy.DairyEntity;
import com.yc.common.entity.dairy.DairyReadEntity;
import com.yc.common.entity.sys.SysUserEntity;
import com.yc.common.exception.ApplicationException;
import com.yc.common.page.PageData;
import com.yc.common.utils.Result;
import com.yc.controller.BasicController;
import com.yc.service.dairy.DairyService;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Description
 *
 * @author YC
 * @createDate 2020/10/21 14:28
 **/
@RestController
@RequestMapping("/manage/dairy")
@Api(tags="API Dairy管理")
public class DairyController extends BasicController {
	@Autowired
	private DairyService dairyService;

	@Autowired
	private SysUserService sysUserService;

	@ApiOperation(value = "所有dairy列表",notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = Constant.OFFSET, value = "偏移量，从0开始", paramType = "query", required = true, dataType="int") ,
			@ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
			@ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
			@ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String"),
			@ApiImplicitParam(name = "title", value = "标题 模糊查询", paramType = "query", dataType="String"),
	})
	@GetMapping("/listPage")
	public Result<PageData<DairyEntity>> listPage(@ApiIgnore @RequestParam Map<String, Object> params) {

		Integer offset = Integer.valueOf(params.get(Constant.OFFSET).toString());
		Integer limit = Integer.valueOf(params.get(Constant.LIMIT).toString());
		Integer pageNo = offset / (limit<=0 ? 1 : limit) + 1;
		params.put(Constant.PAGE,pageNo.toString());
		PageData<DairyEntity> page = dairyService.getListPage(params);

		return new Result<PageData<DairyEntity>>().ok(page);
	}

	@ApiOperation(value = "getOne",notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dairyId", value = "dairyId", paramType = "query", dataType="long")
	})
	@GetMapping("/getOne")
	public Result<DairyEntity> getOne(@RequestParam(value = "dairyId") long dairyId) {

		DairyEntity dairyEntity = dairyService.selectById(dairyId);
		SysUserEntity sysUserEntity = sysUserService.getUserInfo(dairyEntity.getUserId());
		dairyEntity.setSysUserEntity(sysUserEntity);
		dairyEntity.setReadCount(dairyService.getReadCountByDairyId(dairyId));
		return new Result<DairyEntity>().ok(dairyEntity);
	}



	@ApiOperation(value = "readDairy",notes = "")
	@PostMapping("/readDairy")
	public Result readDairy(@RequestBody Map<String,Long> param){
		Long dairyId = param.get("dairyId");
		Long userId = getUser().getId();

		Long readCountNum = dairyService.getReadCount(dairyId,userId);

		DairyReadEntity dairyReadEntity = new DairyReadEntity();
		dairyReadEntity.setReadCount(BigDecimal.ONE);
		dairyReadEntity.setDairyId(dairyId);
		dairyReadEntity.setUserId(userId);
		if(readCountNum>0){//修改
			dairyService.dairyReadCountAdd(dairyReadEntity);
		}else{//新增
			dairyService.saveDairyRead(dairyReadEntity);
		}

		return new Result().ok("成功");
	}


}