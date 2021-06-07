package com.blockchain.server.cct.common.constant;

public class CCTConstant {

    //订单状态
    public static final String STATUS_NEW = "NEW"; //新建
    public static final String STATUS_MATCH = "MATCH"; //已撮合
    public static final String STATUS_FINISH = "FINISH"; //已完成
    public static final String STATUS_CANCEL = "CANCEL"; //撤销
	public static final String STATUS_CANCELING = "CANCELING"; //撤销中

    //公共配置状态
    public static final String STATUS_YES = "Y"; //可用
    public static final String STATUS_NO = "N"; //禁用

    //配置类型
    public static final String TYPE_COMMISSION_ISSUE_TIME = "commission_issue_time"; //佣金发放时间

    //订单状态
    public static final String TYPE_BUY = "BUY"; //买单
    public static final String TYPE_SELL = "SELL"; //卖单

    //发布状态
    public static final String TYPE_MARKET = "MARKET"; //市价交易
    public static final String TYPE_LIMIT = "LIMIT"; //限价交易

    //交易类型
    public static final String TYPE_MAKER = "MAKER"; //挂单
    public static final String TYPE_TAKER = "TAKER"; //吃单

    //操作状态
    public static final String TYPE_ADD = "ADD"; //新增
    public static final String TYPE_DELETE = "DELETE"; //删除
    public static final String TYPE_UPDATE = "UPDATE"; //更新
    public static final String TYPE_START = "START"; //启用
    public static final String TYPE_DISABLE = "DISABLE"; //禁用
}
