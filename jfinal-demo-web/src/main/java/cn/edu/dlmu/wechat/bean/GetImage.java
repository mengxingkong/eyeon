package cn.edu.dlmu.wechat.bean;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jfinal.kit.PropKit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

public class GetImage {

    //用户同时发送多条图片信息会报错
    //private static String path = "D:\\Jfinal-demo\\jfinal-demo-web\\src\\main\\webapp\\evidence\\tipreport\\";

    public static Boolean creatDir(String filePath){
        System.out.println("简单举报---"+filePath);
        File file = new File(filePath);
        if(!file.exists()){
            //文件夹不存在，创建文件夹
            if(!file.mkdirs()){
                //文件夹创建失败
                System.out.println("文件夹创建失败");
                return false;
            }
        }
        return true;
    }






    public static String downLoadImage(String imageUrl,String fileName){
        byte[] btimage = getImageFromNetByUrl(imageUrl);
        if(null!=btimage && btimage.length>0){
            writeImageToDIsk(btimage,fileName);
        }
        else{
            return null;
        }
        return fileName+".jpg";
    }

    public static void writeImageToDIsk(byte[] btimage,String fileName){
        try{
            File file = new File(fileName+".jpg");
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(btimage);
            fops.flush();
            fops.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public static byte[] getImageFromNetByUrl(String imageUrl){
        try{
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5*1000);
            InputStream inputStream = conn.getInputStream();
            byte[] btimage = readInputStream(inputStream);
            return btimage;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static byte[] readInputStream(InputStream inputStream) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = inputStream.read(buffer)) !=-1 ){
            outputStream.write(buffer,0,len);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }

    /**获取网页版的简单举报*/
    public static String[] getImageFromWeb(String path,String serverId,String OpenID,String AccessToken){

        String wexinImageUrl="https://api.weixin.qq.com/cgi-bin/media/get";
        JSONArray serverIdArray = JSON.parseArray(serverId);
        String[] imagePath = new String[serverIdArray.size()];

        creatDir(path);

        System.out.println("test"+path);

        for(int i=0;i<serverIdArray.size();i++){
            String url = wexinImageUrl+"?access_token="+AccessToken+"&media_id="+serverIdArray.getString(i);
            String filename = UUID.randomUUID().toString();
            imagePath[i] = downLoadImage(url,path+"/"+filename);
            System.out.println(path+"/"+filename);
        }
        return imagePath;
    }


    public static String downloadImage(String path2,String serverId,String OpenID,int TaskID,int CaseID,String AccessToken){

        String wexinImageUrl="https://api.weixin.qq.com/cgi-bin/media/get";
        //http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID

        String current_dir = System.getProperty("user.dir");
        System.out.println(current_dir);

        //String path2 = current_dir + "/evidence/tipreportmore";

        //String path2 = PropKit.use("config.properties").get("imgSave");


//        if(path2.endsWith("/")){
//            path2 = path2.substring(0,path2.length()-1);
//        }

        //String path2 = "D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreportMore";

        JSONArray serverIdArray = JSON.parseArray(serverId);

        String[] imagePath = new String[serverIdArray.size()];

        //判断事件文件夹是否存在

        System.out.println("复杂举报图片-----"+path2);
        String path = path2+"/"+CaseID+"/"+TaskID+"/"+OpenID+"/";
        System.out.println("************---"+path);
        File file = new File(path);
        if(!file.exists()){
            //文件夹不存在，创建文件夹
           if(!file.mkdirs()){
               //文件夹创建失败
               System.out.println("文件夹创建失败");
               return null;
           }
        }
        for(int i=0;i<serverIdArray.size();i++){
            String url = wexinImageUrl+"?access_token="+AccessToken+"&media_id="+serverIdArray.getString(i);
            String filename = UUID.randomUUID().toString();
            imagePath[i] = downLoadImage(url,path+filename);
        }
        return path;
    }
}
