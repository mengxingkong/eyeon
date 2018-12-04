package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.model.Case;
import cn.edu.dlmu.wechat.model.TipReport;
import cn.edu.dlmu.wechat.model.WeChatUser;
import com.alibaba.fastjson.JSON;
import com.jfinal.weixin.sdk.msg.in.InImageMsg;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yokiqust on 17-9-28.
 */
public class TipReportService {

    public static List<TipReport> findByCase(Case c) {
        return TipReport.dao.find("select * from tipreport where CaseID = ?", c.getInt("ID"));
    }

    public static TipReport findFirstByCase(Case c){
        return TipReport.dao.findFirst("select * from tipreport where CaseID = ?", c.getInt("ID"));
    }

    public static List<TipReport> findAll(String caseID){
        if(null!=caseID){
            int CaseID = Integer.parseInt(caseID);
            return TipReport.dao.find("select * from tipreport where CaseID=?",caseID);
        }
        return TipReport.dao.find("select * from tipreport");
    }


    public static TipReport findByID(int id) {
        return TipReport.dao.findById(id);
    }

    public static List<TipReport> findByWeChatUser(WeChatUser user) {
        return TipReport.dao.find("select * from tipreport where Tipster = ?", user.getInt("ID"));
    }


    public static void saveFromWeb(String OpenID,String address,String[] IMGDIR){

        Timestamp time = new Timestamp(new Date().getTime());
        WeChatUser weChatUser = WeChatUserService.findByOpenID(OpenID);
        String GPS = weChatUser.getStr("GPS");
        int Tipster = weChatUser.getInt("ID");
        if(null == address){
            address = weChatUser.getStr("Address");
        }
        String Latitude = GPS.split(" ")[0];
        String Longitude = GPS.split(" ")[1];

        for(int i=0;i<IMGDIR.length;i++) {
            new TipReport().set("Time", time).set("Location", address).set("Longitude", Longitude)
                    .set("Latitude", Latitude).set("Tipster", Tipster).set("IMGDIR", IMGDIR[i]).set("IsMore", 0).save();
        }

    }

    /**
     * 将用户发送的简单举报的信息写入数据库 -->picture
     * Location 和经纬度坐标是不是重复了
     */
    public static void save(InImageMsg inImageMsg,String IMGDIR){
        String OpenID = inImageMsg.getFromUserName();
        //Date Time = TimeFormat.TimeStamptoDate(Long.parseLong(inImageMsg.getCreateTime().toString()));
        Timestamp Time = new Timestamp(Long.parseLong((inImageMsg.getCreateTime().toString()))*1000L);
        WeChatUser weChatUser = WeChatUserService.findByOpenID(OpenID);
        String GPS = weChatUser.getStr("GPS");
        int Tipster = weChatUser.getInt("ID");
        String Location = weChatUser.getStr("Address");
        String Latitude = GPS.split(" ")[0];
        String Longitude = GPS.split(" ")[1];
        new TipReport().set("Time",Time).set("Location",Location).set("Longitude",Longitude)
                .set("Latitude",Latitude).set("Tipster",Tipster).set("IMGDIR",IMGDIR).set("IsMore",0).save();
    }

    public static int save(String Location,String OpenID,int CaseID,String Path){
        //解析JSON字符串
        Map loc = (Map) JSON.parse(Location);
        String lat = loc.get("latitude").toString();
        String lon = loc.get("longitude").toString();

        //根据OpenID 查询用户的ID
        WeChatUser weChatUser = WeChatUserService.findByOpenID(OpenID);
        int Tipster = weChatUser.getInt("ID");
        //用户当前位置
        String location = weChatUser.getStr("Address");

        //获取当前时间戳
        Timestamp Time = new Timestamp(new Date().getTime());

        //存入数据库

        TipReport tipReport = new TipReport().set("Time",Time).set("Location",location).set("Longitude",lon).set("Latitude",lat)
                .set("Tipster",Tipster).set("IMGDIR",Path).set("IsMore",1).set("CaseID",CaseID);
        tipReport.save();
        return tipReport.getInt("ID");
    }
}
