package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.model.Config;
import cn.edu.dlmu.wechat.model.PageAuthorize;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.weixin.sdk.api.SnsAccessToken;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;

public class PageAuthorizeService {
    public static PageAuthorize findByOpenID( String OpenID ){
        return PageAuthorize.dao.findFirst("select * from pageauthorize where OpenID =?",OpenID);
    }
    public static void save(SnsAccessToken sns){
        String OpenID = sns.getOpenid();
        String Access_token = sns.getAccessToken();
        String Refresh_token = sns.getRefresh_token();
        if( null == findByOpenID(OpenID) ){
            //创建 保存
            new PageAuthorize().set("OpenID",OpenID).set("Access_token",Access_token).set("Refresh_token",Refresh_token).save();
        }
        else{
            //update
            Db.update("update pageauthorize set Access_token=?,Refresh_token=? where OpenID=?",Access_token, Refresh_token,OpenID);
        }
    }
    public static  void update(Map refreshInfo){
        String OpenID = (String) refreshInfo.get("openid");
        String Access_token = (String) refreshInfo.get("access_token");
        String Refresh_token = (String) refreshInfo.get("refresh_token");
        Db.update("update pageauthorize set Access_token=?,Refresh_token=? where OpenID=?",Access_token, Refresh_token,OpenID);
    }


    public static boolean judge_accessToken(String OpenID){

        PageAuthorize pageAuthorize = PageAuthorize.dao.findFirst("select * from pageauthorize where OpenID=?",OpenID);
        if(null == pageAuthorize){
            return false;
        }

        String AccessToken = pageAuthorize.get("Access_token");
        String RefreshToken = pageAuthorize.get("Refresh_token");

        //验证access_token是否过期
        Map<String,String> queryParas = new HashMap<String, String>();
        queryParas.put("access_token",AccessToken);
        queryParas.put("openid",OpenID);
        String jsonResult = HttpKit.get("https://api.weixin.qq.com/sns/auth",queryParas);
        //使用alibaba Json 包解析 json 为map
        Map mapResult = (Map)JSON.parse(jsonResult);
        //System.out.println(maps.get("errmsg"));

        //过期
        if(!"ok".equals(mapResult.get("errmsg"))){
            return false;
        }

        return true;

    }


    public static boolean refresh_accessToken(String OpenID){
        PageAuthorize pageAuthorize = PageAuthorize.dao.findFirst("select * from pageauthorize where OpenID=?",OpenID);
        if(null == pageAuthorize){
            return false;
        }

        String RefreshToken = pageAuthorize.get("Refresh_token");

        Config config = ConfigService.getConfig();
        String token = config.getStr("Token");
        String appId = config.getStr("AppID");

        Map<String,String> queryParas = new HashMap<String, String>();
        queryParas.put("appid", appId);
        queryParas.put("grant_type","refresh_token");
        queryParas.put("refresh_token",RefreshToken);

        String jsonResult = HttpKit.get("https://api.weixin.qq.com/sns/oauth2/refresh_token",queryParas);
        Map mapResult = (Map)JSON.parse(jsonResult);

        //判断刷新是否成功
        if(mapResult.get("access_token") == null){
            return false;
        }
        //更新数据库
        update(mapResult);
        return true;
    }


    /**
     * 此方法带更新 没有判断是否存在
     * @param OpenID
     * @return
     */
    public static String getAccessToken(String OpenID){

        System.out.println(OpenID);

        PageAuthorize pageAuthorize = PageAuthorize.dao.findFirst("select * from pageauthorize where OpenID=?",OpenID);

        String AccessToken = pageAuthorize.get("Access_token");
        String RefreshToken = pageAuthorize.get("Refresh_token");

        //验证access_token是否过期
        Map<String,String> queryParas = new HashMap<String, String>();
        queryParas.put("access_token",AccessToken);
        queryParas.put("openid",OpenID);
        String jsonResult = HttpKit.get("https://api.weixin.qq.com/sns/auth",queryParas);

        //使用alibaba Json 包解析 json 为map
        Map mapResult = (Map)JSON.parse(jsonResult);
        //System.out.println(maps.get("errmsg"));

        //过期使用refresh_token重新获取
        if(!"ok".equals(mapResult.get("errmsg"))) {
            queryParas.clear();
            Config config = ConfigService.getConfig();
            String token = config.getStr("Token");
            String appId = config.getStr("AppID");

            queryParas.put("appid", appId);


            queryParas.put("grant_type","refresh_token");
            queryParas.put("refresh_token",RefreshToken);
            jsonResult = HttpKit.get("https://api.weixin.qq.com/sns/oauth2/refresh_token",queryParas);
            mapResult = (Map)JSON.parse(jsonResult);

            //更新数据库
            update(mapResult);
            AccessToken = (String) mapResult.get("access_token");
        }
        return AccessToken;
    }
}
