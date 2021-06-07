package com.blockchain.server.user.common.enums;

/**
 * @author huangxl
 * @data 2019/2/21 20:53
 */
public enum UserEnums {
    USER_LIST_EXISTS(1100, "该用户已存在名单列表", "", "该用户已存在名单列表"),
    USER_NOT_EXISTS(1101, "不存在该用户", "There is no such user", "不存在該用戶"),
    AUTHENTICATION_APPLY_NOT_EXISTS(1102, "该用户没有初级审核申请表", "This user does not have a preliminary review application form", "該用戶沒有初級審核申請表"),
    LOGIN_PASSWORD_ERROR(1102, "用户名密码错误", "username or password is wrong", "用戶名密碼錯誤"),
    LOGIN_FORBIDDEN(1103, "你被列入黑名单，禁止登录", "You are blacklisted and login is forbidden", "你被列入黑名單，禁止登錄"),
    PASSWORD_EXIST(1127, "设置失败，你已经设置过密码了", "Setup failed, you have already set a password", "設置失敗，你已經設置過密碼了"),
    PASSWORD_NOT_MATCH(1129, "密码不匹配", "Passwords do not match", "密碼不匹配"),
    USER_MOBILE_PHONE_EXISTS(1130, "用户号码已存在", "User number already exists", "用戶號碼已存在"),
    USER_MOBILE_PHONE_UPDATE_ERROR(1131, "修改用户号码异常，请稍后重试！", "Modify the user number is abnormal, please try again later!", "修改用戶號碼異常，請稍後重試！"),
    USER_IDENTITY_CODE_EXISTS(1130, "身份证号码已存在", "ID card number already exists", "身份證號碼已存在"),
    USER_IDENTITY_CODE_UPDATE_ERROR(1131, "修改身份证号码异常，请稍后重试！", "The ID card number is abnormal. Please try again later!", "修改身份證號碼異常，請稍後重試！"),
    ;


    private int code;
    private String hkmsg;
    private String enMsg;
    private String cnmsg;

    UserEnums(int code, String cnmsg, String enMsg, String hkmsg) {
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
