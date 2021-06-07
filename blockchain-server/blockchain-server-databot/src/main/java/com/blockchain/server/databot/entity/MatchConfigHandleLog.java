package com.blockchain.server.databot.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * MatchConfigHandleLog 数据传输类
 *
 * @version 1.0
 * @date 2019-06-25 13:52:32
 */
@Table(name = "bot_match_config_handle_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchConfigHandleLog extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "sys_user_id")
    private String sysUserId;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "before_user_id")
    private String beforeUserId;
    @Column(name = "after_user_id")
    private String afterUserId;
    @Column(name = "before_coin_name")
    private String beforeCoinName;
    @Column(name = "after_coin_name")
    private String afterCoinName;
    @Column(name = "before_unit_name")
    private String beforeUnitName;
    @Column(name = "after_unit_name")
    private String afterUnitName;
    @Column(name = "before_min_price")
    private BigDecimal beforeMinPrice;
    @Column(name = "after_min_price")
    private BigDecimal afterMinPrice;
    @Column(name = "before_max_price")
    private BigDecimal beforeMaxPrice;
    @Column(name = "after_max_price")
    private BigDecimal afterMaxPrice;
    @Column(name = "before_min_percent")
    private BigDecimal beforeMinPercent;
    @Column(name = "after_min_percent")
    private BigDecimal afterMinPercent;
    @Column(name = "before_max_percent")
    private BigDecimal beforeMaxPercent;
    @Column(name = "after_max_percent")
    private BigDecimal afterMaxPercent;
    @Column(name = "before_price_type")
    private String beforePriceType;
    @Column(name = "after_price_type")
    private String afterPriceType;
    @Column(name = "before_status")
    private String beforeStatus;
    @Column(name = "after_status")
    private String afterStatus;
    @Column(name = "handle_type")
    private String handleType;
    @Column(name = "create_time")
    private java.util.Date createTime;

}