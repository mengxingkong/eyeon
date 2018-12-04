package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.model.Rescue;
import cn.edu.dlmu.wechat.model.RescueSituation;
import com.jfinal.plugin.activerecord.Db;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class RescueSituationService {

    public static RescueSituation getInfo(String phone){
        //先查询自己当前是否存在需要营救的状态
        Rescue rescue = RescueService.findStatus(phone);
        int status = rescue.getInt("Status");
        if(status ==3 || status==4 ){
            //没有救援信息
            return null;
        }
        RescueSituation rescueSituation = RescueSituation.dao.findFirst("select * from rescuesituation rs,rescueuserinfo ru,wechatuser we \n" +
                "\t\t\twhere rs.RescueID=? and rs.RescueUserID=ru.RescueUserID and ru.OpenID = we.OpenID",rescue.getInt("Id"));
        return rescueSituation;
    }

    //0已接收救援 1成功 -1失败
    public static int exeRescue(String phone,int resId){
        //
        int uid = RescueUserInfoService.findId(phone);

        RescueSituation rstu = RescueSituation.dao.findFirst("select * from rescuesituation where RescueID = ? and RescueUserID=?",resId,uid);

        if(null != rstu){
            return 0;
        }
        //没有掺入
        int Status = RescueService.findStatusById(resId);
        if(0 ==Status || 1==Status || 2 == Status){
            RescueService.updateStatus(resId,2);
        }
        //先把用户状态置为一
        RescueUserInfoService.updateIsbasy(phone,1);
        insertExeRescue(uid,resId);
        return 1;
    }

    private static void insertExeRescue(int uid,int resId){
        Timestamp TB = new Timestamp(new Date().getTime());
        new RescueSituation().set("RescueUserID",uid).set("RescueID",resId).set("StartTime",TB).save();
    }

    public static int completeExe(String phone,int resId){
        RescueService.updateStatus(resId,3);
        int uid = RescueUserInfoService.updateIsbasy(phone,0);
        updateExe(uid,resId);
        return 1;

    }
    private static void updateExe(int uid,int resId){
        String text = "已经成功完成";
        Timestamp TE = new Timestamp(new Date().getTime());
        Db.update("update rescuesituation set RescueInformation=?,EndTime=? where RescueUserID=? and RescueID=?",text,TE,uid,resId);
    }

    public static List<RescueSituation> getDistuibute(String phone){
        int uid = RescueUserInfoService.findId(phone);
        return RescueSituation.dao.find("select * from rescue r,rescuesituation rs " +
                "where rs.RescueUserID =? and  rs.RescueID=r.Id and (r.Status=1 or r.Status=2)",uid);
    }

    //3 已经完成了 4 已经失败了 1成功
    public static int exeDistribute(String phone,int resId){
        int uid = RescueUserInfoService.findId(phone);
        int Status = RescueService.findStatusById(resId);
        if(1 == Status){
            RescueService.updateStatus(resId,2);
        }
        else if(3 == Status){
            return 3;
        }
        else if(4 == Status){
            return 4;
        }
        RescueUserInfoService.updateIsbasy(phone,1);
        Timestamp TB = new Timestamp(new Date().getTime());
        Db.update("update rescuesituation set StartTime=? where RescueUserID=? and RescueID=?",TB,uid,resId);
        return 1;
    }
    public static int completeDistri(String phone,int resId){
        RescueService.updateStatus(resId,3);
        int uid = RescueUserInfoService.updateIsbasy(phone,0);
        updateExe(uid,resId);
        return 1;
    }
}
