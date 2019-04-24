import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author mz
 *  服务端
 */
public class EchoServer {
    private final ServerSocket serverSocket;


    public EchoServer(int port) throws IOException {
        /**
         * 创建一个serverSocket并监听 port
         */
        serverSocket = new ServerSocket(port);
    }
    /**
     * 开始等待并接受客户端连接
     */
    public void run() throws IOException {

        Socket client =  serverSocket.accept();
        /**
         * 进行socket通信并处理
         */
        handleClient(client);
    }
    /**
     * 进行socket通信
     */
    private void handleClient(Socket socket) throws IOException {
        /**
         * 获取输入流
         */
        InputStream in = socket.getInputStream();
        /**
         * 输出流
         */
        OutputStream out = socket.getOutputStream();
        /**
         * 设置缓冲区 1024个字节
         */
        byte[] buffer = new byte[1024];
        int n;
        /**
         * 不断读取来自客户端的数据流 然后写回给客户端
         */
        while ((n=in.read(buffer))>0){
            out.write(buffer,0,n);



        }
    }
    public static void main(String[] args){
        try{
            EchoServer server = new EchoServer(9000);
            server.run();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
