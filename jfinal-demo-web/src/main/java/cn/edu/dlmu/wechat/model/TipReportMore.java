package cn.edu.dlmu.wechat.model;

import cn.edu.dlmu.wechat.service.TaskAssignmentService;
import cn.edu.dlmu.wechat.service.TipReportService;
import com.jfinal.plugin.activerecord.Model;

/**
 * Created by yokiqust on 17-9-28.
 */
public class TipReportMore extends Model<TipReportMore> {
    public static final TipReportMore dao = new TipReportMore().dao();

    public TipReport getReport() {
        return TipReportService.findByID(getInt("ReportID"));
    }

    public TaskAssignment getAssignment() {
        return TaskAssignmentService.findByID(getInt("AssignID"));
    }
}
