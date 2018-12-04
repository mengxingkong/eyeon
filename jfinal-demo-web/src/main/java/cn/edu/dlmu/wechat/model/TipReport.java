package cn.edu.dlmu.wechat.model;

import cn.edu.dlmu.wechat.service.CaseService;
import cn.edu.dlmu.wechat.service.TipReportMoreService;
import cn.edu.dlmu.wechat.service.WeChatUserService;
import com.jfinal.plugin.activerecord.Model;

public class TipReport extends Model<TipReport> {
    public static final TipReport dao = new TipReport().dao();

    public WeChatUser getTipster() {
        return WeChatUserService.findByID(getInt("Tipster"));
    }

    public boolean isReportMore() {
        return getInt("IsMore") == 1 ? true : false;
    }

    public TipReportMore getReportMore() {
        return isReportMore() ? TipReportMoreService.findByReport(this) : null;
    }

    public Case getCase() {
        return getInt("CaseID") != null ? CaseService.findByID(getInt("CaseID")) : null;
    }

}