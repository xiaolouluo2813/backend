package com.blockchain.server.eos.mapper;

import com.blockchain.common.base.dto.wallet.WalletBaseDTO;
import com.blockchain.common.base.dto.wallet.WalletParamsDTO;
import com.blockchain.server.eos.dto.WalletDTO;
import com.blockchain.server.eos.entity.Wallet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/18 16:50
 * @user WIN10
 */
@Repository
public interface WalletMapper extends Mapper<Wallet> {
    /**
     * 根据钱包地址、币种地址、钱包类型查询钱包信息
     *
     * @param id        钱包地址
     * @param tokenName 币种地址
     * @return
     */
    WalletDTO selectByAddrAndTokenName(@Param("id") Integer id,
                                       @Param("tokenName") String tokenName);

    /**
     * 根据钱包地址、币种地址、钱包类型查询钱包信息
     *
     * @param id         钱包地址
     * @param tokenName  币种地址
     * @param walletType 钱包类型
     * @return
     */
    WalletDTO selectByAddrAndTokenNameAndWalletType(@Param("id") String id,
                                                    @Param("tokenName") String tokenName,
                                                    @Param("walletType") String walletType);

    /**
     * 根据用户ID、币种地址、钱包类型查询钱包信息
     *
     * @param userOpenId 用户ID
     * @param tokenName  币种地址
     * @param walletType 钱包类型
     * @return
     */
    WalletDTO selectByUserOpenIdAndTokenNameAndWalletType(@Param("userOpenId") String userOpenId,
                                                          @Param("tokenName") String tokenName,
                                                          @Param("walletType") String walletType);

    /**
     * 根据钱包地址、钱包类型查询钱包信息
     *
     * @param id       钱包地址
     * @param walletType 钱包类型
     * @return
     */
    List<WalletDTO> selectByAddrAndWalletType(@Param("id") String id,
                                              @Param("walletType") String walletType);

    /**
     * 根据用户ID、钱包类型查询钱包信息
     *
     * @param userOpenId 用户ID
     * @param walletType 钱包类型
     * @return
     */
    List<WalletDTO> selectByUserOpenIdAndWalletType(@Param("userOpenId") String userOpenId,
                                                    @Param("walletType") String walletType);


    /**
     * 修改用户指定钱包的金额
     *
     * @param userOpenId    用户标识
     * @param tokenName     币种地址
     * @param walletType    应用钱包
     * @param balance       总金额
     * @param freeBalance   可用金额
     * @param freezeBalance 冻结金额
     * @return
     */
    int updateBalanceByUserIdInRowLock(@Param("userOpenId") String userOpenId,
                                       @Param("tokenName") String tokenName,
                                       @Param("walletType") String walletType,
                                       @Param("balance") BigDecimal balance,
                                       @Param("freeBalance") BigDecimal freeBalance,
                                       @Param("freezeBalance") BigDecimal freezeBalance,
                                       @Param("updateTime") Date updateTime);

    /**
     * 修改用户指定钱包的金额
     *
     * @param id            钱包地址
     * @param tokenName     币种地址
     * @param walletType    应用钱包
     * @param balance       总金额
     * @param freeBalance   可用金额
     * @param freezeBalance 冻结金额
     * @return
     */
    int updateBalanceByAddrInRowLock(@Param("id") String id,
                                     @Param("tokenName") String tokenName,
                                     @Param("walletType") String walletType,
                                     @Param("balance") BigDecimal balance,
                                     @Param("freeBalance") BigDecimal freeBalance,
                                     @Param("freezeBalance") BigDecimal freezeBalance,
                                     @Param("updateTime") Date updateTime);

    List<WalletBaseDTO> selectQuery(@Param("params") WalletParamsDTO paramsDTO);
}

