package org.record.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.record.decoder.YjsDecoder;

public class SocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof TextWebSocketFrame) {
            String request = ((TextWebSocketFrame) frame).text();
            System.out.println("Received: " + request);
            ctx.channel().writeAndFlush(new TextWebSocketFrame("Echo: " + request));
        } else if (frame instanceof BinaryWebSocketFrame) {
            ByteBuf content = frame.content();
            byte[] bytes = new byte[content.readableBytes()];
            content.readBytes(bytes);

//            System.out.println("接收到二进制数据: " + bytes.length + " bytes");
//            System.out.println("Received: " + new String(bytes, StandardCharsets.UTF_8));
            YjsDecoder.decode(bytes);
//            System.out.println("接受到的数据是" + new String(bytes, StandardCharsets.UTF_8));
            ctx.channel().writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(bytes)));


        } else if (frame instanceof CloseWebSocketFrame) {
            ctx.channel().close();
        } else {
            throw new UnsupportedOperationException("Unsupported frame type: " + frame.getClass().getName());
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}
