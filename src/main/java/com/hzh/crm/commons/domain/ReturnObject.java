package com.hzh.crm.commons.domain;

/**
 * @author DAHUANG
 * @date 2022/3/10
 */
public class ReturnObject {
    //成功获取的标记 1----成功   0----失败
    private String code;
    //获取提示消息
    private String message;
    //其他数据
    private Object object;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
