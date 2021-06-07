package com.blockchain.server.otc.dto.userhandlelog;

import lombok.Data;

@Data
public class ListUserHandleLogResultDTO {
    private String id;
    private String userId;
    private String userName;
    private String realName;
    private String nickName;
    private String handleNumber;
    private String handleType;
    private String handleNumberType;
    private java.util.Date createTime;
}
