package com.blockchain.server.eth.service;

import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.common.base.dto.wallet.WalletBaseDTO;
import com.blockchain.common.base.dto.wallet.WalletParamsDTO;
import com.blockchain.server.eth.dto.wallet.EthWalletDTO;
import com.blockchain.server.eth.entity.EthWallet;
import com.blockchain.server.eth.entity.EthWalletTransfer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthWalletService {


    /**
     * 查询所有用户的钱包信息
     *
     * @param paramsDTO
     * @return
     */
    List<WalletBaseDTO> select(WalletParamsDTO paramsDTO);

    /**
     * 查询所有用户的链上钱包信息
     *
     * @param paramsDTO
     * @return
     */
    List<WalletBaseDTO> selectBlock(WalletParamsDTO paramsDTO);


    /**
     * 查询用户的所有钱包
     *
     * @param userId 用户ID
     * @return
     */
    List<EthWallet> selectByUserId(String userId);

    /**
     * 查询用户某个钱包
     *
     * @param userId     用户ID
     * @param tokenAddr  代币地址
     * @param walletType 钱包类型
     * @return
     */
    EthWalletDTO findByUserIdAndTokenAddrAndWalletType(String userId, String tokenAddr, String walletType);

    /**
     * 查询用户某个钱包
     *
     * @param addr     钱包地址
     * @param coinName 币种名称
     * @return
     */
    EthWallet findByAddrAndCoinName(String addr, String coinName);
    
    /**
     * 查询用户某个钱包
     *
     * @param addr     钱包地址
     * @param tokenAddr 代币地址
     * @return
     */
    EthWallet findByAddrAndTokenAddr(String addr, String tokenAddr);

    /**
     * 修改可用余额
     *
     * @param userId     用户ID
     * @param tokenAddr  代币地址
     * @param walletType 钱包类型
     * @param amount     修改金额
     */
    void updateFreeBalance(String userId, String tokenAddr, String walletType, BigDecimal amount, Date date);

    /**
     * 钱包冻结与可用余额转换
     *
     * @param orderDTO
     */
    void updateBlanceTransform(WalletOrderDTO orderDTO);

    /**
     * 钱包余额变动
     *
     * @param changeDTO
     */
    void updateBlance(WalletChangeDTO changeDTO);

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
     * 钱包余额变动
     *
     * @param addr         钱包地址
     * @param coinName     币种名称
     * @param freeBlance   可用余额
     * @param freezeBlance 冻结余额
     * @param date         时间
     */
    void updateBlance(String addr, String coinName, BigDecimal freeBlance, BigDecimal freezeBlance, Date date);

    /**
     * WEB3J转账
     *
     * @param fromAddr  支付地址
     * @param toAddr    收款地址
     * @param tokenName 代币名称
     */
    void web3jTx(String fromAddr, String toAddr, String tokenName);

    /**
     * 估算手续费
     *
     * @param fromAddr
     * @param toAddr
     * @param tokenName
     * @return
     */
    Map<String, BigDecimal> getGas(String fromAddr, String toAddr, String tokenName);

    /**
     * 打油费
     *
     * @param userId
     * @param walletType
     * @param coinName
     */
    void addGas(String userId, String walletType, String coinName,String amount);
}
