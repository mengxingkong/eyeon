package cn.edu.dlmu.wechat.model;

import com.jfinal.plugin.activerecord.Model;
import java.util.List;

import cn.edu.dlmu.wechat.service.CaseService;
import cn.edu.dlmu.wechat.service.TaskService;


public class Admin extends Model<Admin> {
    public static final Admin dao = new Admin().dao();

    public List<Case> getCases() {
        return CaseService.findByAdmin(this);
    }

    public List<Task> getTasks() {
        return TaskService.findByAdmin(this);
    }
}
