package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.bean.PathBulid;
import cn.edu.dlmu.wechat.model.Admin;
import cn.edu.dlmu.wechat.model.Case;
import cn.edu.dlmu.wechat.model.TipReport;

import java.util.List;

/**
 * Created by yokiqust on 17-9-28.
 */
public class CaseService {
    public static Case findByID(int id) {
        return Case.dao.findById(id);
    }

    public static  List<Case> findAll(){
        return Case.dao.find("select * from case");
    }

    public static List<Case> findByAdmin(Admin admin) {
        return Case.dao.find("select * from case where AdminID = ?", admin.getInt("AdminID"));
    }

    public static List<Case> relationWithAdmin(){
        //String sql  = "select * from `case`";
        String sql = "select c.*,a.AdminName from `case` c,admin a where c.AdminID = a.AdminID";
        return Case.dao.find(sql);
    }

    public static  List<Case> caseWithPicture(){
        List<Case> caseList = relationWithAdmin();
        for (Case c:
                caseList) {
            TipReport tipReport = TipReportService.findFirstByCase(c);
            if(null == tipReport){
                continue;
            }
            PathBulid.buildPath(tipReport);
            c.put("pic",tipReport.get("IMGDIR"));
        }
        return caseList;
    }

}
