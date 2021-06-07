package com.blockchain.server.cct.dto.publishOrder;

import lombok.Data;

import java.math.BigDecimal;

/***
 * 委托订单列表查询参数
 */
@Data
public class ListOrderParamDTO {
    private String id; //id
    private String userId; //用户id
    private String userName; //手机号
    private String nickName; //昵称
    private String coinName; //基本货币
    private String unitName; //二级货币
    private BigDecimal unitPrice; //单价
    private String publishType; //发布类型
    private String orderType; //订单类型
    private String[] orderStatus; //订单状态
    private String beginTime; //开始时间
    private String endTime; //结束时间
}
