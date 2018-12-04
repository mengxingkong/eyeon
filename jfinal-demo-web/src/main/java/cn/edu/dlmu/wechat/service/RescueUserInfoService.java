package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.model.IdentityCode;
import cn.edu.dlmu.wechat.model.RescueUserInfo;
import com.jfinal.plugin.activerecord.Db;

import java.sql.Timestamp;
import java.util.Date;

public class RescueUserInfoService {
    public static int findUserID(String OpenID){
        return RescueUserInfo.dao.findFirst("select RescueUserID from rescueuserinfo where OpenID = ?",
                OpenID).getInt("RescueUserID");
    }

    private static boolean findPhoneNumber(String phoneNumber){
        if(RescueUserInfo.dao.findFirst("select * from rescueuserinfo where PhoneNumber = ?",phoneNumber)!=null){
            return true;
        }
        return false;
    }

    // 1 注册成功 0号码已经存在 -1验证码错误  -2验证码过期  注册前获取短信验证码
    public static int register(String phoneNumber,String code,String OpenID){
        if(identity(phoneNumber)){
            return 0;
        }
        //根据电话和验证码吧表中数据取出来
        IdentityCode icode = IdentityCodeService.findCode(phoneNumber,code);
        //判断是否为空
        if(null==icode){
            return -1;
        }
        //判断是否过期
        Timestamp TE = icode.getTimestamp("EndTime");
        Timestamp TN = new Timestamp(new Date().getTime());
        if(TN.compareTo(TE)>0){
            return -2;
        }
        String userName = WeChatUserService.fingNameByOpenID(OpenID);
        new RescueUserInfo().set("OpenID",OpenID).set("PhoneNumber",phoneNumber).set("Name",userName).save();
        return 1;
    }

    //1 登录成功 0 没有注册 -1 验证码错误 -2 验证码过期
    public static int login(String phoneNumber,String code,String OpenID){
        if(!identity(phoneNumber)){
            return 0;
        }
        //根据电话和验证码吧表中数据取出来
        IdentityCode icode = IdentityCodeService.findCode(phoneNumber,code);
        if(null==icode){
            return -1;
        }
        //判断是否过期
        Timestamp TE = icode.getTimestamp("EndTime");
        Timestamp TN = new Timestamp(new Date().getTime());
        if(TN.compareTo(TE)>0){
            return -2;
        }
        return 1;
    }

    public static boolean identity(String phoneNumber){
        //注册时从数据库查询是否已经存在该手机号
        //登录时检测该手机号是否存在
        return RescueUserInfoService.findPhoneNumber(phoneNumber);
    }

    public static RescueUserInfo getUserInfo(String phone){
        return RescueUserInfo.dao.findFirst("select * from rescueuserinfo ru,wechatuser we " +
                "where ru.OpenID = we.OpenID and ru.PhoneNumber = ?",phone);
    }

    public static int updateIsbasy(String phone,int isBasy){
        Db.update("update rescueuserinfo set IsBusy=? where PhoneNumber=?",isBasy,phone);
        return RescueUserInfo.dao.findFirst("select * from rescueuserinfo where PhoneNumber=?",phone).getInt("RescueUserID");
    }

    public static int findId(String phone){
        return RescueUserInfo.dao.findFirst("select * from rescueuserinfo where PhoneNumber=?",phone).getInt("RescueUserID");
    }

    public static RescueUserInfo findLocation(String phone){
        return RescueUserInfo.dao.findFirst("select * from rescueuserinfo ri,wechatuser we " +
                "where ri.OpenID=we.OpenID and ri.PhoneNumber=?",phone);
    }
}
