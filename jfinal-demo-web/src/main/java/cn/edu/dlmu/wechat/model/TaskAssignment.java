package cn.edu.dlmu.wechat.model;

import com.jfinal.plugin.activerecord.Model;

import java.util.List;

import cn.edu.dlmu.wechat.service.TaskService;
import cn.edu.dlmu.wechat.service.WeChatUserService;
import cn.edu.dlmu.wechat.service.TipReportMoreService;

public class TaskAssignment extends Model<TaskAssignment> {
    public static final TaskAssignment dao = new TaskAssignment().dao();

    public Task getTask() {
        return TaskService.findByID(getInt("TaskID"));
    }

    public List<TipReportMore> getReportMores() {
        return TipReportMoreService.findByAssignment(this);
    }

    public WeChatUser getWeChatUser() {
        return WeChatUserService.findByID(getInt("UserID"));
    }
}