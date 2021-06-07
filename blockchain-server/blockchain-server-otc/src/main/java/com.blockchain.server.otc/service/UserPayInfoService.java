package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.userpayinfo.ListUserPayInfoResultDTO;

import java.util.List;

public interface UserPayInfoService {

    /***
     * 查询用户支付信息列表
     * @param userName
     * @param payType
     * @return
     */
    List<ListUserPayInfoResultDTO> listUserPayInfo(String userName, String payType);
}
