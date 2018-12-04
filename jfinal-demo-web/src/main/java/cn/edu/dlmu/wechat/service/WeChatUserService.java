package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.bean.EmojiFilter;
import cn.edu.dlmu.wechat.bean.TimeFormat;
import cn.edu.dlmu.wechat.model.WeChatUser;
import com.alibaba.fastjson.JSON;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.msg.in.event.InFollowEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import org.apache.commons.codec.binary.Base64;
import sun.nio.cs.ext.ISCII91;


import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 这个类是不是应该只有一个实例
 */
public class WeChatUserService {

    //创建单例


    public static WeChatUser findByID(int id) {
        return WeChatUser.dao.findById(id);
    }

    public static List<WeChatUser> findAll() {
        return WeChatUser.dao.find("select * from wechatuser");
    }

    public static WeChatUser findByOpenID(String OpenID){
        return WeChatUser.dao.findFirst("select * from wechatuser where OpenID = ?",OpenID);
    }

    public static String fingNameByOpenID(String OpenID){
        return WeChatUser.dao.findFirst("select * from wechatuser where OpenID = ?",OpenID).getStr("UserName");
    }


    public static Object findProperty(String condition,String property){
        Record record = Db.findFirst("select * from wechatuser where OpenID = ?",condition);
        return record.getInt(property);
    }

    public static void save(ApiResult userInfo){
        String OpenID = userInfo.getStr("openid");
        String Address = userInfo.getStr("country")+" "+userInfo.getStr("province")+" "+userInfo.getStr("city");
        String UserName = userInfo.getStr("nickname");
        String HeadImg = userInfo.getStr("headimgurl");
        int Sex = userInfo.getInt("sex");
        int Quota = 0;

        //判断微信用户的nickName中是否包括含了 特殊字符
        EmojiFilter emojiFilter = new EmojiFilter();
        if(emojiFilter.containsEmoji(UserName)){
            //如果包含 则先编码在存储
            //进行编码
            try {
                UserName = Base64.encodeBase64String(UserName.getBytes("UTF-8"));
                //进行解码
                //UserName = new String(Base64.decodeBase64(UserName), "UTF-8");
            }catch (UnsupportedEncodingException e){
                UserName = "默认昵称";
            }
        }


        Timestamp BeginTime = new Timestamp(new Date().getTime());
        //时间格式不太正确  写博客
        //Timestamp BeginTime = new Timestamp(userInfo.getLong("subscribe_time")*1000L);

        System.out.println();
        float Credit = 0.0f;
        new WeChatUser().set("OpenID",OpenID).set("Address",Address).set("UserName",UserName).set("HeadImg",HeadImg)
                .set("Sex",Sex).set("Quota",Quota).set("BeginTime",BeginTime).set("Credit",Credit).set("IsFollowed",1).save();
    }

    public static void update(ApiResult userInfo){
        String OpenID = userInfo.getStr("openid");
        String Address = userInfo.getStr("country")+" "+userInfo.getStr("province")+" "+userInfo.getStr("city");
        if(Address.equals("")){
            Address="未获取到位置信息";
        }
        String UserName = userInfo.getStr("nickname");
        String HeadImg = userInfo.getStr("headimgurl");
        int Sex = userInfo.getInt("sex");
        Db.update("update wechatuser set Address=?,UserName=?,HeadImg=? where OpenID=?",Address,UserName,HeadImg,OpenID);
    }


    public static void update(String openID,String lat,String lon){
        String GPS = lat+" "+lon;
        Db.update("update wechatuser set GPS = ? where OpenID = ?",GPS,openID);
    }
    public static void updateIsSubscribe(String openid,String eventType){
        //update 语句采用将 参数封装为object的方法
        //0未关注 1已关注 2 正在发送举报信息
        int IsSubscribe = 0;
        if(InFollowEvent.EVENT_INFOLLOW_SUBSCRIBE.equals(eventType)){
            IsSubscribe = 1;
        }
        else if(InFollowEvent.EVENT_INFOLLOW_UNSUBSCRIBE.equals(eventType)){
            IsSubscribe = 0;
        }
        Db.update("update wechatuser set IsFollowed =? where OpenID = ?",IsSubscribe,openid);
    }

    public static void updateGPS(String GPS,String OpenID){
        Map loc = (Map) JSON.parse(GPS);
        String lat = loc.get("latitude").toString();
        String lon = loc.get("longitude").toString();
        GPS = lat+" "+lon;
        Db.update("update wechatuser set GPS =? where OpenID=?",GPS,OpenID);
    }

    //方法暂时放弃使用了
    public  static void updateIsSubscribe(String OpenID,String eventType,String keyValue){
        int IsSubscribe = 1;
        if(InMenuEvent.EVENT_INMENU_CLICK.equals(eventType)){
            if("TipReport_start".equals(keyValue)){
                IsSubscribe = 2;
            }
            else if("TipReport_end".equals(keyValue)){
                IsSubscribe = 1;
            }
        }
        Db.update("update wechatuser set IsSubscribe =? where OpenID = ?",IsSubscribe,OpenID);
    }
}