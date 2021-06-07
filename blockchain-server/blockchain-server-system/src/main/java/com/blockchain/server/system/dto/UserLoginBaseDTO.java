package com.blockchain.server.system.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-11 17:43
 **/
@Data
public class UserLoginBaseDTO {
    private String userId;
    private String account;
    private String username;
    private String token;
    private List<UserMenuDto> menuList;
}
