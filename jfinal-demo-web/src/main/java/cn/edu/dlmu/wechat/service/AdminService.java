package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.model.Admin;

public class AdminService {
    public static boolean Login(Admin admin) {
        Admin a = Admin.dao.findFirst("select * from admin where AdminName = ? and AdminPwd = ?", admin.get("AdminName"), admin.get("AdminPwd"));
        return a == null ? false : true;
    }
    public static Admin findByID(int adminID) {
        return Admin.dao.findById(adminID);
    }
}

