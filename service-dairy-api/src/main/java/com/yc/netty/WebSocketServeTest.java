package com.yc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WebSocketServeTest {
    public static void main(String[] args) {
        //创建一组线程池组
        //主线程池:用于接收客户端的请求连接，不做任何处理
        EventLoopGroup mainGroup = new NioEventLoopGroup();


        //从线程池:主线程池会把任务交给从线程池，让其做任务
        EventLoopGroup subGroup = new NioEventLoopGroup();

        try{
            //创建服务器启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(mainGroup,subGroup)//设置主从线程组
                    .channel(NioServerSocketChannel.class)//设置Nio双向通道
                    .childHandler(new WSServerInitialzer());//添加子处理器 用于处理从线程池的任务

            //启动服务 设置端口号 同时启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            //监听关闭的channel 设置为同步方式
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){

        }finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}
