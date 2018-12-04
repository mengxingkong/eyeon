package cn.edu.dlmu.wechat.model;

import cn.edu.dlmu.wechat.service.AdminService;
import cn.edu.dlmu.wechat.service.TipReportService;
import cn.edu.dlmu.wechat.service.TaskService;


import com.jfinal.plugin.activerecord.Model;
import java.util.List;

public class Case extends Model<Case> {
    public final static Case dao = new Case().dao();

    public Admin getAdmin() {
        return AdminService.findByID(getInt("AdminID"));
    }

    public List<TipReport> getTipReports() {
        return TipReportService.findByCase(this);
    }

    public List<Task> getTasks() {
        return TaskService.findByCase(this);
    }
}
