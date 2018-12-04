package cn.edu.dlmu.wechat.bean;


import cn.edu.dlmu.wechat.service.IdentityCodeService;
import com.submail.lib.MESSAGEXsend;
import com.submail.utils.AppConfig;
import com.submail.utils.ConfigLoader;

import java.sql.Timestamp;
import java.util.Date;

public class MessageSend {

    public static boolean login(String phoneNumber){

        return false;
    }


    public static  boolean message(String phoneNumber){
        //发送验证码
        String code = MessageSend.sendMessage(phoneNumber);
        //获取当前时间
        Timestamp TimeBeign = new Timestamp(new Date().getTime());
        //设置过期时间
        Timestamp TimeEnd = new Timestamp(new Date().getTime()+600*1000);
        System.out.println(TimeBeign+"\n"+TimeEnd);
        //先查询是否有当前手机号的验证码
        if(IdentityCodeService.codeFindByPhoneNum(phoneNumber)){
            IdentityCodeService.codeUpdate(phoneNumber,code,TimeBeign,TimeEnd);
        }
        //将验证码写入表中
        else{
            IdentityCodeService.codeInsert(phoneNumber,code,TimeBeign,TimeEnd);
        }
        return true;
    }

    public static String sendMessage(String phoneNumber){
        String appid = "20352";
        String appkey = "47d97b5c9099ff4d7cbcfe4d0279ca5c";
        String signtype = "md5";
        AppConfig config = ConfigLoader.createConfig(appid, appkey, signtype);
        MESSAGEXsend submail = new MESSAGEXsend(config);
        submail.addTo(phoneNumber);
        submail.setProject("ki6Yd3");
        //获取随机验证码
        String code = getRandNUm();
        submail.addVar("code", code);
        submail.addVar("time", "10分钟");
        String response = submail.xsend();
        //判断验证码是否发送成功
        System.out.println("获取到的消息：" + response);
        return code;
    }

    //产生随机数
    private static String getRandNUm(){
        String s = "";
        while (s.length() < 6)
            s += (int) (Math.random() * 10);
        return s;
    }
}
