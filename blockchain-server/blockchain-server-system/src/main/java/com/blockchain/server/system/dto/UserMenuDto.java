package com.blockchain.server.system.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-11 17:49
 **/
@Data
public class UserMenuDto {
    /**
     * id
     */
    private String id;
    /**
     * 父id
     */
    private String pid;
    /**
     * 标识
     */
    private String code;
    /**
     * 名字
     */
    private String name;
    /**
     * 类型（M目录 C菜单 F按钮）
     */
    private String type;
    /**
     * 目录图标
     */
    private String icon;

}
