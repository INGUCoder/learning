package websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;

/**
 * @author mz
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = Logger.getLogger(WebSocketServerHandshaker.class.getName());
    private WebSocketServerHandshaker handshaker;
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 传统的http接入
         *
         */
        if(msg instanceof FullHttpRequest){
            handHttpRequest(ctx,(FullHttpRequest) msg);

        }
        /**
         * websocket 接入
         */
        else if (msg instanceof WebSocketFrame){
            handleWebSocketFrame(ctx,(WebSocketFrame)msg);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        ctx.flush();

    }

    private void handHttpRequest(ChannelHandlerContext ctx,FullHttpRequest req){
        /**
         * 如果http解码失败 返回http异常
         */
        if (!req.getDecoderResult().isSuccess()||(!"websocket".equals(req.headers().get("UpGrade")))){

            sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,BAD_REQUEST));
            return;
        }
        /**
         * 构造握手响应返回 本机测试
         */
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket",null,false);
        handshaker  = wsFactory.newHandshaker(req);
        if(handshaker==null){

            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {

          handshaker.handshake(ctx.channel(),req);

        }
    }
    private void handleWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
        /**
         * 判断是否关闭链路的指令
         */
        if(frame instanceof CloseWebSocketFrame){
           handshaker.close(ctx.channel(),(CloseWebSocketFrame) frame.retain());
           return;
        }
        /**
         * 判断是否ping消息
         */
        if(frame instanceof PingWebSocketFrame){
            ctx.channel().write(ctx.channel(), (ChannelPromise) frame.retain());
            return;
        }

        /**
         * 返回应答消息
         */
        String request = ((TextWebSocketFrame) frame).text();
        if(logger.isLoggable(Level.FINE)){
            logger.fine(String.format("%s received % s",ctx.channel(),request));

        }
        ctx.channel().write(new TextWebSocketFrame(request+",欢迎使用netty websocket 服务 现在时刻是"+new java.util.Date().toString()));


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res){

        /**
         * 返回应答给客户端  200状态码 表示请求成功
         */

        if(res.getStatus().code()!=200){
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res,res.content().readableBytes());

        }
        /**
         * 如果是非keep-Alive连接  关闭连接
         */
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if(!isKeepAlive(req)||res.getStatus().code()!=200){

            f.addListener(ChannelFutureListener.CLOSE);
        }


    }

}
