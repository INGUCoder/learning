package time;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * @author mz
 */
public class TimeServer {

    public void bind(int port)throws Exception{
        /**NioEventLoopGroup 是线程组  它包含了一组NIO线程 专门处理网络事件 实际上它们就是Reactor线程组
         *Reactor线程是多面手，负责多路分离套接字，Accept新连接，并分派请求到处理器链中。
         */

        /**
         *用于服务端接受客户端的连接
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        /**
         * 用于进行SocketChannel的网络读写
         */
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            /**
             * 用于启动NIO服务端的辅助启动类 降低服务端的开发难度
             */
            ServerBootstrap b = new ServerBootstrap();
            /**
             *1：将两个NIO线程组当作参数传到ServerBootstrap中
             * 2:设置创建的Channel 为NioServerSocketChannel 它对应于JDK NIO类库中的ServerSocketChannel
             * 3：配置NioServerSocketChannel的Tcp参数   BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。
             * 4：绑定I/O事件的处理类ChildChannelHandler  它的作用模式类似于Reactor模式中的handler类 主要处理网络I/O事件 列如记录日志 对消息进行编解码等
             */

            b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG,1024).childHandler(new
                    ChildChannelHandler());
            /**
             * 服务端启动辅助类配置完成后 调用它的bind方法绑定监听端口 随后调用它的同步阻塞方法sync等待绑定操作完成 完成后Netty会返回一个ChannelFuture 类似于jdk中
             * 的java.util.concurrent.Future包  主要用于异步操作的通知回调
             */
            ChannelFuture f = b.bind(port).sync();
            /**
             * 进行阻塞 等待服务端链路关闭之后 main函数才退出
             */
            f.channel().closeFuture().sync();
        }finally {
            /**
             * 优雅退出 相关资源
             */
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{
        @Override
        protected void initChannel(SocketChannel ch){
            ch.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        int port  = 8080;
        if(args!=null&&args.length>0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        new TimeServer().bind(port);
    }

}
