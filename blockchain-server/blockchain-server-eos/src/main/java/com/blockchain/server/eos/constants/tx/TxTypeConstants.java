package com.blockchain.server.eos.constants.tx;

public class TxTypeConstants {
    public final static String OUT = "OUT";
    public final static String IN = "IN";
    public final static String CCT = "CCT";
    public final static String GAS = "GAS";
    public final static String TXMIN = "TXMIN"; // 内部转账（自身）
    public final static String TXOIN = "TXOIN"; // 内部转账（他人）
    public static final String FAST = "FAST"; //交易类型 转内快速转账

    public final static int ERROR = 0; // 失败
    public final static int SUCCESS = 1; // 成功
}
