package com.blockchain.server.cct.dto.tradingRecord;

import lombok.Data;

/***
 * 成交记录列表查询参数
 */
@Data
public class ListRecordParamDTO {
    private String id;
    private String orderId; //订单id
    private String userName; //手机号
    private String nickName; //昵称
    private String coinName; //基本货币
    private String unitName; //二级货币
    private String beginTime; //开始时间
    private String endTime; //结束时间
}
