package com.blockchain.server.system.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author: Liusd
 * @create: 2019-03-05 17:05
 **/
@Data
public class SystemMenuAddDto {
    /**
     * 名字
     */
    private String name;
    /**
     * 标识
     */
    private String code;
    /**
     * 资源地址
     */
    private String url;
    /**
     * 父id
     */
    private String pid;
    /**
     * 类型（M目录 C菜单 F按钮）
     */
    private String type;
    /**
     * 目录图标
     */
    private String icon;
    /**
     * 序号
     */
    private Integer ranking;
}
