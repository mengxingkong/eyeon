package cn.edu.dlmu.wechat.bean;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ChangeAudioFormat {

    public static void changeToMp3(String sourcePath, String targetPath) {

        String osInfo = System.getProperty("os.name").toLowerCase();

        if(osInfo.contains("linux")){
            linux(sourcePath,targetPath);
        }
        else if(osInfo.contains("windows")){
            windows(sourcePath,targetPath);
        }
        else{
            System.out.println("音频转码错误");
        }

    }

    public static void windows(String sourcePath,String targetPath){
        File source = new File(sourcePath);
        File target = new File(targetPath);
        AudioAttributes audio = new AudioAttributes();
        Encoder encoder = new Encoder();

        audio.setCodec("libmp3lame");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);

        try {
            encoder.encode(source, target, attrs);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InputFormatException e) {
            e.printStackTrace();
        } catch (EncoderException e) {
            System.out.println("有错但是成功转换了");
        }
    }
    public static void linux(String sourcePath,String targetPath){
        String webroot = "";
        Runtime run = null;
        try {
            run = Runtime.getRuntime();
            long start=System.currentTimeMillis();
            System.out.println(new File(webroot).getAbsolutePath());
            //执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
            Process p=run.exec("ffmpeg -i "+sourcePath+" -acodec libmp3lame "+targetPath);
            //释放进程

            //BufferedReader br1 = new BufferedReader(new OutputStreamWriter(p.getOutputStream()));

            p.getOutputStream().close();

            p.getInputStream().close();

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line = null;
            //逐行读取输出到控制台
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            p.getErrorStream().close();
            p.waitFor();
            long end=System.currentTimeMillis();
            System.out.println(sourcePath+" convert success, costs:"+(end-start)+"ms");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //run调用lame解码器最后释放内存
            run.freeMemory();
        }
    }
}