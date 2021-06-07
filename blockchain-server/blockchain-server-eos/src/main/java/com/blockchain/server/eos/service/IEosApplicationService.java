package com.blockchain.server.eos.service;

import com.blockchain.server.eos.entity.Application;

import java.util.List;

/**
 * 以太坊钱包应用提现表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEosApplicationService {
    /**
     * 验证是否有该应用体系的钱包
     *
     * @param appId 应用标识
     */
    void CheckWalletType(String appId);

    /**
     * 查询所有钱包应用类型
     *
     * @return
     */
    List<Application> selectAll();

}
