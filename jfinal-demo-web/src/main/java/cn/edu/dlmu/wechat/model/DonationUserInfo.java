package cn.edu.dlmu.wechat.model;

import com.jfinal.plugin.activerecord.Model;

public class DonationUserInfo extends Model<DonationUserInfo> {
    public static final DonationUserInfo dao = new DonationUserInfo().dao();
}
