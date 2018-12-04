package cn.edu.dlmu.wechat.controller;


import cn.edu.dlmu.wechat.bean.GetImage;
import cn.edu.dlmu.wechat.model.Config;
import cn.edu.dlmu.wechat.service.ConfigService;
import cn.edu.dlmu.wechat.service.TipReportService;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.jfinal.MsgController;
import com.jfinal.weixin.sdk.msg.in.*;
import com.jfinal.weixin.sdk.msg.in.event.*;
import com.jfinal.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.jfinal.weixin.sdk.msg.out.*;


public class WeixinMsgController extends MsgController {
    private static final String helpStr = " the first test";
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

    /**
     * 处理微信文本消息
     * @param inTextMsg
     */
    protected void processInTextMsg(InTextMsg inTextMsg) {
        String msgContent = inTextMsg.getContent().trim();
        // 帮助提示
        if ("help".equalsIgnoreCase(msgContent) || "帮助".equals(msgContent)) {
            OutTextMsg outMsg = new OutTextMsg(inTextMsg);
            outMsg.setContent(helpStr);
            render(outMsg);
        }
        // 其它文本消息直接返回原值 + 帮助提示
        else {
            renderOutTextMsg("\t文本消息已成功接收，内容为： " + inTextMsg.getContent() + "\n\n" + helpStr);
        }
    }

    /**
     * 实现父类抽方法，处理图片消息
     * 发送过来的图片消息会带有图片的保存地址，获取图片并保存到本地
     */
    protected void processInImageMsg(InImageMsg inImageMsg) {
        String current_dir = System.getProperty("user.dir");
        System.out.println(current_dir);

        String filePath = getRequest().getSession().getServletContext().getRealPath("/evidence/tipreport/");
//        String filePath = current_dir + "/evidence/tipreport/";
        GetImage.creatDir(filePath);
        //String filePath = "D:/Jfinal-demo/jfinal-demo-web/src/main/webapp/evidence/tipreport/";
        //编辑回传信息
        OutTextMsg outMsg = new OutTextMsg(inImageMsg);
        //获取用户信息
        String OpenID = inImageMsg.getFromUserName();
        //int status = (Integer) WeChatUserService.findProperty(OpenID,"IsSubscribe");
        //查询数据库 看用户是否处于举报状态
        //if(2 == status){
        //保存图片信息到本地,并将路径信息写入数据库
        String urlImage = inImageMsg.getPicUrl();
        String fileName = inImageMsg.getFromUserName()+inImageMsg.getCreateTime();
        fileName = String.valueOf(fileName.hashCode());
        String path =  GetImage.downLoadImage(urlImage,filePath+"/"+fileName);

//        String ppath = getRequest().getRealPath("/");
//        System.out.println(ppath);

        if(null == path){
            System.out.println("下载失败");
            outMsg.setContent("举报信息上传错误，请重新上传");
        }
        else {
            //将简单举报的信息写入数据库
            TipReportService.save(inImageMsg,path);
            outMsg.setContent("举报成功");
        }
        //}
        //else{
            //提醒用户点击举报按钮开始举报
            //outMsg.setContent("请先点击举报按钮再开始举报");
        //}
        //返回举报结果
        render(outMsg);
    }

    /**
     * 处理语音消息
     * @param inVoiceMsg
     */
    @Override
    protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
        OutVoiceMsg outMsg = new OutVoiceMsg(inVoiceMsg);
        // 将刚发过来的语音再发回去
        outMsg.setMediaId(inVoiceMsg.getMediaId());
        render(outMsg);
    }

    @Override
    protected void processInVideoMsg(InVideoMsg inVideoMsg) {

    }

    @Override
    protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {

    }

    @Override
    protected void processInLocationMsg(InLocationMsg inLocationMsg) {

    }

    @Override
    protected void processInLinkMsg(InLinkMsg inLinkMsg) {

    }
    @Override
    protected void processInCustomEvent(InCustomEvent inCustomEvent) {

    }
    /**
     * 处理取消和关注事件 subscribe or unsubscribe
     * 将关注用户的基本信息写入数据库
     * @param inFollowEvent
     */
    @Override
    protected void processInFollowEvent(InFollowEvent inFollowEvent) {
        renderText("");
    }
    /**
    protected void processInFollowEvent(InFollowEvent inFollowEvent) {
        String openId  = inFollowEvent.getFromUserName();
        System.out.println(openId);
        //UserApi.getUserInfo(openid).getJson() 返回用户基本信息的URL格式
        //ApiResult 类中有map数据结构 可以直接通过json的key值进行访问获取
        System.out.println(inFollowEvent.getEvent());
        if(WechatEvent.EVENT_SUBSCRIBE.equals(inFollowEvent.getEvent())) {
            //先判断用户信息是否存在于wechatuser表中
            if( null == WeChatUserService.findByOpenID(openId)){
                ApiResult userInfo = UserApi.getUserInfo(openId);
                WeChatUserService.save(userInfo);
            }
            else{
                WeChatUserService.updateIsSubscribe(openId,inFollowEvent.getEvent());
            }
        }
        else{
            //取消关注的操作，将IsSubscribe字段更新为0
            WeChatUserService.updateIsSubscribe(openId,inFollowEvent.getEvent());
        }
        //返回eye'on 的简介，目前先返回文本消息
        OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
        outMsg.setContent("欢迎关注：eye'on.公众号简介稍后补充！！！");
        render(outMsg);
    }
    */
    @Override
    protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {

    }

    /**
     * 用户没进入一次公众号就可以获取一次其地理位置信息，消息有微信服务器推送
     * @param inLocationEvent
     */
    @Override
    protected void processInLocationEvent(InLocationEvent inLocationEvent) {
        renderText("success");
    }
    /**
    protected void processInLocationEvent(InLocationEvent inLocationEvent) {
        String openid = inLocationEvent.getFromUserName();
        String latitude = inLocationEvent.getLatitude();
        String longitude = inLocationEvent.getLongitude();
        //每次用户进入微信公众号都更新其Gps信息
        WeChatUserService.update(openid,latitude,longitude);
        renderText("success");
    }
    */
    @Override
    protected void processInMassEvent(InMassEvent inMassEvent) {

    }

    /**
     * 处理接收到的自定义菜单事件
     * @param inMenuEvent
     */
    @Override
    protected void processInMenuEvent(InMenuEvent inMenuEvent) {
        if(inMenuEvent.getEvent().equals(InMenuEvent.EVENT_INMENU_VIEW)){
            renderText("");
        }
    }

    @Override
    protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {

    }

    @Override
    protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {

    }

    @Override
    protected void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent) {

    }

    @Override
    protected void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent) {

    }

    @Override
    protected void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent) {

    }

    @Override
    protected void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent) {

    }

    @Override
    protected void processInWifiEvent(InWifiEvent inWifiEvent) {

    }

    @Override
    protected void processInUserViewCardEvent(InUserViewCardEvent inUserViewCardEvent) {

    }

    @Override
    protected void processInSubmitMemberCardEvent(InSubmitMemberCardEvent inSubmitMemberCardEvent) {

    }

    @Override
    protected void processInUpdateMemberCardEvent(InUpdateMemberCardEvent inUpdateMemberCardEvent) {

    }

    @Override
    protected void processInUserPayFromCardEvent(InUserPayFromCardEvent inUserPayFromCardEvent) {

    }

    @Override
    protected void processInMerChantOrderEvent(InMerChantOrderEvent inMerChantOrderEvent) {

    }

    @Override
    protected void processIsNotDefinedEvent(InNotDefinedEvent inNotDefinedEvent) {

    }

    @Override
    protected void processIsNotDefinedMsg(InNotDefinedMsg inNotDefinedMsg) {

    }
}
