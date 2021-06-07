package com.blockchain.server.btc.service;

import com.blockchain.common.base.dto.wallet.WalletBaseDTO;
import com.blockchain.common.base.dto.wallet.WalletParamsDTO;
import com.blockchain.server.btc.dto.BtcWalletDTO;
import com.blockchain.server.btc.entity.BtcWallet;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IBtcWalletService {


    /**
     * 查询用户的所有钱包
     *
     * @param userId 用户ID
     * @return
     */
    List<BtcWallet> selectByUserId(String userId);

    /**
     * 查询所有用户的链上钱包信息
     *
     * @param paramsDTO
     * @return
     */
    List<WalletBaseDTO> selectBlock(WalletParamsDTO paramsDTO);


    /**
     * 查询用户某个钱包
     *
     * @param addr     地址
     * @param coinName 币种名称
     * @return
     */
    BtcWallet findByAddrAndCoinName(String addr, String coinName);

    /**
     * 查询用户某个钱包
     *
     * @param userId     用户ID
     * @param tokenAddr  代币地址
     * @param walletType 钱包类型
     * @return
     */
    BtcWalletDTO findByUserIdAndTokenAddrAndWalletType(String userId, String tokenAddr, String walletType);


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
     * 修改余额
     *
     * @param userId        用户ID
     * @param tokenAddr     代币地址
     * @param walletType    钱包类型
     * @param freeBalance   可用余额
     * @param freezeBalance 冻结余额
     * @param date
     */
    void updateBalance(String userId, String tokenAddr, String walletType, BigDecimal freeBalance, BigDecimal freezeBalance, Date date);

    /**
     * @Description: btc自己明细
     * @Param: [paramsDTO]
     * @return: java.util.List<com.blockchain.common.base.dto.wallet.WalletBaseDTO>
     * @Author: Liu.sd
     * @Date: 2019/3/23
     */
    List<WalletBaseDTO> select(WalletParamsDTO paramsDTO);

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
     * 查询BTC 节点钱包总余额
     *
     * @return
     */
    Map<String, String> totalBtc();

    /**
     * 获取油费信息
     *
     * @return
     */
    Map<String, String> getGasWallet();

    /**
     * rpc代币转账
     *
     * @param fromAddr 发送地址
     * @param toAddr   接受地址
     */
    void rpcTx(String fromAddr, String toAddr);

    /**
     * 回去BTC余额
     *
     * @param addr
     * @return
     */
    String getBtc(String addr);


    void goBtcTx(String toAddr);


    /**
     * 获取所有用户托管钱包地址
     *
     * @return
     */
    Set<String> getAllWalletAddr();

}
