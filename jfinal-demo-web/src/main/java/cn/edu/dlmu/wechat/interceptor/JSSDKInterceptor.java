package cn.edu.dlmu.wechat.interceptor;

import cn.edu.dlmu.wechat.model.Config;
import cn.edu.dlmu.wechat.service.ConfigService;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.api.JsTicket;
import com.jfinal.weixin.sdk.api.JsTicketApi;

import java.util.UUID;

public class JSSDKInterceptor implements Interceptor {


    /**
     * 在JsTicketApi中使用getJsTicket方法需要使用到ApiConfig对象
     * @return
     */
    public ApiConfig getApiConfig() {
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


        /**
         * 是否对消息进行加密，对应于微信平台的消息加解密方式： 1：true进行加密且必须配置 encodingAesKey
         * 2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
        ac.setEncodingAesKey(PropKit.get("encodingAesKey",
                "setting it in config file"));
        return ac;
    }

    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        ApiConfigKit.setThreadLocalApiConfig(getApiConfig());
        JsTicket jsApiTicket = JsTicketApi.getTicket(JsTicketApi.JsApiType.jsapi);
        String jsapi_ticket = jsApiTicket.getTicket();
        String url = "http://mstark.free.ngrok.cc/jsPage/"+inv.getMethodName();
        System.out.println(url);
        String timestamp = create_timestamp();
        String nonce_str = create_nonce_str();
        String  str = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        String signature = HashKit.sha1(str);
        controller.setAttr("appId", ApiConfigKit.getApiConfig().getAppId());
        controller.setAttr("nonceStr", nonce_str);
        controller.setAttr("timestamp", timestamp);
        controller.setAttr("url", url);
        controller.setAttr("signature", signature);
        controller.setAttr("jsapi_ticket", jsapi_ticket);
        System.out.println(controller.getAttr("appid"));
        inv.invoke();
    }


    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }


}
