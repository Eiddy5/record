package org.example.netty.sample.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.example.netty.sample.handler.TestHttpServerHandler;

import java.net.InetSocketAddress;

public class TestServer {


    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(io.netty.channel.ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new TestServerInitializer());
//            ChannelFuture channelFuture = serverBootstrap.bind(6677).sync();
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress("localhost", 6678)).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
