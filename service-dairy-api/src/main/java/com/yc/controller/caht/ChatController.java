/**
 * Copyright (c) 2020
 */

package com.yc.controller.caht;

import com.yc.common.constant.Constant;
import com.yc.common.entity.dairy.DairyEntity;
import com.yc.common.entity.dairy.DairyReadEntity;
import com.yc.common.entity.sys.SysUserEntity;
import com.yc.common.page.PageData;
import com.yc.common.utils.Result;
import com.yc.controller.BasicController;
import com.yc.dto.ChatFriendDto;
import com.yc.dto.ChatMsgDto;
import com.yc.service.chat.ChatMsgService;
import com.yc.service.dairy.DairyService;
import com.yc.service.security.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author YC
 * @createDate 2020/10/21 14:28
 **/
@RestController
@RequestMapping("/dairy/chat")
@Api(tags="API Dairy管理")
public class ChatController extends BasicController {

	@Autowired
	private ChatMsgService chatMsgService;


	@ApiOperation(value = "getOne",notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "friendId", value = "friendId", paramType = "query", dataType="long")
	})
	@GetMapping("/getOneFriend")
	public Result getOne(@RequestParam(value = "friendId") Long friendId) {
		ChatFriendDto chatFriendDto = chatMsgService.getOneFriend(getUserId(),friendId);
		return new Result().ok(chatFriendDto);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "friendId", value = "friendId", paramType = "query", dataType="long")
	})
	@GetMapping("/getFriendMsgHistoryList")
	public Result getFriendMsgHistoryList(@RequestParam(value = "friendId") Long friendId) {
		List<ChatMsgDto> chatMsgDtoList = chatMsgService.getFriendMsgHistoryList(getUserId(),friendId);
		return new Result().ok(chatMsgDtoList);
	}




}