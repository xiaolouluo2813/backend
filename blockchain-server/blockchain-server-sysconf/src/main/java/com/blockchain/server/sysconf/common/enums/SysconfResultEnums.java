package com.blockchain.server.sysconf.common.enums;

public enum SysconfResultEnums {

    NOTICE_DOES_NOT_EXIST(1009, "公告不存在"),
    IMAGE_DOES_NOT_EXIST(1010, "轮播图不存在"),
    SYSTEM_ERROR(1012, "系统内部错误"),
    FILE_UPLOAD_ERROR(1011, "文件上传失败");



    private int code;
    private String msg;

    SysconfResultEnums(int code, String msg) {
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
