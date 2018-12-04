package cn.edu.dlmu.wechat.controller;

import cn.edu.dlmu.wechat.model.Config;
import cn.edu.dlmu.wechat.model.WeChatUser;
import cn.edu.dlmu.wechat.service.ConfigService;
import cn.edu.dlmu.wechat.service.PageAuthorizeService;
import cn.edu.dlmu.wechat.service.WeChatUserService;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.SnsAccessToken;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import com.jfinal.weixin.sdk.api.SnsApi;

import java.util.HashMap;
import java.util.Map;

public class AuthorizeController extends Controller {

    /**
     * 访问形式  /eyeon/index?state=homePage
     */
    public void index(){

        String OpenID = getSessionAttr("OpenID");
        String state = getPara("state");
        if(state == null){
            state = "homePage";
        }
        if(null == OpenID){
            String authorizeURL = build_url(state,true,"authorize_slient");
            redirect(authorizeURL);
        }
        else{

            WeChatUser weChatUser = WeChatUserService.findByOpenID(OpenID);

            if(null == weChatUser){
                String authorizeURL = build_url(state, false, "authorize_user");
                redirect(authorizeURL);
                return;
            }

            boolean isValid = judge(OpenID);
            if(!isValid){
                String authorizeURL = build_url(state,false,"authorize_user");
                redirect(authorizeURL);
            }
            //access_token 有效
            //执行后续操作
            //重定向
            if("tipReport".equals(state)){
                redirect("/jsPage/tipReportPage");
            }
            else{
                redirect("/wPage/"+state);
            }
        }
    }

    public void authorize_slient(){

        String state = getPara("state");
        //看看这里获取到的不是 OPENID
        String code = getPara("code");

        SnsAccessToken snsAccessToken = getAccessTokenByCode(code);

        //System.out.println(snsAccessToken.getOpenid());

        String OpenID = snsAccessToken.getOpenid();

        if(null == OpenID){
            renderText("获取OpenID失败，请确认是否从微信登录");
        }
        else {

            setSessionAttr("OpenID", OpenID);
            //判断 用户信息是否存在  不存在说明 用户首次进入 eyeon 需要进行手动授权
            WeChatUser weChatUser = WeChatUserService.findByOpenID(OpenID);

            if(null == weChatUser){
                String authorizeURL = build_url(state, false, "authorize_user");
                redirect(authorizeURL);
                return;
            }

            //存在的话 判断用户的 access_token 在pageauthorize中是否存在 存在的话 判断 是否有效
            //判断用户 access_token是否存在
            //存在则更新
            //这里的 snsBase

            PageAuthorizeService.save(snsAccessToken);

            boolean isValid = judge(OpenID);

            if (!isValid) {
                String authorizeURL = build_url(state, false, "authorize_user");
                redirect(authorizeURL);
                return;
            }

            //更新用户地理位置


            //ApiResult apiResult = getUserInfo(snsAccessToken.getAccessToken(),OpenID);

            //access_token 有效
            //后续操作
            //重定向
            if ("tipReport".equals(state)) {
                redirect("/jsPage/tipReportPage");
            } else {
                redirect("/wPage/" + state);
            }
        }

    }
    public void authorize_user(){

        String code = this.getPara("code");
        String state = this.getPara("state");

        SnsAccessToken snsAccessToken = this.getAccessTokenByCode(code);

        PageAuthorizeService.save(snsAccessToken);

        String OpenID = snsAccessToken.getOpenid();
        setSessionAttr("OpenID",snsAccessToken.getOpenid());

        ApiResult apiResult = getUserInfo(snsAccessToken.getAccessToken(),OpenID);

        WeChatUser weChatUser = WeChatUserService.findByOpenID(OpenID);

        if(null == weChatUser){
            //插入
            WeChatUserService.save(apiResult);
        }
        else{
            //更新
            WeChatUserService.update(apiResult);
        }


        //accessToken 有效 后续操作
        //重定向
        if("tipReport".equals(state)){
            redirect("/jsPage/tipReportPage");
        }
        else{
            redirect("/wPage/"+state);
        }
    }

    /**
     * 判断用户授权是否过期
     * @param OpenID
     * @return ture 有效  false 无效
     */
    private boolean judge(String OpenID){

        //access_token false 过期  true 有效
        boolean isValid = PageAuthorizeService.judge_accessToken(OpenID);
        //使用refresh_token更新
        if(!isValid){
            return PageAuthorizeService.refresh_accessToken(OpenID);
        }
        return true;

    }

    /**
     * snsbase 为 true 则是静默调用
     * 否则为授权页面调用
     */
    public String build_url(String state,boolean snsBase,String method){
        Config config = ConfigService.getConfig();
        String appId = config.getStr("AppID");
        String redirect_url = config.getStr("Domain")+"/eyeon/"+method;
        String authorizeURL = SnsAccessTokenApi.getAuthorizeURL(appId,redirect_url,state,snsBase);
        return authorizeURL;
    }

    private SnsAccessToken getAccessTokenByCode(String code) {
        Config config = ConfigService.getConfig();
        String token = config.getStr("Token");
        String appId = config.getStr("AppID");
        String appSecret = config.getStr("Appsecret");
        SnsAccessToken snsAccessToken = SnsAccessTokenApi.getSnsAccessToken(appId, appSecret, code);
        return snsAccessToken;
    }

    private ApiResult getUserInfo(String AccessToken, String OpenID) {
        return SnsApi.getUserInfo(AccessToken, OpenID);
    }

    /***
     * 访问 主页 简单举报 等页面时  先到 拦截器（或控制器）
     *
     * 判断 session 中openID 是否存在
     *
     * 不存在
     *      采用静默授权获取用户的 OpenID 存入 session
     *
     *     判断用户ACCESS_TOKEN 是否存在
     *     不存在
     *         调用手动授权 获得ACCESS_TOKEN，REFRESH_TOKEN
     *     存在
     *         判断是否过期
     *         过期
     *             使用refresh_TOKEN 刷新  若 refresh_token也过期 则 调用用户手动授权
     * 拉取用户信息
     */
}
