package cn.edu.dlmu.wechat.model;

import com.jfinal.plugin.activerecord.Model;

public class Config extends Model<Config>{
    public static final Config dao = new Config().dao();
}
