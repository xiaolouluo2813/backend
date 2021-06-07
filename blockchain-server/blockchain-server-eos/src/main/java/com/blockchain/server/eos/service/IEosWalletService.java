package com.blockchain.server.eos.service;

import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.common.base.dto.wallet.WalletBaseDTO;
import com.blockchain.common.base.dto.wallet.WalletParamsDTO;
import com.blockchain.server.eos.dto.WalletDTO;
import com.blockchain.server.eos.entity.Wallet;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEosWalletService {


    /**
     * 查询用户的所有钱包
     *
     * @param userId 用户ID
     * @return
     */
    List<Wallet> selectByUserId(String userId);

    /**
     * 查询用户某个钱包
     *
     * @param userId      用户ID
     * @param tokenSymbol 代币名字
     * @param walletType  钱包类型
     * @return
     */
    WalletDTO findByUserIdAndTokenAddrAndWalletType(String userId, String tokenSymbol, String walletType);

    /**
     * 查询用户某个钱包
     *
     * @param id       钱包标识
     * @param coinName 币种名称
     * @return
     */
    Wallet findByAddrAndCoinName(int id, String coinName);


    /**
     * 修改可用余额
     *
     * @param userId     用户ID
     * @param tokenAddr  代币地址
     * @param walletType 钱包类型
     * @param amount     修改金额
     */
    void updateFreeBalance(String userId, String tokenAddr, String walletType, BigDecimal amount, Date date);

    List<WalletBaseDTO> select(WalletParamsDTO paramsDTO);

    /**
     * 钱包余额变动方法
     *
     * @param walletChangeDTO
     */
    Integer handleWalletChange(WalletChangeDTO walletChangeDTO);

    /**
     * 冻结与解冻方法
     *
     * @param walletOrderDTO
     * @return
     */
    Integer updateWalletOrder(WalletOrderDTO walletOrderDTO);

    /**
     * 钱包余额变动
     *
     * @param addr         钱包地址
     * @param coinName     币种名称
     * @param freeBlance   可用余额
     * @param freezeBlance 冻结余额
     * @param txType       记录类型
     */
    void updateBlance(String addr, String coinName, String freeBlance, String freezeBlance, String txType);

    /**
     * 修改钱包余额
     *
     * @param addr         钱包地址
     * @param coinName     币种名称
     * @param freeBlance   可用余额
     * @param freezeBlance 冻结余额
     */
    void updateBlance(String addr, String coinName, BigDecimal freeBlance, BigDecimal freezeBlance, Date date);


    boolean isId(String walletId);
}
