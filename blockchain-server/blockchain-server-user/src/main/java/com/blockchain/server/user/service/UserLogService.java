package com.blockchain.server.user.service;

/**
 * @author Harvey Luo
 * @date 2019/6/16 13:55
 */
public interface UserLogService {

    /**
     * 修改手机号码添加方法
     * @param type
     * @return
     */
    Integer insertLog(String userId, String type);
}
