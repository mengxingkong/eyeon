package cn.edu.dlmu.wechat.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class TxtWrite {

    public static void write(String content){

        String current_dir = System.getProperty("user.dir");
        System.out.println(current_dir);
        String path2 = current_dir + "/evidence/1.txt";
        contentToTxt(path2,content);
    }

    public static void contentToTxt(String filePath, String content) {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath),true));
            writer.write("\n"+content);
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
