package cn.edu.dlmu.wechat.bean;

import cn.edu.dlmu.wechat.model.TipReport;
import cn.edu.dlmu.wechat.model.TipReportMore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PathBulid {

    public static void buildPath(List tipreports ){
        //遍历 tipreport 取出 路径 更改保存

        //如果时简单举报  这么操作

        //如果时复杂举报  怎么操作

        for (Object o:tipreports
             ) {
            TipReport tip = (TipReport)o;
            String url = tip.getStr("IMGDIR");
            url = pathAnalyse(url);
            tip.set("IMGDIR",url);
        }
    }

    public static void buildPath(TipReport tip){
        String url = tip.getStr("IMGDIR");
        url = pathAnalyse(url);
        tip.set("IMGDIR",url);
    }

    private static String pathAnalyse(String url){
        //等于-1不包含  不等于-1 包含
        int index = url.indexOf("tipreportMore");
        String preUrl = "/evidence/";
        if(index !=-1){
            // 先拿出一张照片,因为数据库只存了路径
            String pictureName = getPictureName(url);
            url = preUrl +url.substring(index)+pictureName;
        }
        else{
            index = url.indexOf("tipreport");
            url = preUrl +url.substring(index);
        }
        return url;
    }

    private static String getPictureName(String path){

        if(null!=path){
            File file  = new File(path);
            if(file.exists()){
                File[] list = file.listFiles();
                for (File child:list) {
                    if(child.isFile()){
                        //是文件判断是否是图片
                        int index = child.getName().lastIndexOf('.');
                        String extension = child.getName().substring(index).toLowerCase();
                        if(".jpg".equals(extension) || ".png".equals(extension) || ".gif".equals(extension) || ".jpeg".equals(extension)){
                            //是图片
                            return child.getName();
                        }
                    }
                }
            }
        }

        return null;
    }

    public static void rebuiltTipMore( List tipMores ){
        for (Object o:tipMores) {
            TipReportMore tipMore = (TipReportMore)o;
            //重建语音路径
            String voicePath = tipMore.get("SoundDir");

            System.out.println(voicePath);

            voicePath = voicePath.replaceAll("\\\\","/");

            System.out.println(voicePath);

            voicePath = voicePath.substring( voicePath.indexOf("/evidence") );

            tipMore.set("SoundDir",voicePath);
            //重建图片路径，添加图片路径链表
            String picPath = tipMore.get("VideoDir");

            picPath = picPath.replaceAll("\\\\","/");

            String prePath = picPath.substring( picPath.indexOf("/evidence") );

            List<String> picPathList = new ArrayList<String>();

            File file = new File(picPath);

            if(file.exists()){
                File[] list = file.listFiles();
                for (File child:list
                     ) {
                    int index = child.getName().lastIndexOf('.');
                    String extension = child.getName().substring(index).toLowerCase();
                    if(".jpg".equals(extension) || ".png".equals(extension) || ".gif".equals(extension) || ".jpeg".equals(extension)){
                        //是图片
                        picPathList.add(prePath+child.getName());
                    }
                }
            }
            tipMore.put("plist",picPathList);
        }
    }
}
