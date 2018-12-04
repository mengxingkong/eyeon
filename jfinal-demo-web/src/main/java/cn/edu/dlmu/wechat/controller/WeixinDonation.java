package cn.edu.dlmu.wechat.controller;

import cn.edu.dlmu.wechat.bean.MessageSend;
import cn.edu.dlmu.wechat.model.Config;
import cn.edu.dlmu.wechat.model.DonationUserInfo;
import cn.edu.dlmu.wechat.service.ConfigService;
import cn.edu.dlmu.wechat.service.DonationUserInfoService;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;

public class WeixinDonation extends Controller{

    public WeixinDonation(){}

    public ApiConfig getApiConfig() {
        //PropKit.use("config.properties");
        ApiConfig ac = new ApiConfig();
//        // 配置微信 API 相关常量
//        ac.setToken(PropKit.get("token"));
//        ac.setAppId(PropKit.get("appId"));
//        ac.setAppSecret(PropKit.get("appSecret"));
        /*更新*/
        Config config = ConfigService.getConfig();
        String token = config.getStr("Token");
        String appId = config.getStr("AppID");
        String appSecret = config.getStr("Appsecret");

        ac.setToken(token);
        ac.setAppId(appId);
        ac.setAppSecret(appSecret);

        /**
         *  是否对消息进行加密，对应于微信平台的消息加解密方式：
         *  1：true进行加密且必须配置 encodingAesKey
         *  2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
        //ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
        return ac;
    }

    public void loginIdentity(){
//        if(getSessionAttr("phoneNumber") == null){
//            render("login.html");
//        }
        render("login.html");
    }

    public void getIdentityCode(){
        String phoneNum = getPara("phoneNum");
        //需要先检测一下OpenID是否存在 不存在则需要微信登录
        String openID = getSessionAttr("OpenID");
        if(!MessageSend.message(phoneNum)){
            //发送成功
            setAttr("result","err");
        }
        setAttr("result","ok");
        renderJson();
    }

    public void register(){
        String phoneNum = getPara("phoneNum");
        String OpenID = getSessionAttr("OpenID");
        String code = getPara("code");
        int state = DonationUserInfoService.register(phoneNum,code,OpenID);
        String result = "";
        if(-2 == state){
            result = "overtime";
        }
        else if(-1 == state){
            result = "errorcode";
        }
        else if(0 == state){
            result = "exited";
        }
        else if(1 == state){
            result = "ok";
        }
        setAttr("result",result);
        renderJson();
    }

    public void login(){
        String phoneNum = getPara("phoneNum");
        String OpenID = getSessionAttr("OpenID");
        String code = getPara("code");
        int state = DonationUserInfoService.login(phoneNum,code,OpenID);
        String result = "";
        if(-2 == state){
            result = "overtime";
        }
        else if(-1 == state){
            result = "errorcode";
        }
        else if(0 == state){
            result = "register";
        }
        else if(1 == state){
            result = "ok";
        }
        setAttr("result",result);
        renderJson();
    }

}
