package time;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author mz
 */
public class TimeClient {

    public void connect(int port,String host){
        /**
         * 创建客户端处理的I/O读写的NioEventLoopGroup线程组
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            /**
             * 创建客户端辅助启动类
             */
            Bootstrap b = new Bootstrap();
            /**
             * 配置
             */
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true).handler(
                    /**
                     * 再初始化化的时候将它的ChannelHandler设置到ChannelPipeline中 用于处理网络I/O事件
                     */
                    new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch){
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    }
            );
            /**
             * 发起异步连接操作
             */
            ChannelFuture f = b.connect(host,port);
            /**
             * 等待客户端链路关闭
             */
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }
    public static void main(String[] args){

        int port = 8080;
        if(args!=null&&args.length>0){
            try {
                port=Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        new TimeClient().connect(port,"127.0.0.1");
    }

}
