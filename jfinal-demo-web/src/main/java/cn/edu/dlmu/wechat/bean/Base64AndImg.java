package cn.edu.dlmu.wechat.bean;

import com.alibaba.fastjson.JSON;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class Base64AndImg {
    String path = "D:\\Jfinal-demo\\gzh";
    private boolean base64ToImg(String base){
        //对字节数组字符串进行Base64解码并生成图片
        if (base == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(base);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            Date date = new Date();
            String imgFilePath = path+"\\222"+date.getTime()+".jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public void base64ToImg(String base[]){

    }

    //遍历map 数组的方法
    //http://www.cnblogs.com/fczjuever/archive/2013/04/07/3005997.html
    public void FileRederPrase(String json){
        Map imgMap = (Map) JSON.parse(json);
        Iterator iterator = imgMap.keySet().iterator();
        Object key = null;
        String value = null;
        while(iterator.hasNext()){
            key = iterator.next();
            value = imgMap.get(key).toString();
            value = value.substring(value.indexOf(",")+1);
            System.out.println(value);
            base64ToImg(value);
        }
    }

}
