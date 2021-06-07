package com.blockchain.server.eth.common.enums;

public enum EthWalletEnums {
    SUCCESS(200, "请求成功", "Request success", ""),
    NO_LOGIN(201, "未登录", "No login", ""),
    NOT_PERFECT(500, "接口未完善", "Not perfect", ""),
    NOT_WALLETPASSWORD(800, "你还没有设置资金密码", "You have not set the fund password", "妳還沒有設置資金密碼"),
    // 关于为空的提示
    NULL_WALLETS(12500, "没有找到钱包信息", "No wallet information found", "沒有找到錢包資訊"),
    NULL_ERROR(12500, "没有接收到参数", "No arguments were received", "沒有接收到參數"),
    NULL_USEROPENID(12500, "用户标识不能为空", "The user id cannot be empty", "用戶標識不能為空"),
    NULL_ADDR(12500, "钱包地址不能为空", "The wallet address cannot be empty", "錢包地址不能為空"),
    NULL_TOKENADDR(12500, "货币地址不能为空", "The currency address cannot be empty", "貨幣地址不能為空"),
    NULL_TOKENALL(12500, "统计结果未找到", "The statistical results were not found", "統計結果未找到"),
    NULL_TOTALID(12500, "记录标识不能为空", "Record id cannot be empty", "記錄標識不能為空"),
    NULL_WALLETTYPE(12500, "钱包类型不能为空", "The wallet type cannot be empty", "錢包類型不能為空"),
    NULL_PASSWORD(12500, "钱包密码不能为空", "The wallet password cannot be empty", "錢包密碼不能為空"),
    NULL_AMOUNT(12500, "转账金额不能为空", "The transfer amount cannot be empty", "轉賬金額不能為空"),
    NULL_TXIN(12500, "充值记录不能为空", "The recharge record cannot be empty", "充值記錄不能為空"),
    NULL_OUT_TOADDR(12500, "提现的钱包地址不能为空", "The withdrawal address cannot be empty", "提現地址不能為空"),
    NULL_FREEBLANCE(12500, "可用余额不能为空", "The available balance cannot be empty", "可用余額不能為空"),
    NULL_FREEZEBLANCE(12500, "冻结余额不能为空", "The frozen balance cannot be empty", "凍結余額不能為空"),
    NULL_TXID(12500, "操作失败,记录标识不能为空", "The record id cannot be empty", "操作失敗,記錄標識不能為空"),
    NULL_TX(12500, "操作失败，该记录不存在", "The record does not exist", "操作失敗，該記錄不存在"),
    // 关于查询不到的提示
    INEXISTENCE_TOKENADDR(12600, "该币种暂不支持此操作", "This operation is not currently supported in this currency",
            "該幣種暫不支持此操作"),
    IEXIST_TOKENADDRS(12600, "该币种存在多个", "There are multiple versions of this currency",
            "該幣種存在多個"),
    INEXISTENCE_WALLET(12600, "钱包信息未找到", "Wallet information not found", "錢包信息未找到"),
    INEXISTENCE_WALLETTYPE(12600, "该钱包类型不存在", "The wallet type does not exist", "該錢包類型不存在"),
    INEXISTENCE_TX(12500, "该记录未找到", "The record was not found", "該記錄未找到"),
    INEXISTENCE_BLOCKTX(12500,"该币种还没存在发币账户","There is no issuing account in this currency","該幣種還沒存在發幣賬戶"),

    // 关于校验错误的提示
    DATA_EXCEPTION_ERROR(12700, "操作失败，数据异常", "Operation failed, data abnormal", "操作失败，数据异常"),
    OUT_TOADDR_ERROR(12700, "操作失败，提现钱包的钱包地址格式有误", "Operation failed, the wallet address format of the withdrawal " +
            "wallet is wrong", "操作失敗，提現錢包的錢包地址格式有誤"),
    PASSWORD_ERROR(12700, "操作失败，密码错误", "Operation failed, password error", "操作失敗，密碼錯誤"),
    INITWALLERT_ERROR(12700, "操作失败，钱包不能重复初始化", "Operation failed. Wallet cannot be initialized again",
            "操作失敗，錢包不能重復初始化"),
    EQWALLERTTYPE_ERROR(12700, "操作失败，不支持同钱包类型划转", "Operation failed, do not support the same wallet type transfer",
            "操作失敗，不支持同錢包類型劃轉"),
    NUMBER_TYPE_ERROR(12700, "操作失败，转账金额格式有误", "Operation failed, the transfer amount format is wrong", "操作失敗，轉賬金額格式有誤"),
    NUMBER_COUNT_ERROR(12700, "操作失败，转账金额必须大于0", "Operation failed, transfer amount must be greater than 0",
            "操作失敗，轉賬金額必須大於0"),
    NUMBER_GASAMOUNT_ERROR(12700, "操作失败，ETH手续费不足", "Operation failed, ETH commission is insufficient",
            "操作失敗，ETH手續費不足"),
    NUMBER_INSUFFICIENT_ERROR(12700, "操作失败，该数字货币可用余额不足", "Operation failed, the available balance of the digital " +
            "currency is insufficient", "操作失敗，該數字貨幣可用余額不足"),
    NUMBER_INSUFFICIENTZE_ERROR(12700, "操作失败，该数字货币可用余额不足", "Operation failed, the digital currency freeze balance is " +
            "insufficient", "操作失敗，該數字貨幣凍結余額不足"),
    NUMBER_INSUFFICIENT_GAS_ERROR(12700, "操作失败，提现手续费不足", "Operation failure, insufficient withdrawal charge",
            "操作失敗，提現手續費不足"),
    NUMBER_MINWDAMOUNT_ERROR(12700, "提现余额不得小于最小提现额度", "The withdrawal balance shall not be less than the minimum withdrawal limit",
            "提現余額不得小於最小提現額度"),
    // 数据的增删改报错的提示
    INSERT_INITWALLERT(12800, "钱包初始化失败，请稍后重试", "Wallet initialization failed. Please try again later", "錢包初始化失敗，請稍後重試"),
    INSERT_ADDWALLERT(12800, "该币种钱包已经创建", "The currency wallet has been created", "該幣種錢包已經創建"),

    NULL_GASWALLETNULL(12800,"操作失败，没有打油费的钱包账户","Operation failed. No wallet account for gas charge","操作失敗，沒有打油費的錢包賬戶"),
    NULL_ADDGASETH(12800,"操作失败，ETH余额充足，不需要打油费","Operation failure, ETH sufficient balance, no fuel charge","操作失敗，ETH余額充足，不需要打油費"),

    // 关于连接失败，服务器繁忙的提示
    SERVER_IS_TOO_BUSY(15000, "服务器繁忙,请稍后重试", "The server is busy, please try again later", "服務器繁忙,請稍後重試"),
    NUMBER_BLOCKED_ERROR(15001, "操作失败，输入金额必须大于0", "Operation failed, input number must be greater than 0",
            "操作失敗，输入金額必須大於0"),
    NUMBER_UNBLOCK_INSUFFICIENTZE_ERROR(15002, "操作失败，此前没有冻结过该账号余额，无法解冻", "The account balance has not been frozen before", "操作失敗，此前沒有凍結過該賬號余額，無法解凍");
    private int code;
    private String hkmsg;
    private String enMsg;
    private String cnmsg;

    EthWalletEnums(int code, String cnmsg, String enMsg, String hkmsg) {
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
