package cn.edu.dlmu.wechat.config;

import cn.edu.dlmu.wechat.controller.*;

import cn.edu.dlmu.wechat.model.*;

import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;
import com.jfinal.weixin.sdk.api.ApiConfigKit;

import java.io.File;

public class wechatConfig extends JFinalConfig {
    public void configConstant(Constants me) {
        PropKit.use("config.properties");
        //????

        //修改日志文件路径
        String logFilesPath = System.getProperty("catalina.base")+ File.separator+"eyeon_logs";
        System.setProperty("logFilesPath",logFilesPath);

        me.setDevMode(PropKit.getBoolean("devMode", false));
        // ApiConfigKit 设为开发模式可以在开发阶段输出请求交互的 xml 与 json 数据
        ApiConfigKit.setDevMode(me.getDevMode());
    }

    public void configRoute(Routes me) {
        me.add("/msg", WeixinMsgController.class);
        me.add("/api", WeixinApiController.class);
        me.add("/wPage", WeixinWebpageController.class);
        me.add("/jsPage", WeixinJSSdkPageController.class);
        me.add("/donation", WeixinDonation.class);
        me.add("/rescue",WeixinRescue.class);
        me.add("/eyeon",AuthorizeController.class);
    }

    public void configEngine(Engine engine) {
        engine.setDevMode(true);
    }
    public void configPlugin(Plugins plugins) {
        loadPropertyFile("config.properties");

        DruidPlugin dp = new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password"));
        plugins.add(dp);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);

        plugins.add(arp);

        arp.setDialect(new MysqlDialect());
        //database mapping
        arp.addMapping("admin","AdminID", Admin.class);
        arp.addMapping("case","ID",Case.class);
        arp.addMapping("task","TaskID", Task.class);
        arp.addMapping("taskassignment","AssignID", TaskAssignment.class);
        arp.addMapping("tipreport","ID", TipReport.class);
        arp.addMapping("tipreportmore","ID", TipReportMore.class);
        arp.addMapping("wechatuser","ID", WeChatUser.class);
        arp.addMapping("pageauthorize","ID",PageAuthorize.class);
        //募捐模块
        //arp.addMapping("DonationUserInfo","DonationUserID",DonationUserInfo.class);
        //arp.addMapping("IdentityCode","CodeID",Identi tyCode.class);
        //救援模块
        arp.addMapping("rescue","Id",Rescue.class);
        arp.addMapping("rescueuserinfo","RescueUserID",RescueUserInfo.class);
        arp.addMapping("rescuesituation","RescueSituID",RescueSituation.class);
        arp.addMapping("resources","Id",Resources.class);
        arp.addMapping("config","Id",Config.class);
        //arp.addMapping("TaskRemain")
    }
    /**
     * 添加全局拦截器
     * @param interceptors
     */
    public void configInterceptor(Interceptors interceptors) {
    }
    public void configHandler(Handlers handlers) {
        handlers.add(new ContextPathHandler("ctx"));
    }
}
