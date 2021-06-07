package com.blockchain.server.btc.mapper;

import com.blockchain.common.base.dto.wallet.WalletBaseDTO;
import com.blockchain.common.base.dto.wallet.WalletParamsDTO;
import com.blockchain.server.btc.dto.BtcWalletDTO;
import com.blockchain.server.btc.entity.BtcWallet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * BtcWalletMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Repository
public interface BtcWalletMapper extends Mapper<BtcWallet> {
    /**
     * 根据钱包地址、币种地址、钱包类型查询钱包信息
     *
     * @param addr      钱包地址
     * @param tokenId 币种地址
     * @return
     */
    BtcWalletDTO selectByAddrAndTokenAddr(@Param("addr") String addr,
                                          @Param("tokenId") String tokenId);

    /**
     * 根据钱包地址、币种地址、钱包类型查询钱包信息
     *
     * @param addr       钱包地址
     * @param tokenId  币种地址
     * @param walletType 钱包类型
     * @return
     */
    BtcWalletDTO selectByAddrAndTokenAddrAndWalletType(@Param("addr") String addr,
                                                       @Param("tokenId") String tokenId,
                                                       @Param("walletType") String walletType);

    /**
     * 根据用户ID、币种地址、钱包类型查询钱包信息
     *
     * @param userOpenId 用户ID
     * @param tokenId  币种地址
     * @param walletType 钱包类型
     * @return
     */
    BtcWalletDTO selectByUserOpenIdAndTokenAddrAndWalletType(@Param("userOpenId") String userOpenId,
                                                             @Param("tokenId") String tokenId,
                                                             @Param("walletType") String walletType);

    /**
     * 根据钱包地址、钱包类型查询钱包信息
     *
     * @param addr       钱包地址
     * @param walletType 钱包类型
     * @return
     */
    List<BtcWalletDTO> selectByAddrAndWalletType(@Param("addr") String addr,
                                                 @Param("walletType") String walletType);

    /**
     * 根据用户ID、钱包类型查询钱包信息
     *
     * @param userOpenId 用户ID
     * @param walletType 钱包类型
     * @return
     */
    List<BtcWalletDTO> selectByUserOpenIdAndWalletType(@Param("userOpenId") String userOpenId,
                                                       @Param("walletType") String walletType);


    /**
     * 修改用户指定钱包的金额
     *
     * @param userOpenId    用户标识
     * @param tokenId     币种地址
     * @param walletType    应用钱包
     * @param balance       总金额
     * @param freeBalance   可用金额
     * @param freezeBalance 冻结金额
     * @return
     */
    int updateBalanceByUserIdInRowLock(@Param("userOpenId") String userOpenId,
                                       @Param("tokenId") String tokenId,
                                       @Param("walletType") String walletType,
                                       @Param("balance") BigDecimal balance,
                                       @Param("freeBalance") BigDecimal freeBalance,
                                       @Param("freezeBalance") BigDecimal freezeBalance,
                                       @Param("updateTime") Date updateTime);

    /**
     * 修改用户指定钱包的金额
     *
     * @param addr          钱包地址
     * @param tokenId     币种地址
     * @param walletType    应用钱包
     * @param balance       总金额
     * @param freeBalance   可用金额
     * @param freezeBalance 冻结金额
     * @return
     */
    int updateBalanceByAddrInRowLock(@Param("addr") String addr,
                                     @Param("tokenId") String tokenId,
                                     @Param("walletType") String walletType,
                                     @Param("balance") BigDecimal balance,
                                     @Param("freeBalance") BigDecimal freeBalance,
                                     @Param("freezeBalance") BigDecimal freezeBalance,
                                     @Param("updateTime") Date updateTime);

    /** 
    * @Description: btc资金明细 
    * @Param: [paramsDTO] 
    * @return: java.util.List<com.blockchain.common.base.dto.wallet.WalletBaseDTO> 
    * @Author: Liu.sd 
    * @Date: 2019/3/23 
    */ 
    List<WalletBaseDTO> selectQuery(@Param("params") WalletParamsDTO paramsDTO);

    /**
     * 获取所有用户托管钱包地址
     *
     * @return
     */
    Set<String> getAllWalletAddr();
}