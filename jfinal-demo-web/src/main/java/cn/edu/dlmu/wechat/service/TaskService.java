package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.model.Admin;
import cn.edu.dlmu.wechat.model.Task;
import cn.edu.dlmu.wechat.model.Case;

import java.util.LinkedList;
import java.util.List;

public class TaskService {
    public static List<Task> findByAdmin(Admin admin) {
        return Task.dao.find("select * from task where AdminID = ?", admin.getInt("AdminID"));
    }

    public static List<Task> findByCase(Case c) {
        return Task.dao.find("select * from task where Queston = ?", c.getInt("ID"));
    }

    public static List<Task> findAll(String question){
        if(null != question){
            int Question = Integer.parseInt(question);
            return Task.dao.find("select * from task where Question=?",Question);
        }
        return Task.dao.find("select * from task");
    }

    public static Task findByID(int id) {
        return Task.dao.findById(id);
    }

    public static List<Task> findUserNotAssinged(String question,String OpenID){
        if(null != question){
            int Question;
            try {
                Question = Integer.parseInt(question);
            }catch (Exception e){
                return new LinkedList<Task>();
            }
            return Task.dao.find("SELECT\n" +
                    "\t*\n" +
                    "FROM\n" +
                    "\ttask\n" +
                    "WHERE\n" +
                    "\tQuestion = ?\n" +
                    "AND TaskID NOT IN (\n" +
                    "\tSELECT\n" +
                    "\t\tTaskID\n" +
                    "\tFROM\n" +
                    "\t\twechatuser w,\n" +
                    "\t\ttaskassignment t\n" +
                    "\tWHERE\n" +
                    "\t\tw.ID = t.UserID\n" +
                    "\tAND w.OpenID = ?\n" +
                    ")",Question,OpenID);
        }

        return Task.dao.find("SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\ttask\n" +
                "WHERE\n" +
                "\tTaskID NOT IN (\n" +
                "\t\tSELECT\n" +
                "\t\t\tTaskID\n" +
                "\t\tFROM\n" +
                "\t\t\twechatuser w,\n" +
                "\t\t\ttaskassignment t\n" +
                "\t\tWHERE\n" +
                "\t\t\tw.ID = t.UserID\n" +
                "\t\tAND w.OpenID = ?\n" +
                "\t)",OpenID);
    }
}
