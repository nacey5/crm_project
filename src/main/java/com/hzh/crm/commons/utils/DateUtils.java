package com.hzh.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author DAHUANG
 * @date 2022/3/11
 */
public class DateUtils {

    /**
     *对指定的date对象进行格式化输出
     * @param date
     * @return
     */
    public static String formateDateTime(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String dateStr=sdf.format(date);
        return dateStr;
    }
}
