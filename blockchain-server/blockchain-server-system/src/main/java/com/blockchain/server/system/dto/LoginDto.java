package com.blockchain.server.system.dto;

import com.blockchain.server.system.entity.SystemUser;
import lombok.Data;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-06 16:25
 **/
@Data
public class LoginDto {
    /**
     * 用户
     */
    private SystemUser user;
    /**
     * 菜单集合
     */
    private List<UserMenuDto> menus;
}
