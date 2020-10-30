package com.yc.netty;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户id 和channel 的关联关系处理
 */
public class UserChannelRel {
    private static HashMap<Long, Channel> manage = new HashMap<>();

    public static  void put(Long senderId,Channel channel){
        manage.put(senderId,channel);
    }

    public static Channel get(Long senderId){
        return manage.get(senderId);
    }

    public static void output(){
        for (Map.Entry<Long,Channel> entry  :manage.entrySet()) {
            System.out.println("UserId:"+entry.getKey()
                    +",ChannelId:"+entry.getValue().id().asLongText()
            );
        }
    }
}
