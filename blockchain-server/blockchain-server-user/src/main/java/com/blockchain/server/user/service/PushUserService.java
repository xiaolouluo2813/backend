package com.blockchain.server.user.service;

import com.blockchain.server.user.entity.PushUser;

public interface PushUserService {

    /***
     * 根据用户id查询
     * @param userId
     * @return
     */
    PushUser selectByUserId(String userId);

    /***
     * 根据客户端id查询
     * @param clientId
     * @return
     */
    PushUser selectByClientId(String clientId);
}
