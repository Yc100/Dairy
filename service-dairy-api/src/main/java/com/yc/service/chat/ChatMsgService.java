/**
 * Copyright (c) 2020
 */

package com.yc.service.chat;

import com.yc.common.entity.sys.SysUserEntity;
import com.yc.common.page.PageData;
import com.yc.common.service.BaseService;
import com.yc.netty.ChatMsg;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 * @author YC
 */
public interface ChatMsgService extends BaseService<com.yc.common.entity.chat.ChatMsg> {


	Long saveMsg(ChatMsg chatMsg);

	void updateMsgSigned(List<Long> msgIds);

	Map<String,Object> getOneFriend(Long userId);

}