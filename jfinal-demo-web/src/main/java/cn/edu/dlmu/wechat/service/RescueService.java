package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.model.Rescue;
import com.jfinal.plugin.activerecord.Db;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class RescueService {
    public static void insertRescue(String name,String phone,String location,String describe,String lng,String lat,String OpenID){
        int rescueUserID = RescueUserInfoService.findUserID(OpenID);
        Timestamp time = new Timestamp(new Date().getTime());
        new Rescue().set("RescueUserID",rescueUserID).set("HelpName",name).set("Phone",phone).set("Longitude",lng)
                .set("Latitude",lat).set("Address",location).set("Describe",describe).set("Status",0).set("RescueTime",time).save();
    }
    public static Rescue findStatus(String phone){
        //select * from table where ID =(select max(ID) from table)
        return Rescue.dao.findFirst("select * from rescue where Phone=? and RescueTime = (select max(RescueTime))",phone);
    }

    public static List<Rescue> getNearby(String lng,String lat){
        //之前需要进行举例判断
        return Rescue.dao.find("select * from rescue where `Status` = 0 or `Status` = 1 or `Status` = 2");
    }

    public static int findStatusById(int id){
        return Rescue.dao.findFirst("select * from rescue where `Id` = ?",id).getInt("Status");
    }

    public static void updateStatus(int id,int status){
        Db.update("update rescue set `Status`=? where Id = ?",status,id);
    }
    public static Rescue findById(int id){
        return Rescue.dao.findFirst("select * from rescue where Id = ?",id);
    }
}
