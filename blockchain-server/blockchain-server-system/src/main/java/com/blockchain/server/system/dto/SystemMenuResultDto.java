package com.blockchain.server.system.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-05 17:05
 **/
@Data
public class SystemMenuResultDto {
    /**
     * id
     */
    private String id;
    /**
     * 名字
     */
    private String label;
    /**
     * 标识
     */
    private String code;
    /**
     * 资源地址
     */
    private String url;
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

    /**
     * 子菜单
     */
    private List<SystemMenuResultDto> children;
}
