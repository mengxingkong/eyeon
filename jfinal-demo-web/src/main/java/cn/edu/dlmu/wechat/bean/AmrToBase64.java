package cn.edu.dlmu.wechat.bean;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;

public class AmrToBase64 {

    public String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        String base64str =  new BASE64Encoder().encode(buffer);
        //System.out.println(base64str);
        return base64str;
    }

}