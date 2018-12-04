package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.model.Resources;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

public class ResourcesService {
    public static List<Resources> showResources(String lng,String lat){
        //距离判断就先不写了
        return Resources.dao.find("select * from resources");
    }

    //1 成功 0 资源使用完了
    public static int resRequest(int resID){
        int isUse = Resources.dao.findFirst("select * from resources where Id = ?",resID)
                .getInt("IsUse");
        if(1 == isUse){
            return 0;
        }
        Db.update("update resources set IsUse = 1 where Id=?",resID);
        return 1;
    }
}
