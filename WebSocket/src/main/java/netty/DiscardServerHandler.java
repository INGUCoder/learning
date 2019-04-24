package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author mz
 * 处理服务端 channel
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 丢弃收到的数据
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){

        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()){
                System.out.print((char) in.readByte());
                System.out.flush();
            }
        }finally {
            ((ByteBuf) msg).release();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        /**
         *
         * 当出现异常就关闭连接
         */
        cause.printStackTrace();
        ctx.close();

    }
}
