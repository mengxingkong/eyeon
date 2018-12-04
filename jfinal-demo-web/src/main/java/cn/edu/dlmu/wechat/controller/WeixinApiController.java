package cn.edu.dlmu.wechat.controller;


import cn.edu.dlmu.wechat.model.Config;
import cn.edu.dlmu.wechat.service.ConfigService;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.*;
import com.jfinal.weixin.sdk.jfinal.ApiController;

public class WeixinApiController extends ApiController {

    //测试可用
    private final String wtest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx19e37fd297b61efd&redirect_uri=http://mstark.free.ngrok.cc/wPage/index&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect ";
                                  //https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx19e37fd297b61efd&redirect_uri=http://mstark.free.ngrok.cc/wPage/index&response_type=code&scope=snsapi_userinfo&state=wx#wechat_redirect
    private String authorizeURL = null;

    private String authorizeURL_tip = null;
    private String authorizeURL_task = null;
    private String authorizeURL_assign = null;

    public WeixinApiController(){

        /*更新*/
        Config config = ConfigService.getConfig();
        String appId = config.getStr("AppID");
        String redirect_url = config.getStr("Redirect");

        authorizeURL="http://eyeon.natapp1.cc/eyeon/index?state=homePage";
        authorizeURL_tip="http://eyeon.natapp1.cc/eyeon/index?state=tipReport";
        authorizeURL_task="http://eyeon.natapp1.cc/eyeon/index?state=userTasks";
        authorizeURL_assign ="http://eyeon.natapp1.cc/eyeon/index?state=userAssignment";

//        authorizeURL = SnsAccessTokenApi.getAuthorizeURL(appId,redirect_url,"homePage",false);
//        authorizeURL_tip = SnsAccessTokenApi.getAuthorizeURL(appId,redirect_url,"tipReport",false);
//        authorizeURL_task = SnsAccessTokenApi.getAuthorizeURL(appId,redirect_url,"userTasks",false);
//        authorizeURL_assign = SnsAccessTokenApi.getAuthorizeURL(appId,redirect_url,"userAssignment",false);

        System.out.println("test***************test");
        System.out.println(authorizeURL);
        System.out.println(authorizeURL_tip);
        System.out.println(authorizeURL_task);
        System.out.println(authorizeURL_assign);

        //获取认证所需的连接
        //authorizeURL = SnsAccessTokenApi.getAuthorizeURL(PropKit.get("appId"),PropKit.get("redirect_uri"),false);

    }

    public ApiConfig getApiConfig() {
        ApiConfig ac = new ApiConfig();

        /*更新*/
        Config config = ConfigService.getConfig();
        String token = config.getStr("Token");
        String appId = config.getStr("AppID");
        String appSecret = config.getStr("Appsecret");

        System.out.println(appId);

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

    public void index(){
        renderText("api");
    }

    /**
     * 获取公众号菜单
     */
    public void getMenu() {
        ApiResult apiResult = MenuApi.getMenu();
        if (apiResult.isSucceed())
            renderText(apiResult.getJson());
        else
            renderText(apiResult.getErrorMsg());
    }

    /**
     * 创建菜单
     */
    public void createMenu()
    {
        //地址需要以http://开头
        String str = "{\n" +
                "    \"button\": [\n" +
                "        {\n" +
                "            \"name\": \"Eye'on\",\n" +
                "            \"url\": \"http://www.google.com\",\n" +
                "            \"type\": \"view\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"历史消息\",\n" +
                "            \"sub_button\": [\n" +
                "                 {\n" +
                "                   \"type\": \"view\",\n" +
                "                   \"name\": \"简单举报\", \n" +
                "                   \"url\":\""+this.authorizeURL_tip+"\"\n" +
                "                 },\n" +
                "                 {\n" +
                "                   \"type\": \"view\",\n" +
                "                   \"name\": \"代接任务\", \n" +
                "                   \"url\":\""+this.authorizeURL_assign+"\"\n" +
                "                 },\n" +
                "                 {\n" +
                "                   \"type\": \"view\",\n" +
                "                   \"name\": \"已接任务\", \n" +
                "                   \"url\":\""+this.authorizeURL_task+"\"\n" +
                "                 }\n" +
                "              ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Eye'on网页\",\n" +
                "          \"url\":\""+this.authorizeURL+"\",\n"+
                "          \"type\":\"view\"\n"+
                "        }\n" +
                "    ]\n" +
                "}";
        ApiResult apiResult = MenuApi.createMenu(str);
        if (apiResult.isSucceed())
            renderText(apiResult.getJson());
        else
            renderText(apiResult.getErrorMsg());
    }

    /**
     * 获取公众号关注用户
     */
    public void getFollowers()
    {
        ApiResult apiResult = UserApi.getFollows();
        renderText(apiResult.getJson());
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo()
    {
        ApiResult apiResult = UserApi.getUserInfo("ohbweuNYB_heu_buiBWZtwgi4xzU");
        renderText(apiResult.getJson());
    }

    /**
     * 发送模板消息
     */
    public void sendMsg()
    {
        String str = " {\n" +
                "           \"touser\":\"ohbweuNYB_heu_buiBWZtwgi4xzU\",\n" +
                "           \"template_id\":\"9SIa8ph1403NEM3qk3z9-go-p4kBMeh-HGepQZVdA7w\",\n" +
                "           \"url\":\"http://www.sina.com\",\n" +
                "           \"topcolor\":\"#FF0000\",\n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"恭喜你购买成功！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\"去哪儿网发的酒店红包（1个）\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\":{\n" +
                "                       \"value\":\"1元\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        ApiResult apiResult = TemplateMsgApi.send(str);
        renderText(apiResult.getJson());
    }

    /**
     * 获取参数二维码
     */
    public void getQrcode()
    {
        String str = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
        ApiResult apiResult = QrcodeApi.create(str);
        renderText(apiResult.getJson());

//        String str = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"123\"}}}";
//        ApiResult apiResult = QrcodeApi.create(str);
//        renderText(apiResult.getJson());
    }

    /**
     * 长链接转成短链接
     */
    public void getShorturl()
    {
        String str = "{\"action\":\"long2short\"," +
                "\"long_url\":\"http://wap.koudaitong.com/v2/showcase/goods?alias=128wi9shh&spm=h56083&redirect_count=1\"}";
        ApiResult apiResult = ShorturlApi.getShorturl(str);
        renderText(apiResult.getJson());
    }

    /**
     * 获取客服聊天记录
     */
    public void getRecord()
    {
        String str = "{\n" +
                "    \"endtime\" : 987654321,\n" +
                "    \"pageindex\" : 1,\n" +
                "    \"pagesize\" : 10,\n" +
                "    \"starttime\" : 123456789\n" +
                " }";
        ApiResult apiResult = CustomServiceApi.getRecord(str);
        renderText(apiResult.getJson());
    }

    /**
     * 获取微信服务器IP地址
     */
    public void getCallbackIp()
    {
        ApiResult apiResult = CallbackIpApi.getCallbackIp();
        renderText(apiResult.getJson());
    }

}
