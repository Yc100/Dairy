package com.yc.netty;

import com.yc.SpringUtil;
import com.yc.common.enums.MsgActionEnum;
import com.yc.service.chat.impl.ChatMsgServiceImpl;
import com.yc.service.security.SysUserService;
import com.yc.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 用于处理消息的handler
 * 由于它的传输数据的载体是frame，在netty中，是用于为WebSocket专门处理问对象的，frame是消息的载体，此类叫:TextWebSocketFrame
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用于记录和管理所有客户端的通道 channel
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext context, TextWebSocketFrame msg) throws Exception {
        /*//获取客户端传输的消息
        String text = msg.text();
        System.out.println("接收到的数据："+text);

        //将数据刷新到客户端上
        users.writeAndFlush(
                new TextWebSocketFrame(
                        "[服务器在:]"+ new Date().toLocaleString()
                         +"接收到消息，消息内容为:"+text
                )
        );*/

        //1、获取客户端发来的消息
        String text = msg.text();
        DataContent dataContent = JsonUtils.jsonToPojo(text, DataContent.class);
        Integer action = dataContent.getAction();
        Channel channel = context.channel();

        //2、判断消息类型，根据不同的类型来处理不同的业务
        if(action == MsgActionEnum.CONNECT.type){
            //2.1、 当webSocket第一次open的时候，初始化channel 吧channel和用户id关联起来
            Long senderId = dataContent.getChatMsg().getSenderId();
            UserChannelRel.put(senderId,channel);

            //测试
            for(Channel c:users){
                System.out.println(c.id().asLongText());
            }
            UserChannelRel.output();

        }else if (action == MsgActionEnum.CHAT.type){
            //2.2、聊天类型的消息，把聊天记录保存在数据库中，同时标记消息的签收状态[未签收]
            ChatMsg chatMsg = dataContent.getChatMsg();
            String msgContent = chatMsg.getMsg();
            Long senderId = chatMsg.getSenderId();
            Long receiverId = chatMsg.getReceiverId();
            //保存消息到数据库 并且标记为未签收
            ChatMsgServiceImpl chatMsgService = (ChatMsgServiceImpl) SpringUtil.getBean("chatMsgServiceImpl");
            Long msgId = chatMsgService.saveMsg(chatMsg);
            chatMsg.setMsgId(msgId);

            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setChatMsg(chatMsg);

            Channel receiveChannel = UserChannelRel.get(receiverId);
            if(receiveChannel == null){
                //离线用户
            }else{
                //当receiverChannel 不为空的时候，从ChannelGroup 去查找对应的channel 是否存在
                Channel findChannel = users.find(receiveChannel.id());
                if(findChannel != null){
                    //用户在线
                    receiveChannel.writeAndFlush(
                      new TextWebSocketFrame(JsonUtils.objectToJson(dataContentMsg))
                    );
                }else{
                    //离线用户
                }

            }

        }else if (action == MsgActionEnum.SIGNED.type){
            //2.3、签收消息类型，针对具体消息进行签收，修改数据库中对应消息的签收状态[已签收]
            ChatMsgServiceImpl chatMsgService = (ChatMsgServiceImpl) SpringUtil.getBean("chatMsgServiceImpl");
            //扩展字段在signed 类型消息中 ，代表需要去签收的消息id，逗号间隔
            String msgIdsStr = dataContent.getExtAnd();
            String[] msgsId = msgIdsStr.split(",");

            List<Long> msgIdList = new ArrayList<>();
            for (String mid: msgsId) {
                if(StringUtils.isNotBlank(mid)){
                    msgIdList.add(Long.valueOf(mid));
                }
            }
            System.out.println(msgIdList.toString());
            if(msgIdList!=null && !msgIdList.isEmpty() && msgIdList.size()>0){
                //批量签收
                chatMsgService.updateMsgSigned(msgIdList);
            }

        }else if (action == MsgActionEnum.KEEPALIVE.type){
            //2.4、心跳类型的消息
            //2.4 心跳类型的消息
            //System.out.println("收到来自channel 为【"+channel+"】的心跳包");
            String friendId = dataContent.getExtAnd();
            Channel receiveChannel = UserChannelRel.get(Long.valueOf(friendId));
            Channel findChannel ;
            if(receiveChannel !=null ){
                findChannel = users.find(receiveChannel.id());
            }else{
                findChannel =null;
            }

            DataContent dataContentPing = new DataContent();

            if(findChannel == null){//离线
                dataContentPing.setExtAnd("0");//离线
                System.out.println("收到来自channel 为【"+channel+"】的心跳包"+"[对方离线]");
            }else{//在线
                dataContentPing.setExtAnd("1");//在线
                System.out.println("收到来自channel 为【"+channel+"】的心跳包"+"[对方在线]");
            }
            channel.writeAndFlush( new TextWebSocketFrame(JsonUtils.objectToJson(dataContentPing)));
        }


    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开，channel对应的长ID为:"+ctx.channel().id().asLongText()+",短ID为:"+ctx.channel().id().asShortText());
        //String chaneelId = ctx.channel().id().asShortText();
        users.remove(ctx.channel());


    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //发生异常后关闭连接，同时从channelGroup中移除
        users.remove(ctx.channel());
    }
}
