package ocr;

import java.io.*;
import java.net.URLEncoder;
import com.baidu.aip.util.Base64Util;

/**
 * 上传图片检测
 */

public class PictureUtil {
    public static String pictureUtil(){
        /**
         * 请求url
         */
        String url = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/user_defined";
        try{

            /**
             * 本地文件路径
             */
            String filePath = "C:\\Users\\mz\\Desktop\\新建文件夹\\learning\\4.jpeg";
            byte[] imaData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imaData);
            String imgParam = URLEncoder.encode(imgStr,"UTF-8");
            String param = "image="+imgParam;
            String result = HttpUtil.post(url,AuthService.getAuth(),param);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
    public static void main(String[] args) throws IOException {
        PictureUtil.pictureUtil();
    }
}
