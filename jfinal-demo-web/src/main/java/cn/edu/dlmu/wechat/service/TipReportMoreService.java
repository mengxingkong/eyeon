package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.model.TaskAssignment;
import cn.edu.dlmu.wechat.model.TipReport;
import cn.edu.dlmu.wechat.model.TipReportMore;

import java.util.List;

/**
 * Created by yokiqust on 17-9-28.
 */
public class TipReportMoreService {
    public static List<TipReportMore> findByAssignment(TaskAssignment ta) {
        return TipReportMore.dao.find("select * from tipreportmore where AssignID = ?", ta.getInt("AssignID"));
    }

    public static TipReportMore findByReport(TipReport tr) {
        return TipReportMore.dao.findFirst("select * from tipreportmore where ReportID = ?", tr.getInt("ID"));
    }

    public static void save(String Depict,int ReportID,int AssignID,String SoundDir,String VideoDir){
        new TipReportMore().set("Depict",Depict).set("ReportID",ReportID).set("AssignID",AssignID)
                .set("SoundDir",SoundDir).set("VideoDir",VideoDir).save();
    }

    public static List<TipReportMore> findByReportID(int tipID){

        return TipReportMore.dao.find("select * from tipreportmore where ReportID = ?",tipID);

    }

}
