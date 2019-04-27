package ocr;

import java.io.*;

/**文件读取工具类
 * @author mz
 */
public class FileUtil {

    /**
     * 读取文件内容  作为字符串返回
     */

    public static String readFileAsString(String filepath) throws IOException {

        File file = new File(filepath);
        if(!file.exists()){
            System.out.println("文件不存在");
            throw new FileNotFoundException(filepath);

        }
        if(file.length()>1024*1024*1024){
            throw new IOException("文件过大，建议放入小于1GB的文件");
        }
        StringBuilder sb = new StringBuilder((filepath.length()));
        /**
         * 创建字节输入流
         */
        FileInputStream fis = new FileInputStream(filepath);
        /**
         * 创建1个长度为10240的buffer  10M
         */
        byte[] buf = new byte[10240];
        /**
         * 用于保存实际读取的字节数
         */
        int hasRead = 0;
        while ((hasRead=fis.read(buf))>0){

            sb.append(new String(buf,0,hasRead));

        }
        fis.close();
        return sb.toString();
    }
    /**
    * 根据文件路径读取byte[] 数组
     */
    public static byte[] readFileByBytes(String filePath) throws IOException {

        File file = new File(filePath);
        if(!file.exists()){
            throw new FileNotFoundException(filePath);
        }else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;
            try{

                in = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int lenl;
                while (-1!=(lenl=in.read(buffer,0,bufSize))){
                    bos.write(buffer,0,lenl);

                }
                byte[] var7 = bos.toByteArray();
                return var7;
            }finally {
                try{
                    if (in!=null){
                        in.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
                bos.close();
            }
        }



    }




}
