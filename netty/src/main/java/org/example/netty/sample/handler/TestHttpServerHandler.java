package org.example.netty.sample.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;

public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.printf("对应的channel是[%s],pipeline是[%s],通过pipeline获取channel[%s]\n", ctx.channel(), ctx.pipeline(), ctx.channel().pipeline());
        System.out.printf("当前的handler是[%s]\n", ctx.handler());
        if (msg instanceof HttpRequest request) {

            System.out.printf("ctx的类型是[%s]\n", ctx.getClass());
            System.out.printf("msg的类型是[%s]\n", msg.getClass());
            System.out.printf("客户端地址是[%s]\n", ctx.channel().remoteAddress());

            URI uri = new URI(request.uri());
            System.out.printf("uri是[%s],port是[%s]\n", uri.getPath(), uri.getPort());
            ByteBuf content = Unpooled.copiedBuffer("hello , 我是服务器", StandardCharsets.UTF_8);
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ctx.writeAndFlush(response);
        }
    }
}
