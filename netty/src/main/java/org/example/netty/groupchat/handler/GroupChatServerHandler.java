package org.example.netty.groupchat.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;


public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    // 将所有连接用户广播到所有在线用户
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        // 将用户连接广播到所有用户
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天" + sdf.format(new Date()) + " \n");
        channelGroup.add(channel);
    }

    // 将离线用户广播到所有在线用户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "离开聊天" + sdf.format(new Date()) + " \n");
        System.out.printf("当前在线人数为[%s] \n", channelGroup.size());
    }

    // 表示channel处于active 状态，提示 channel上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.printf("客户端[%s]上线\n", ctx.channel().remoteAddress());
    }

    // 表示channel处于inactive 状态，提示 channel离线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.printf("客户端[%s]离线\n", ctx.channel().remoteAddress());
    }

    // 读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(c -> {
            if (channel != c) {
                // 转发消息
                c.writeAndFlush("[客户]" + c.remoteAddress() + "发送了消息：" + msg + "\n");
            } else {
                c.writeAndFlush("[自己]" + c.remoteAddress() + "发送了消息：" + msg + "\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}