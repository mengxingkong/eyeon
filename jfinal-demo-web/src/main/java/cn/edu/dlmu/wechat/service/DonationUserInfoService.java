package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.bean.MessageSend;
import cn.edu.dlmu.wechat.model.DonationUserInfo;
import cn.edu.dlmu.wechat.model.IdentityCode;

import java.util.Date;
import java.sql.Timestamp;

public class DonationUserInfoService {

    public static boolean findPhoneNumber(String phoneNumber){
        if(DonationUserInfo.dao.findFirst("select * from donationuserinfo where PhoneNumber = ?",phoneNumber)!=null){
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
        new DonationUserInfo().set("OpenID",OpenID).set("PhoneNumber",phoneNumber).save();
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
        return DonationUserInfoService.findPhoneNumber(phoneNumber);
    }
}
