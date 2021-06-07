package com.blockchain.server.cct.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CctPublishOrderLog 数据传输类
 *
 * @version 1.0
 * @date 2019-03-06 14:49:01
 */
@Table(name = "pc_cct_publish_order_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishOrderLog extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "sys_user_id")
    private String sysUserId;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "field")
    private String field;
    @Column(name = "before_value")
    private String beforeValue;
    @Column(name = "after_value")
    private String afterValue;
    @Column(name = "create_time")
    private java.util.Date createTime;

}