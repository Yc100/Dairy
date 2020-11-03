package com.yc.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WebSocketServer {

    private static class SingletionWSServer {
        static final WebSocketServer instance = new WebSocketServer();
    }

    public static WebSocketServer getInstance() {
        return SingletionWSServer.instance;
    }

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public WebSocketServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitialzer());
    }

    public void start() {
        try {
            this.future = server.bind("127.0.0.1",8088).sync();
            //this.future = server.bind("172.168.1.177",8088);
            //this.future = server.bind("localhost",8088);
            Thread.sleep(1000);
            while (!this.future.isSuccess()){
                log.info("Netty 连接失败 !!!");
                this.future = server.bind("127.0.0.1",8088);
                Thread.sleep(1000);
            }
            if (future.isSuccess()) {
                log.info("Netty 服务启动成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
