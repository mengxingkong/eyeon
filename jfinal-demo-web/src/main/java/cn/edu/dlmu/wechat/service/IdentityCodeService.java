package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.model.IdentityCode;
import com.jfinal.plugin.activerecord.Db;

import java.sql.Timestamp;

public class IdentityCodeService {
    public static void codeInsert(String p, String c, Timestamp TB, Timestamp TE){
        new IdentityCode().set("PhoneNum",p).set("Code",c).set("BeignTime",TB).set("EndTime",TE).save();
    }
    public static void codeUpdate(String p,String c,Timestamp TB,Timestamp TE){
        Db.update("update identitycode set Code=?,BeignTime=?,EndTime=? where PhoneNum = ?",c,TB,TE,p);
    }
    public static  boolean codeFindByPhoneNum(String phoneNumber){
        return IdentityCode.dao.findFirst("select * from identitycode where PhoneNum = ?",phoneNumber)!=null;
    }
    public static IdentityCode findCode(String phoneNum,String code){
        return IdentityCode.dao.findFirst("select * from identitycode where PhoneNum = ? and Code = ?",phoneNum,code);
    }
}
