package cn.edu.dlmu.wechat.model;

import cn.edu.dlmu.wechat.service.TaskAssignmentService;
import cn.edu.dlmu.wechat.service.TipReportService;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

/**
 * Created by yokiqust on 17-9-28.
 */
public class WeChatUser extends Model<WeChatUser> {
    public static final WeChatUser dao = new WeChatUser().dao();

    public List<TipReport> getReports() {
        return TipReportService.findByWeChatUser(this);
    }

    public List<TaskAssignment> getAssignments() {
        return TaskAssignmentService.findByWeChatUser(this);
    }

}
