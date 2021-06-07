package com.blockchain.server.otc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OrderHandleLog 数据传输类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Table(name = "otc_order_handle_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHandleLog extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "sys_user_id")
    private String sysUserId;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "before_status")
    private String beforeStatus;
    @Column(name = "after_status")
    private String afterStatus;
    @Column(name = "create_time")
    private java.util.Date createTime;

}