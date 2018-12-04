package cn.edu.dlmu.wechat.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class GetVoice {
    public static String getVoice(){
        //http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID

        return null;
    }

    /**
     * 根据文件id下载文件
     * @param mediaId 媒体id
     * @throws Exception
     */

    public static InputStream getInputStream(String accessToken, String mediaId) {
        InputStream is = null;
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="
                + accessToken + "&media_id=" + mediaId;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet
                    .openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            // 获取文件转化为byte流
            is = http.getInputStream();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;

    }

    /**
     * 获取下载图片信息（jpg）
     * @param mediaId 文件的id
     * @throws Exception
     */

    public static String saveVoiceToDisk(String path,String accessToken, String mediaId,String OpenID,int CaseID,int TaskID) {
        //String path = System.getProperty("user.dir")+"/evidence/tipreportmore";
        //String path = "D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore";
        System.out.println("语音---"+path);
        path = path + "/" +CaseID+"/"+ TaskID +"/" + OpenID+"/";
        //判断文件夹是否存在，不存在新建
        File file = new File(path);
        if(!file.exists()){
            //文件夹不存在，创建文件夹
            if(!file.mkdirs()){
                //文件夹创建失败
                System.out.println("文件夹创建失败");
                return null;
            }
        }
        //创建随机文件名
        String filename = UUID.randomUUID().toString();

        InputStream inputStream = getInputStream(accessToken, mediaId);

        byte[] data = new byte[10240];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(path+filename+".amr");
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        String sPath = path+filename+".amr";
//        String tPath = path+filename+".mp3";
//        ChangeAudioFormat.changeToMp3(sPath,tPath);
        return path+filename+".amr";
    }
}
