package com.blockchain.server.system.common.enums;

public enum SystemResultEnums {
    ACCOUNT_EXISTS(1000, "该账户名已存在"),
    USER_DOES_NOT_EXIST(1001, "用户不存在"),
    CODE_EXISTS(1002, "标识已存在"),
    ROLE_DOES_NOT_EXIST(1003, "角色不存在"),
    USER_NAME_OR_PASSWORD_ERROR(1004, "用户名或密码错误"),
    ACCOUNT_IS_DEACTIVATED(1005, "账户被停用"),
    OLDPASSWORD_ERROR(1006, "原密码错误"),//原:修改密码验证原密码异常报错
    ROLE_IS_DEACTIVATED(1008, "角色被停用"),
    ROLE_OCCUPIED(1007, "角色被占用"),//原:删除角色，有绑定用户时不允许删除报错,

    //系统设置
    NOTICE_DOES_NOT_EXIST(1009, "公告不存在"),
    IMAGE_DOES_NOT_EXIST(1010, "轮播图不存在"),
    SERVER_IS_TOO_BUSY(1012, "服务器繁忙,请稍后重试"),
    SMS_VERIFY_FAIL(1013, "手机号验证码错误"),
    SMS_CODE_NOT_EXIST(1014, "请先获取验证码信息"),
    PHONE_FORMAT_ERROR(1016, "手机号格式不正确"),
    PHONE_ERROR(1018, "用户与手机号不匹配"),
    VERIFY_CODE_TYPE_ERROR(1017, "没有此类型短信"),
    VERIFY_CODE_OVER_COUNT(1015, "今日获取验证码到达上限"),
    FILE_UPLOAD_ERROR(1011, "文件上传失败");



    private int code;
    private String msg;

    SystemResultEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
