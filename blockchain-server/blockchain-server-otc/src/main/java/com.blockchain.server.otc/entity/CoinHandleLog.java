package com.blockchain.server.otc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * CoinHandleLog 数据传输类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Table(name = "otc_coin_handle_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinHandleLog extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "coin_id")
    private String coinId;
    @Column(name = "sys_user_id")
    private String sysUserId;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "before_coin_name")
    private String beforeCoinName;
    @Column(name = "after_coin_name")
    private String afterCoinName;
    @Column(name = "before_unit_name")
    private String beforeUnitName;
    @Column(name = "after_unit_name")
    private String afterUnitName;
    @Column(name = "before_coin_net")
    private String beforeCoinNet;
    @Column(name = "after_coin_net")
    private String afterCoinNet;
    @Column(name = "before_coin_decimal")
    private Integer beforeCoinDecimal;
    @Column(name = "after_coin_decimal")
    private Integer afterCoinDecimal;
    @Column(name = "before_unit_decimal")
    private Integer beforeUnitDecimal;
    @Column(name = "after_unit_decimal")
    private Integer afterUnitDecimal;
    @Column(name = "before_coin_service_charge")
    private BigDecimal beforeCoinServiceCharge;
    @Column(name = "after_coin_service_charge")
    private BigDecimal afterCoinServiceCharge;
    @Column(name = "before_status")
    private String beforeStatus;
    @Column(name = "after_status")
    private String afterStatus;
    @Column(name = "create_time")
    private java.util.Date createTime;

}