/**
 * Copyright (c) 2020
 */

package com.yc.dao.chat;

import com.yc.common.dao.BaseDao;
import com.yc.common.entity.chat.ChatMsg;
import com.yc.common.entity.sys.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author YC
 */
@Mapper
public interface ChatMsgDao extends BaseDao<ChatMsg> {

	void saveMsg(ChatMsg chatMsg);

	void updateMsgSigned(@Param("msgIds") List<Long> msgIds);

	Map<String,Object> getOneFriend(@Param("userId") Long userId);

}