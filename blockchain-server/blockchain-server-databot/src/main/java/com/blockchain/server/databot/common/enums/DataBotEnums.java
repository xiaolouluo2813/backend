package com.blockchain.server.databot.common.enums;

public enum DataBotEnums {
    CURRENCY_PAIR_EXIST(9901, "已存在相同的币对配置！", "The same coin pair already exists!", "已存在相同的幣對配置！"),
    CURRENCY_PAIR_NULL(9902, "币对配置不存在！", "Currency pairs do not exist!", "幣對配置不存在！"),
    MATCH_CONFIG_NULL(9903, "撮合配置不存在！", "Matching configuration does not exist!", "撮合配置不存在！"),
    MATCH_CONFIG_EXIST(9904, "已存在相同币对的撮合配置！", "There is already the same coin matching configuration!", "已存在相同幣對的撮合配置！"),
    USER_NULL(9905, "操作失败，用户不存在！", "Operation failed, user does not exist!", "操作失敗，用戶不存在！"),
    ;
    private int code;
    private String cnmsg;
    private String enMsg;
    private String hkmsg;

    DataBotEnums(int code, String cnmsg, String enMsg, String hkmsg) {
        this.code = code;
        this.cnmsg = cnmsg;
        this.enMsg = enMsg;
        this.hkmsg = hkmsg;
    }

    public int getCode() {
        return code;
    }

    public String getCnmsg() {
        return cnmsg;
    }

    public String getEnMsg() {
        return enMsg;
    }

    public String getHkmsg() {
        return hkmsg;
    }
}
