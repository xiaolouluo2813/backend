package com.blockchain.server.eth.web3j;

import com.blockchain.server.eth.dto.web3j.Web3jWalletDTO;
import org.web3j.protocol.core.methods.request.Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 关于以太坊钱包操作的工具类
 */
public interface IWalletWeb3j extends IBaseWeb3j {

    /**
     * 根据用户输入的托管钱包密码，创建“以太坊托管钱包”
     *
     * @param password 托管钱包密码
     * @return 数据对象
     */
    Web3jWalletDTO creationEthWallet(String password);

    /**
     * 根据用户的私钥，修改托管钱包密码
     *
     * @param privateKey  私钥
     * @param passwordNew 新密码
     * @return
     */
    Web3jWalletDTO updateEthWallet(String privateKey, String passwordNew);

    /**
     * 验证钱包密码
     *
     * @param keystore
     * @param password
     * @return
     */
    void isPassword(String keystore, String password);


    /**
     * 查询ETH余额
     *
     * @param address 钱包地址
     * @return 余额
     */
    BigInteger getEthBalance(String address);

    /**
     * 查询代币余额
     *
     * @param addr      钱包地址
     * @param tokenAddr 代币地址
     * @return 余额
     */
    BigInteger getTokenBalance(String addr, String tokenAddr);

    /**
     * 查询代币小数位
     *
     * @return 小数位
     */
    int getEthDecimal();

    /**
     * 查询代币小数位
     *
     * @return 小数位
     */
    int getTokenDecimal(String tokenAddr);

    /**
     * ETH 转账
     *
     * @param fromAddr       支付地址
     * @param fromPrivateKey 支付地址私钥
     * @param toAddr         接收地址
     * @param tBalance       转账的余额
     * @param gasPrice       燃烧的费用
     * @return hash
     */
    String ethWalletTransfer(String fromAddr, String fromPrivateKey, String toAddr, BigInteger tBalance, BigInteger gasPrice);

    /**
     * ETH 转账
     *
     * @param fromAddr       支付地址
     * @param fromPrivateKey 支付地址私钥
     * @param toAddr         接收地址
     * @param tBalance       转账的余额
     * @return hash
     */
    String ethWalletTransfer(String fromAddr, String fromPrivateKey, String toAddr, BigInteger tBalance);
    
    /**
     * 
     * @param fromAddr			支付地址
     * @param fromPrivateKey	支付地址私钥
     * @param toAddr			接收地址
     * @param tBalance			转账的金额
     * @return hash
     */
    String ethWalletTransfer(String fromAddr, String fromPrivateKey, String toAddr, BigDecimal tBalance);
    /**
     * 代币转账
     *
     * @param fromAddr       支付地址
     * @param tokenAddr      代币地址
     * @param fromPrivateKey 支付地址私钥
     * @param toAddr         接收地址
     * @param tBalance       转账代币余额
     * @param gasPrice       旷工费用
     * @return hash
     */
    String ethWalletTokenTransfer(String fromAddr, String tokenAddr, String fromPrivateKey, String toAddr, BigInteger tBalance, BigInteger gasPrice);

    /**
     * 代币转账
     *
     * @param fromAddr       支付地址
     * @param tokenAddr      代币地址
     * @param fromPrivateKey 支付地址私钥
     * @param toAddr         接收地址
     * @param tBalance       转账代币余额
     * @return hash
     */
    String ethWalletTokenTransfer(String fromAddr, String tokenAddr, String fromPrivateKey, String toAddr, BigInteger tBalance);

    /**
     * 创建交易
     * @param fromAddr
     * @param toAddr
     * @param tokenAddr
     * @param amount
     * @return
     */
    Transaction creatTx(String fromAddr,String toAddr,String tokenAddr,BigInteger amount);
    /**
     * 矿工费估算
     * @param fromAddr
     * @param toAddr
     * @param tokenAddr
     * @param amount
     * @return
     */
    BigInteger estimateGas(String fromAddr, String toAddr, String tokenAddr, BigInteger amount);

    /**
     * 矿工费估算
     * @param fromAddr
     * @param toAddr
     * @param tokenAddr
     * @param amount
     * @param gasPrice
     * @param gasLimit
     * @return
     */
    BigInteger estimateGas(String fromAddr, String toAddr, String tokenAddr, BigInteger amount,BigInteger gasPrice,BigInteger gasLimit);

}
