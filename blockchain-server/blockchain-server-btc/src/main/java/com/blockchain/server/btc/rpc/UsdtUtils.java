package com.blockchain.server.btc.rpc;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.server.btc.common.constants.UsdtConstans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

/**
 * @author hugq
 * @date 2019/2/16 15:54
 */
@Component
public class UsdtUtils {

    private Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private BtcOmniRpcClient client;

    /**
     * 创建并广播发送一个简单usdt交易
     *
     * @param fromAddress 发起地址
     * @param toAddress   接受地址
     * @param amount      数量
     * @return 返回结果为16进制字符串表示的交易哈希
     * @throws Exception
     */
    public String send(String fromAddress, String toAddress, Double amount) throws Exception {
        try {
            return client.getClient().invoke("omni_send", new Object[]{fromAddress, toAddress, UsdtConstans.USDT_PROPERTY_ID, amount.toString()}, String.class);
        } catch (Throwable e) {
            LOG.info("=== BtcOmniUtils.send(String fromAddress, String toAddress, double amount):{} ===", e.getMessage(), e);
            throw new Exception(e.getMessage() + String.format("[params]: fromAddress=%s,toAddress=%s,amount=%s", fromAddress, toAddress, amount));
        }
    }

    /**
     * 创建并广播发送一个简单usdt交易，指定支付手续费地址
     *
     * @param fromAddress 发起地址
     * @param toAddress   接受地址
     * @param amount      数量
     * @param feeaddress  支付手续费的地址
     * @return 返回结果为16进制字符串表示的交易哈希
     * @throws Exception
     */
    public String fundedSend(String fromAddress, String toAddress, String amount, String feeaddress) throws Exception {
        try {
            return client.getClient().invoke("omni_funded_send", new Object[]{fromAddress, toAddress, UsdtConstans.USDT_PROPERTY_ID, amount, feeaddress}, String.class);
        } catch (Throwable e) {
            LOG.info("=== BtcOmniUtils.fundedSend(String fromAddress, String toAddress, double amount, String feeaddress):{} ===", e.getMessage(), e);
            throw new Exception(e.getMessage() + String.format("[params]: fromAddress=%s,toAddress=%s,amount=%s,feeaddress=%s", fromAddress, toAddress, amount, feeaddress));
        }
    }

    /**
     * 调用创建并广播一个交易，将所有可用代币转入指定生态系统中的接收地址
     *
     * @param fromAddress 发起地址
     * @param toAddress   接受地址
     * @param ecosystem   生态系统编码，数值，1 - 主生态，2 - 测试生态
     * @param feeaddress  支付手续费的地址
     * @return 返回结果为16进制字符串表示的交易哈希
     * @throws Exception
     */
    public String fundedSendAll(String fromAddress, String toAddress, int ecosystem, String feeaddress) throws Exception {
        try {
            return client.getClient().invoke("omni_funded_sendall", new Object[]{fromAddress, toAddress, ecosystem, feeaddress}, String.class);
        } catch (Throwable e) {
            LOG.info("=== BtcOmniUtils.fundedSendAll(String fromAddress, String toAddress, int ecosystem, String feeaddress):{} ===", e.getMessage(), e);
            throw new Exception(e.getMessage() + String.format("[params]: fromAddress=%s,toAddress=%s,ecosystem=%s,feeaddress=%s", fromAddress, toAddress, ecosystem, feeaddress));
        }
    }

    /**
     * 获取指定地址usdt余额
     *
     * @param address 地址
     * @return
     * @throws Exception
     */
    public BigDecimal getBalance(String address) throws Exception {
        try {
            LinkedHashMap<String, String> balanceMap = client.getClient().invoke("omni_getbalance", new Object[]{address, UsdtConstans.USDT_PROPERTY_ID}, LinkedHashMap.class);
            return new BigDecimal(balanceMap.get("balance"));
        } catch (Throwable e) {
            LOG.info("=== BtcOmniUtils.getBalance(String address):{} ===", e.getMessage(), e);
            throw new Exception(e.getMessage() + String.format("[params]: address=%s", address));
        }
    }

    /**
     * 获取指定Omni交易的详细信息
     *
     * @param txId 交易哈希
     * @return
     * @throws Exception
     */
    public JSONObject getTransaction(String txId) throws Exception {
        try {
            return client.getClient().invoke("omni_gettransaction", new Object[]{txId}, JSONObject.class);
        } catch (Throwable e) {
//            LOG.info("=== BtcOmniUtils.getTransaction(String):{} ===", e.getMessage(), e);
            throw new Exception(e.getMessage() + String.format("[params]: txId=%s", txId));
        }
    }

}
