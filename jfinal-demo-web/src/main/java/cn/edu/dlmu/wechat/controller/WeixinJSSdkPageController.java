package cn.edu.dlmu.wechat.controller;
import cn.edu.dlmu.wechat.bean.Base64AndImg;
import cn.edu.dlmu.wechat.bean.GetImage;
import cn.edu.dlmu.wechat.bean.GetVoice;
import cn.edu.dlmu.wechat.bean.TxtWrite;
import cn.edu.dlmu.wechat.model.Config;
import cn.edu.dlmu.wechat.service.*;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.*;

import java.util.UUID;


public class WeixinJSSdkPageController extends Controller{

    public void index(){

    }

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

    public void tipReportMorePage(){
        String TaskID = getPara("TaskID");
        String AssignID = getPara("AssignID");
        setSessionAttr("TaskID",TaskID);
        setSessionAttr("AssignID",AssignID);
        jsSdk();
        render("TipReportMore.html");
    }

    public void tipReportPage(){
        jsSdk();
        render("tipReport.html");
    }


    public void tipReportSubmit(){
        String OpenID = getSessionAttr("OpenID");

        String title = getPara("title");

        String address = getPara("address");

        String serverId = getPara("serverId");

        String GPS = getPara("location");

        System.out.println(GPS);

        WeChatUserService.updateGPS(GPS,OpenID);

        ApiConfigKit.setThreadLocalApiConfig(getApiConfig());
        String AccessToken = AccessTokenApi.getAccessTokenStr();

        String path = getRequest().getSession().getServletContext().getRealPath("/evidence/tipreport/");

        String[]imagePath = GetImage.getImageFromWeb(path,serverId,OpenID,AccessToken);

        TipReportService.saveFromWeb(OpenID,address,imagePath);

        renderJson("{\"result\":\"ok\"}");

    }


    public void tipReportMoreSubmit(){
        String OpenID = getSessionAttr("OpenID");
        int TaskID = Integer.parseInt( getSessionAttr("TaskID").toString() );
        int AssignID = Integer.parseInt(getSessionAttr("AssignID").toString());

        String title = getPara("title");
        String discribe = getPara("discribe");
        String serverId = getPara("serverId");
        String address = getPara("address");
        String Depict = title+":\n"+discribe;


        String path = getRequest().getSession().getServletContext().getRealPath("/evidence/tipreportMore");


        TxtWrite.write(path);
        System.out.println("path*********:"+path);


        //地理位置信息 获取到的是json字符串，需要在后续解析
        String JsonLocation = getPara("location");


        //获取用户的语音信息serverId
        String voiceServerId = getPara("voiceId");


        //获取验证口令
        ApiConfigKit.setThreadLocalApiConfig(getApiConfig());
        String AccessToken = AccessTokenApi.getAccessTokenStr();

        //获取事件CaseID
        int CaseID = TaskService.findByID( TaskID ).getInt("Question");

        //图片信息成功保存，接下来将举报数据存入数据库
        String loadPath = GetImage.downloadImage(path,serverId,OpenID,TaskID,CaseID,AccessToken);

        //将语音数据保存到数据库
        String voicePath = GetVoice.saveVoiceToDisk(path,AccessToken,voiceServerId,OpenID,CaseID,TaskID);

        if(loadPath!=null&&voicePath!=null) {
            System.out.println("测试"+loadPath + "-->" + voicePath);
        }

        //先创建复杂举报对应的简单举报，填入必要的信息 Location lat lon Tipster CaseID,图片的路径
        int ReportID = TipReportService.save(JsonLocation,OpenID,CaseID,loadPath);

        //将信息写入复杂举报
        TipReportMoreService.save(Depict,ReportID,AssignID,voicePath,loadPath);


        //更新AssignTask表的任务状态
        //3代表已完成
        TaskAssignmentService.statusUpdate(AssignID,3);

        //字符串解析成功,w为什么会发送两次呢？？？？ --》 调试工具的问题


        // 将请求图片保存获取图片路径

        //System.out.println(openId+title+discribe+serverIdArray.getString(0));


//        String serverPath = "d:/fileUpload/";
//        String folderName = serverPath+UUID.randomUUID().toString();
//        UploadFile files = getFile(getPara("file"), folderName);


        renderJson("{\"result\":\"ok\"}");
    }


    public void imgTest(){

        String images = getPara("images");
        Base64AndImg convert = new Base64AndImg();
        convert.FileRederPrase(images);
        renderText("nihao");

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
