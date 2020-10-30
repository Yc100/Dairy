/**
 * Copyright (c) 2020
 */

package com.yc.service.chat.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yc.common.entity.sys.SysUserEntity;
import com.yc.common.enums.MsgSignFlagEnum;
import com.yc.common.page.PageData;
import com.yc.common.service.impl.BaseServiceImpl;
import com.yc.dao.chat.ChatMsgDao;
import com.yc.dao.security.SysUserDao;
import com.yc.netty.ChatMsg;
import com.yc.service.chat.ChatMsgService;
import com.yc.service.security.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author YC
 */
@Service
@Transactional
public class ChatMsgServiceImpl extends BaseServiceImpl<ChatMsgDao, com.yc.common.entity.chat.ChatMsg> implements ChatMsgService {

    @Autowired
    private ChatMsgDao chatMsgDao;

    @Override
    public Long saveMsg(ChatMsg chatMsg) {
        com.yc.common.entity.chat.ChatMsg chatMsgDB = new com.yc.common.entity.chat.ChatMsg();
        chatMsgDB.setMsg(chatMsg.getMsg());
        chatMsgDB.setSendUserId(chatMsg.getSenderId());
        chatMsgDB.setAcceptUserId(chatMsg.getReceiverId());
        chatMsgDB.setCreateTime(new Date());
        chatMsgDB.setSignFlag(MsgSignFlagEnum.unsign.type);
        chatMsgDao.saveMsg(chatMsgDB);
        return chatMsgDB.getId();
    }

    @Override
    public void updateMsgSigned(List<Long> msgIds) {
        chatMsgDao.updateMsgSigned(msgIds);
    }

    @Override
    public Map<String, Object> getOneFriend(Long userId) {
        return chatMsgDao.getOneFriend(userId);
    }
}
