package com.blockchain.server.aibot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author ljj
 * @create CREATE TABLE CREATE TABLE `pc_trading_strategy` (
 *			`id` varchar(36) NOT NULL,
 *			`coin` varchar(20) DEFAULT NULL COMMENT '币对',
 *			`api_key` varchar(255) DEFAULT NULL,
 *			`secret_key` varchar(255) DEFAULT NULL,
 *			`startup_funds` varchar(20) DEFAULT NULL COMMENT '启动资金',
 *			`grid_num` varchar(10) DEFAULT NULL COMMENT '网格数',
 *			`spreads` varchar(20) DEFAULT NULL COMMENT '价差比例',
 *			`creaet_time` datetime DEFAULT NULL COMMENT '创建时间',
 *			`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
 *			`state` char(1) DEFAULT NULL COMMENT '账户策略状态,可用(Y),禁用(N)',
 *			PRIMARY KEY (`id`)
 *			) ENGINE=InnoDB DEFAULT CHARSET=utf8
 */
@Table(name = "pc_trading_strategy")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradingStrategy {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "coin")
	private String coin;
	@Column(name = "api_key")
	private String apiKey;
	@Column(name = "secret_key")
	private String secretKey;
	@Column(name = "startup_funds")
	private String startupFunds;
	@Column(name = "grid_num")
	private String gridNum;
	@Column(name = "spreads")
	private String spreads;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "modify_time")
	private Date modifyTime;
	@Column(name = "state")
	private String state;
}
