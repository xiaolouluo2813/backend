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
 * CREATE TABLE `pc_quantized_coin` (
 *   `id` varchar(36) NOT NULL,
 *   `type` char(1) DEFAULT NULL COMMENT '基本币0,交易币1',
 *   `name` varchar(10) DEFAULT NULL,
 *   `create_time` datetime DEFAULT NULL,
 *   PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 **/
@Table(name = "pc_quantized_coin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantizedCoin {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "type")
    private String type;
    @Column(name = "name")
    private String name;
    @Column(name = "create_time")
    private Date createTime;
}
