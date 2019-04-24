import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author mz
 * socket
 */
public class EchoClient {
    private final Socket socket;

    /**
     * 创建socket并连接服务器
     * @param  host port
     */
    public EchoClient(String host,int port) throws IOException {
        socket = new Socket(host,port);
    }
    /**
     * 和服务端进行通信
     */
    public void run() throws IOException {
        Thread readThread = new Thread(this::readResponse);
        /*Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                readResponse();
            }
        });*/
        readThread.start();

        OutputStream out = socket.getOutputStream();
        byte[] buffer = new byte[1024];
        int n;
        while ((n=System.in.read(buffer))>0){
            out.write(buffer,0,n);
        }
    }

    private void readResponse(){
        try {
            InputStream in = socket.getInputStream();


            byte[] buffer = new byte[1024];
            int n ;
            while ((n=in.read(buffer))>0){
                System.out.write(buffer,0,n);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void  main(String[] args){
        try {
            /**
             * 由于服务端和客户端运行在同一主机  我们这里就用localhost
             */
            EchoClient client = new EchoClient("localhost",9000);
            client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
