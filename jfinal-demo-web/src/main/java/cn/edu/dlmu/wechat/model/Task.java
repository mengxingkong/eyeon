package cn.edu.dlmu.wechat.model;

import cn.edu.dlmu.wechat.service.AdminService;
import cn.edu.dlmu.wechat.service.CaseService;
import cn.edu.dlmu.wechat.service.TaskAssignmentService;
import com.jfinal.plugin.activerecord.Model;

import java.util.List;

public class Task extends Model<Task> {
    public static final Task dao = new Task().dao();

    public Admin getAdmin() {
        return AdminService.findByID(getInt("AdminID"));
    }

    public Case getCase() {
        return CaseService.findByID(getInt("Question"));
    }

    public List<TaskAssignment> getTAssignments() {
        return TaskAssignmentService.findByTask(this);
    }
}

