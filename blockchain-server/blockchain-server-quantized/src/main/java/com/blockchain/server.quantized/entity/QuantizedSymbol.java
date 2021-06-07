package com.blockchain.server.quantized.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author: Liusd
 * @create: 2019-04-19 16:47
 * CREATE TABLE `pc_quantized_symbols` (
 *   `id` varchar(36) NOT NULL,
 *   `symbol` varchar(20) DEFAULT NULL COMMENT '交易对',
 *   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 **/
@Table(name = "pc_quantized_symbols")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantizedSymbol {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "create_time")
    private Date createTime;
}
