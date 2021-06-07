package com.blockchain.server.cct.dto.publishOrder;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/***
 * 委托订单列表返回参数
 */
@Data
public class ListOrderResultDTO {
    private String id;
    private String userId;
    private String userName; //账户、手机
    private String nickName; //昵称
    private String realName; //真实姓名
    private String coinName; //基本货币
    private String unitName; //二级货币
    private String orderStatus; //订单状态
    private String publishType; //发布类型
    private String orderType; //订单类型
    private BigDecimal unitPrice; //单价
    private BigDecimal totalNum; //委托数量
    private BigDecimal lastNum; //剩余数量
    private BigDecimal totalTurnover; //委托总额
    private BigDecimal lastTurnover; //剩余总额
    private Integer version; //版本
    private Date createTime; //创建时间
    private Date modifyTime; //修改时间
}
