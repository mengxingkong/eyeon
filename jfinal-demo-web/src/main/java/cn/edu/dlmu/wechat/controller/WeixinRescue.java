package cn.edu.dlmu.wechat.controller;

import cn.edu.dlmu.wechat.bean.MessageSend;
import cn.edu.dlmu.wechat.model.*;
import cn.edu.dlmu.wechat.service.*;
import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.JsTicket;
import com.jfinal.weixin.sdk.api.JsTicketApi;

import java.util.List;
import java.util.UUID;

public class WeixinRescue extends Controller {
    public WeixinRescue(){}
    public ApiConfig getApiConfig() {
        //PropKit.use("config.properties");
        ApiConfig ac = new ApiConfig();
        // 配置微信 API 相关常量

        /*更新*/
        Config config = ConfigService.getConfig();
        String token = config.getStr("Token");
        String appId = config.getStr("AppID");
        String appSecret = config.getStr("Appsecret");

        ac.setToken(token);
        ac.setAppId(appId);
        ac.setAppSecret(appSecret);

//        ac.setToken(PropKit.get("token"));
//        ac.setAppId(PropKit.get("appId"));
//        ac.setAppSecret(PropKit.get("appSecret"));

        /**
         *  是否对消息进行加密，对应于微信平台的消息加解密方式：
         *  1：true进行加密且必须配置 encodingAesKey
         *  2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
        //ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
        return ac;
    }


    public void index(){
        if(getSessionAttr("RescuePhone")==null){
            render("login.html");
        }
        else{
            render("index.html");
        }
    }

    public void rescueRequest(){
        jsSdk();
        render("rescueDemand.html");
    }

    public void rescueSubmit(){
        String name = getPara("name");
        String phone = getPara("phone");
        String location = getPara("location");
        String describe = getPara("describe");
        String lng = getPara("lat");
        String lat = getPara("lng");
        String OpenID = getSessionAttr("OpenID");
        RescueService.insertRescue(name,phone,location,describe,lng,lat,OpenID);
        setAttr("url","/rescue/");
        renderJson();
    }

    public void rescueStatus(){

        String phone = getSessionAttr("RescuePhone");

        Rescue rescue = RescueService.findStatus(phone);
        //请求用户的信息
        if(null != rescue){
            System.out.print(rescue);
            setAttr("rescue",rescue);
            render("status.html");
        }
        else {
            render("index.html");
        }
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
        int state = RescueUserInfoService.register(phoneNum,code,OpenID);
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
        setSessionAttr("RescuePhone",phoneNum);
        setAttr("result",result);
        renderJson();
    }

    public void login(){
        String phoneNum = getPara("phoneNum");
        String OpenID = getSessionAttr("OpenID");
        String code = getPara("code");
        int state = RescueUserInfoService.login(phoneNum,code,OpenID);
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
        setSessionAttr("RescuePhone",phoneNum);
        setAttr("result",result);
        renderJson();
    }

    public void showResources(){
        String lng = getPara("lng");
        String lat = getPara("lat");
        List<Resources> resources = ResourcesService.showResources(lng,lat);
        System.out.println(resources);
        setAttr("res",resources);
        renderJson();
    }

    public void resRequest(){
        int resID = getParaToInt("resID");
        int status = ResourcesService.resRequest(resID);
        String result = "";
        if(0 == status){
            result = "none";
        }
        else{
            result = "ok";
        }
        setAttr("res",result);
        renderJson();
    }
    public void showRescuer(){
        String phone = getSessionAttr("RescuePhone");
        RescueSituation resc = RescueSituationService.getInfo(phone);
        System.out.println(resc);
        String result = "";
        if(resc == null){
            result = "none";
        }
        else{
            String gps = resc.getStr("GPS");
            String[] loc = gps.split(" ");
            System.out.println(loc);
            setAttr("lat",loc[0]);
            setAttr("lng",loc[1]);
            setAttr("resc",resc);
            result = "ok";
        }
        setAttr("result",result);
        renderJson();
    }

    public void showNearby(){
        String phone = getSessionAttr("RescuePhone");
        RescueUserInfo user = RescueUserInfoService.getUserInfo(phone);
        String[] loc = user.getStr("GPS").split(" ");
        setAttr("lng",loc[1]);
        setAttr("lat",loc[0]);
        setAttr("user",user);
        render("nearby.html");
    }

    public void showNearbyInfo(){
        String lng = getPara("lng");
        String lat = getPara("lat");
        List<Rescue> rescues = RescueService.getNearby(lng,lat);
        setAttr("rescues",rescues);
        System.out.println(rescues);
        renderJson();
    }

    public void exeRescue(){
        int resId = getParaToInt("resId");
        String phone = getSessionAttr("RescuePhone");

        int status = RescueSituationService.exeRescue(phone,resId);

        String result="";
        if(-1 == status){
            result="over";
        }
        else if(1 == status){
            result="ok";
        }
        else if(0 == status){
            result="exited";
        }
        setAttr("res",result);
        renderJson();
    }

    public void completeExe(){
        int resId = getParaToInt("resId");
        String phone = getSessionAttr("RescuePhone");
        int status = RescueSituationService.completeExe(phone,resId);
        String result ="ok";
        setAttr("res",result);
        renderJson();
    }

    public void showDistribute(){
        String phone = getSessionAttr("RescuePhone");
        List<RescueSituation> rs = RescueSituationService.getDistuibute(phone);
        setAttr("rs",rs);
        System.out.println(rs);
        render("distribute.html");
    }

    public void lookDistribute(){
        int resId = getParaToInt("resId");
        String phone = getSessionAttr("RescuePhone");
        Rescue r = RescueService.findById(resId);
        RescueUserInfo ri = RescueUserInfoService.findLocation(phone);
        System.out.println(r);
        System.out.println(ri);
        setAttr("r",r);
        setAttr("ri",ri);
        renderJson();
    }

    public void exeDistribute(){
        int resId = getParaToInt("resId");
        String phone = getSessionAttr("RescuePhone");

        int status = RescueSituationService.exeDistribute(phone,resId);

        String result="";
        if(3 == status){
            result="success";
        }
        else if(1 == status){
            result="ok";
        }
        else if(4 == status){
            result="fail";
        }
        setAttr("res",result);
        renderJson();
    }

    public void completeDis(){
        int resId = getParaToInt("resId");
        String phone = getSessionAttr("RescuePhone");
        int status = RescueSituationService.completeDistri(phone,resId);
        String result ="ok";
        setAttr("res",result);
        renderJson();
    }








    private void jsSdk(){
        ApiConfigKit.setThreadLocalApiConfig(getApiConfig());
        JsTicket jsApiTicket = JsTicketApi.getTicket(JsTicketApi.JsApiType.jsapi);
        String jsapi_ticket = jsApiTicket.getTicket();
        String url = getRequest().getRequestURL().toString();
        String qurryString = getRequest().getQueryString();
        if(null!=qurryString){
            url = url+"?"+qurryString;
        }
        String timestamp = create_timestamp();
        String nonce_str = create_nonce_str();
        String  str = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        String signature = HashKit.sha1(str);
        setAttr("appId", ApiConfigKit.getApiConfig().getAppId());
        setAttr("nonceStr", nonce_str);
        setAttr("timestamp", timestamp);
        setAttr("url", url);
        setAttr("signature", signature);
        setAttr("jsapi_ticket", jsapi_ticket);
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

}
