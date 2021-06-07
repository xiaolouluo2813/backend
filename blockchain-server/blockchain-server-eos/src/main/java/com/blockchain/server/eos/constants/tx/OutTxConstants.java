package com.blockchain.server.eos.constants.tx;

public class OutTxConstants {
    public final static int CHECK = 2; // 待初审提币
    public final static int RECHECK = 3; // 待复审提币
    //    public final static int ?PACK = 4; // 待出币
    public final static int PACK = 5; // 已出币（打包中）
    public final static int ERROR = 0; // 出币失败
    public final static int SUCCESS = 1; // 出币成功
    public final static int REJECT = 7; // 审核不通过
}
