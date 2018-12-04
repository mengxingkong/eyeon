package cn.edu.dlmu.wechat.service;

import cn.edu.dlmu.wechat.model.Config;

public class ConfigService {
    public static Config getConfig(){
        return Config.dao.findFirst("select * from config where Id = 1");
    }
}
