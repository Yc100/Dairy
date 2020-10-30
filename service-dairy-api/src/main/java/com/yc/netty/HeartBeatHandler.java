package com.yc.netty;

import com.yc.SpringUtil;
import com.yc.common.enums.MsgActionEnum;
import com.yc.service.chat.impl.ChatMsgServiceImpl;
import com.yc.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 用于检测channel的心跳handler
 * 继承ChannelInboundHandlerAdapter，目的是不需要实现chann elRead0 方法
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)evt;//强制类型转换
            if(event.state() == IdleState.READER_IDLE){
                System.out.println("进入读空闲······");
            }else if (event.state() == IdleState.WRITER_IDLE){
                System.out.println("进入写空闲······");
            }else if (event.state() == IdleState.ALL_IDLE){
                System.out.println("channel 关闭之前:users组的数量为:"+ChatHandler.users.size());
                Channel channel = ctx.channel();
                //资源释放
                channel.close();
                System.out.println("channel 关闭之后:users组的数量为:"+ChatHandler.users.size());
            }
        }
    }
}
