package time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * @author mz
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    private final ByteBuf fristMessage;

    public TimeClientHandler() {
        byte[] req = "QUERY TIME ORDER".getBytes();
        fristMessage = Unpooled.buffer(req.length);
        fristMessage.writeBytes(req);
    }

    /**
     * 当客户端和服务端的TCP链路建立成功后 Netty的NIO线程会调用channelActive方法 发送查询事件的指令给服务端 调用writeAndFlush方法
     * 将请求发送给服务端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush(fristMessage);
    }

    /**
     * 党服务端返回应答消息 channelRead 方法被调用
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 读取并打印应答消息
         */
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"UTF-8");
        System.out.println("Now is:"+body);

    }

    /**
     * 发生异常  释放客户端资源
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
