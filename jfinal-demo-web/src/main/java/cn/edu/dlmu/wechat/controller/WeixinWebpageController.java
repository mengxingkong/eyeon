package cn.edu.dlmu.wechat.controller;

import cn.edu.dlmu.wechat.bean.AmrToBase64;
import cn.edu.dlmu.wechat.bean.PathBulid;
import cn.edu.dlmu.wechat.model.*;
import cn.edu.dlmu.wechat.service.*;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiResult;
import com.jfinal.weixin.sdk.api.SnsAccessToken;
import com.jfinal.weixin.sdk.api.SnsAccessTokenApi;
import com.jfinal.weixin.sdk.api.SnsApi;
import java.util.List;

public class WeixinWebpageController extends Controller {
    public WeixinWebpageController() {

    }

    public ApiConfig getApiConfig() {
        ApiConfig ac = new ApiConfig();
        Config config = ConfigService.getConfig();
        String token = config.getStr("Token");
        String appId = config.getStr("AppID");
        String appSecret = config.getStr("Appsecret");
        ac.setToken(token);
        ac.setAppId(appId);
        ac.setAppSecret(appSecret);
        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false).booleanValue());
        return ac;
    }

    public void index() {
        String code = this.getPara("code");
        String state = this.getPara("state");

        System.out.println(code+"   *****  "+getSessionAttr("code"));

        setSessionAttr("code",code);
//        if(getSessionAttr("code")==null ){
//            setSessionAttr("code",code);
//        }
//        else{
//            //code存在不相等
//            System.out.println("重复使用了code");
//        }

        SnsAccessToken snsAccessToken = this.getAccessTokenByCode(code);

        PageAuthorizeService.save(snsAccessToken);

        this.setSessionAttr("OpenID", snsAccessToken.getOpenid());

        System.out.println(state);

        if ("homePage".equals(state)) {
            this.redirect("/wPage/homePage?OpenID=" + snsAccessToken.getOpenid());
        } else if ("tipReport".equals(state)) {
            this.redirect("/jsPage/tipReportPage?OpenID=" + snsAccessToken.getOpenid());
        } else if ("userTasks".equals(state)) {
            this.redirect("/wPage/taskList?OpenID=" + snsAccessToken.getOpenid());
        } else if ("userAssignment".equals(state)) {
            this.redirect("/wPage/userAssignment?OpenID=" + snsAccessToken.getOpenid());
        }

    }

    public void homePage() {
        String OpenID = this.getSessionAttr("OpenID");
        String AccessToken = PageAuthorizeService.getAccessToken(OpenID);
        ApiResult UserInfo = this.getUserInfo(AccessToken, OpenID);
        String headImgUrl = UserInfo.get("headimgurl");
        String nickName = UserInfo.get("nickname");
        long unAccept = TaskAssignmentService.taskCount(OpenID, 0).longValue();
        this.setAttr("unAccept", unAccept);
        this.setAttr("headImgUrl", headImgUrl);
        this.setAttr("nickName", nickName);
        this.render("homePage.html");
    }

    public void tipReport() {
        this.render("tipReport.html");
    }

    public void tipReportMorePage() {
        String OpenId = (String)this.getSessionAttr("OpenID");
        this.render("/jsPage/TipReportMore.html");
    }

    public void tipReportList() {
        String CaseID = this.getPara("CaseID");
        System.out.println(this.getRequest().getQueryString());
        System.out.println(CaseID);
        List<TipReport> tipReports = TipReportService.findAll(CaseID);
        PathBulid.buildPath(tipReports);
        this.setAttr("tipReports", tipReports);
        this.render("tipReportList.html");
    }

    public void userTipreport(){
        String OpenID = getSessionAttr("OpenID");
        WeChatUser weChatUser = WeChatUserService.findByOpenID(OpenID);
        List<TipReport> tipReports = TipReportService.findByWeChatUser(weChatUser);
        PathBulid.buildPath(tipReports);
        this.setAttr("tipReports", tipReports);
        this.render("tipReportList.html");
    }

    public void caseList() {
        List<Case> cases = CaseService.caseWithPicture();
        this.setAttr("cases", cases);
        this.render("caseList.html");
    }

    public void taskList() {
        String OpenID = (String)this.getSessionAttr("OpenID");
        String question = this.getPara("question");
        List<Task> tasks = TaskService.findUserNotAssinged(question, OpenID);
        this.setAttr("tasks", tasks);
        this.render("taskList.html");
    }

    public void taskAssign() {
        String OpenID = (String)this.getSessionAttr("OpenID");
        String taskID = this.getPara("taskID");
        if (!TaskAssignmentService.taskAssign(OpenID, taskID)) {
            this.renderJson("{\"result\":\"error\"}");
        }

        this.renderJson("{\"result\":\"ok\"}");
    }

    public void userTasks() {
        String OpenID = (String)this.getSessionAttr("OpenID");
        List<TaskAssignment> taskAssignments = TaskAssignmentService.findByWeChatUserOpenID(OpenID, -1);
        this.setAttr("tasks", taskAssignments);
        this.render("userTasks.html");
    }

    public void userAssignment() {
        String OpenID = (String)this.getSessionAttr("OpenID");
        List<TaskAssignment> taskAssignments = TaskAssignmentService.findByWeChatUserOpenID(OpenID, 0);
        this.setAttr("tasks", taskAssignments);
        this.render("userAssignment.html");
    }

    public void assignConfirm() {
        String type = this.getPara("type");
        int AssignID = Integer.parseInt(this.getPara("AssignID"));
        byte status;
        if ("confirm".equals(type)) {
            status = 2;
        } else {
            status = 1;
        }

        int res = TaskAssignmentService.statusUpdate(AssignID, status);
        if (status == 2 && res == 1) {
            this.renderJson("{\"result\":\"refused\"}");
        } else if (status == 2 && res == 4) {
            this.renderJson("{\"result\":\"overtime\"}");
        } else if (status == 1 && res == 2) {
            this.renderJson("{\"result\":\"recieved\"}");
        }

        this.renderJson("{\"result\":\"ok\"}");
    }

    public void articlePage() {
        int tipID = Integer.parseInt(this.getPara("tipID"));
        int status = Integer.parseInt(this.getPara("status"));
        TipReport tipReport = TipReportService.findByID(tipID);
        PathBulid.buildPath(tipReport);
        if (1 == status) {
            List<TipReportMore> tipReportMores = TipReportMoreService.findByReportID(tipID);
            PathBulid.rebuiltTipMore(tipReportMores);
            this.setAttr("tipMores", tipReportMores);
        }

        this.setAttr("tip", tipReport);
        this.setAttr("status", status);
        this.render("article.html");
    }

    public void playAudio(){
        String audioPath = getPara("audioPath");
        //如果不存在 怎返回一个不存在的路径
        audioPath = getRequest().getSession().getServletContext().getRealPath(audioPath);
        String result = "";
        AmrToBase64 base64 = new AmrToBase64();
        String baseStr = "";
        try {
            baseStr = base64.encodeBase64File(audioPath);
        }
        catch (Exception e){
            result = "error";
        }
        System.out.println(audioPath);
        if(!"error".equals(result)){
            result = "ok";
            setAttr("base64",baseStr);
        }
        setAttr("result",result);
        renderJson();
    }

    private SnsAccessToken getAccessTokenByCode(String code) {
        Config config = ConfigService.getConfig();
        String token = config.getStr("Token");
        String appId = config.getStr("AppID");
        String appSecret = config.getStr("Appsecret");
        SnsAccessToken snsAccessToken = SnsAccessTokenApi.getSnsAccessToken(appId, appSecret, code);
        return snsAccessToken;
    }

    private ApiResult getUserInfo(String AccessToken, String OpenID) {
        return SnsApi.getUserInfo(AccessToken, OpenID);
    }
}
