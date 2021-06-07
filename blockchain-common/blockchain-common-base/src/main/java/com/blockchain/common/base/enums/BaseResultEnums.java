package com.blockchain.common.base.enums;

public enum BaseResultEnums {
    SUCCESS(200, "请求成功", "Request success",""),
    NO_LOGIN(201, "未登录", "No login",""),
    LOGIN_REPLACED(202, "您的账号在别处登录！", "",""),
    RSA_ERROR(250, "加密错误", "RSACoder error",""),
    BUSY(300, "服务器繁忙", "Server busy",""),
    DEFAULT(500, "系统错误", "System error",""),
    PASSWORD_ERROR(300, "密码错误", "Password error",""),
    PARAMS_ERROR(401, "参数异常", "Params error",""),
    ACCESS_DENIED(403,"你没有相应的权限","","你没有相应的权限");

    private int code;
    private String hkmsg;
    private String enMsg;
    private String cnmsg;

    BaseResultEnums(int code, String cnmsg, String enMsg,String hkmsg) {
        this.code = code;
        this.cnmsg = cnmsg;
        this.enMsg = enMsg;
        this.hkmsg = hkmsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getHkmsg() {
        return hkmsg;
    }

    public void setHkmsg(String hkmsg) {
        this.hkmsg = hkmsg;
    }

    public String getEnMsg() {
        return enMsg;
    }

    public void setEnMsg(String enMsg) {
        this.enMsg = enMsg;
    }

    public String getCnmsg() {
        return cnmsg;
    }

    public void setCnmsg(String cnmsg) {
        this.cnmsg = cnmsg;
    }
}
