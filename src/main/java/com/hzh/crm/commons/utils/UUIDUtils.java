package com.hzh.crm.commons.utils;

import java.util.UUID;

/**
 * @author DAHUANG
 * @date 2022/3/13
 */
public class UUIDUtils {

    /**
     * 获取uuid字符串
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
