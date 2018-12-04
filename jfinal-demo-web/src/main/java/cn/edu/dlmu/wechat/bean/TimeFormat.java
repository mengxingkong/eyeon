package cn.edu.dlmu.wechat.bean;

import java.sql.Date;

public class TimeFormat {
    public static Date TimeStamptoDate(Long timeStamp){
        Date sqlDate = new Date(timeStamp);
        return sqlDate;
    }
}
