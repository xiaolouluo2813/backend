package com.blockchain.server.cct.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Commission 数据传输类
 *
 * @version 1.0
 * @date 2019-07-18 14:28:26
 */
@Table(name = "app_cct_commission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commission extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "pid")
    private String pid;
    @Column(name = "record_id")
    private String recordId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "status")
    private String status;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;

}