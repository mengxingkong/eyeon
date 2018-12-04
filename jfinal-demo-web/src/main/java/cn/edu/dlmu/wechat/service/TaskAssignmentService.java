package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.model.Task;
import cn.edu.dlmu.wechat.model.TaskAssignment;
import cn.edu.dlmu.wechat.model.WeChatUser;
import com.jfinal.plugin.activerecord.Db;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static javafx.scene.input.KeyCode.L;

/**
 * Created by yokiqust on 17-9-28.
 */
public class TaskAssignmentService {
    public static List<TaskAssignment> findByTask(Task task) {
        return TaskAssignment.dao.find("select * from taskassignment where TaskID = ?", task.getInt("TaskID"));
    }

    public static TaskAssignment findByID(int id) {
        return TaskAssignment.dao.findById(id);
    }

    public static List<TaskAssignment> findByWeChatUser(WeChatUser user) {
        return TaskAssignment.dao.find("select * from taskassignment where UserID = ?", user.getInt("ID"));
    }

    public static boolean taskAssign(String OpenID,String taskID){
        //通过用户的OpenID 获取其ID
        int TaskID = Integer.parseInt(taskID);

        //这里有一个bug还没有找出来
        int UserID = WeChatUser.dao.findFirst("select ID from wechatuser where OpenID = ?",OpenID).get("ID");
        System.out.println("UserID"+UserID);
        //判断是否已经领取过该任务 在TaskAssign表中进行判断 //在taskService 中 taskList 列出事件的任务时 已经对用户选择了任务进行了过滤
        TaskAssignment taskAssignment = TaskAssignment.dao.findFirst("select * from taskassignment where UserID=? and TaskID=?",UserID,TaskID);
        if(null!=taskAssignment){
            return false;
        }
        //判断领取任务是否已经达到最大额度，从视图表进行判断
        //Db.queryInt("select remainCount from taskremain where TaskID = ?",TaskID) <= 0
        int maxAssignment = Task.dao.findById(TaskID).getInt("MaxAssignments");

        //"select count(*) from taskassignment where TaskID = ?",TaskID
        //count(*) 传回来的值是long 类型的
        // 待完成 --》 需要在分配表中取消对于 用户已经拒绝接收的任务《-- select count(*) c from taskassignment where TaskID = ? and Status != 1
        long assigned = Db.findFirst("select count(*) c from taskassignment where TaskID = ?",TaskID).getLong("c");

        if((long)maxAssignment-assigned <= 0){
            //任务被分配达到最大额度了
            return false;
        }

        //没有领取 尚有额度 则进行领取
        Timestamp AssignDate = new Timestamp(new Date().getTime());
        int AssignType = 0;//主动
        int Status = 2;//已指派，未完成
        float Reward = 0L;
        new TaskAssignment().set("UserID",UserID).set("TaskID",TaskID).set("AssignDate",AssignDate).set("AssignType",AssignType)
                .set("Status",Status).set("Reward",Reward).save();
        return true;
    }
    public static List<TaskAssignment> findByWeChatUserOpenID(String OpenID,int Status){
        if(Status != -1) {
            return TaskAssignment.dao.find(
                    "select task.*,t.* from `task`,taskassignment t,wechatuser w where t.UserID=w.ID and t.TaskID=`task`.TaskID and w.OpenID=? and t.Status = ?", OpenID, Status);
        }
        //返回用户领取过的所用任务
        return TaskAssignment.dao.find(
                "select task.*,t.* from `task`,taskassignment t,wechatuser w where t.UserID=w.ID and t.TaskID=`task`.TaskID and w.OpenID=? and t.status!=0",OpenID);
    }
    public static int statusUpdate(int AssignID,int Status){
        //领取过的任务 在本页面不能拒绝，拒绝过的任务不能接收
        TaskAssignment taskAssignment = findByID(AssignID);
        // 0 1 2 3 4 未接 拒绝 进行中 完成 未完成
        if(2 == Status){
            if(1 == taskAssignment.getInt("Status")){ //已经被拒绝了
                return 1;
            }
            if(4 == taskAssignment.getInt(("Status"))){
                return 4;
            }
        }
        if(1 == Status){
            if(2 == taskAssignment.getInt("Status")){
                return 2;
            }
        }
        Db.update("update taskassignment set Status =? where AssignID = ?",Status,AssignID);
        return Status;
    }
    public static Long taskCount(String OpenID,int status){
        return Db.queryLong("select count(*) from taskassignment t,wechatuser w where w.ID=t.UserID and w.OpenID=? and t.Status=? ",OpenID,status);
    }
}
