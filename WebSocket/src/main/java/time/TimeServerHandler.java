package time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @author mz
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * TimeServerHandler继承ChannelInboundHandlerAdapter 用于对网络事件进行读写操作 通常我们只关注channelRead方法和exceptionCaught方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 做类型转换 将msg转化为netty的ByteBuf对象
         */
        ByteBuf buf = (ByteBuf) msg;
        /**
         * 获取缓冲区可读的字节数 根据可读字节数创建byte数组
         */
        byte[] req = new byte[buf.readableBytes()];
        /**
         * 将缓冲区的字节数复制到新建的byte数组
         */
        buf.readBytes(req);
        /**
         *对请求消息进行判断 如果是"QUERY TIME ORDER" 则创建应答消息  通过ChannelHandlerContext的write方法异步发送应答消息给客户端
         */
        String body = new String(req,"UTF-8");
        System.out.println("The time server receive order: "+body);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(
                System.currentTimeMillis()).toString():"BAD ORDER";
                ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
                ctx.write(resp);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /**
         * 将消息发送队列中的消息写入到SocketChannel中发送给对方  通过调用write方法将消息发送到缓冲数组中 再调用flush方法将
         * 缓冲区的消息全部写入SocketChannel
         */
        ctx.flush();

    }

    /**
     * 发生异常时关闭资源 打印异常信息
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
