import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author mz
 */
public class PipeExample {

    public static  void  main(String[] args) throws IOException {

        final PipedOutputStream outputStream = new PipedOutputStream();
        final PipedInputStream inputStream = new PipedInputStream(outputStream);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    outputStream.write("hello world".getBytes());
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int data = inputStream.read();
                    while (data!=-1){
                        System.out.print((char)data);
                        data = inputStream.read();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread2.start();



    }

}
