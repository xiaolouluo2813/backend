package com.blockchain.server.eth.service.impl;


import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.RSACoderUtils;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.common.util.RedisPrivateUtil;
import com.blockchain.server.eth.common.util.RedisWalletAddrUtil;
import com.blockchain.server.eth.dto.web3j.Web3jWalletDTO;
import com.blockchain.server.eth.entity.EthWalletKey;
import com.blockchain.server.eth.mapper.EthWalletKeyMapper;
import com.blockchain.server.eth.service.IEthWalletKeyService;
import com.blockchain.server.eth.web3j.IWalletWeb3j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class EthWalletKeyServiceImpl implements IEthWalletKeyService {

    @Autowired
    EthWalletKeyMapper ethWalletKeyMapper;
    @Autowired
    IWalletWeb3j walletWeb3j;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public EthWalletKey findByUserOpenId(String userOpenId) {
        EthWalletKey ethWalletKey = ethWalletKeyMapper.selectByPrimaryKey(userOpenId);
        return ethWalletKey;
    }

    @Override
    public EthWalletKey findByAddr(String addr) {
        EthWalletKey key = new EthWalletKey();
        key.setAddr(addr);
        EthWalletKey walletKey = ethWalletKeyMapper.selectOne(key);
        if (walletKey == null) {
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_WALLET);
        }
        walletKey.setPrivateKey(RSACoderUtils.decryptPassword(walletKey.getPrivateKey())); // 获取解密后的私钥;
        return walletKey;
    }

    @Override
    public boolean existsPass(String userOpenId) {
        EthWalletKey ethWalletKey = ethWalletKeyMapper.selectByPrimaryKey(userOpenId);
        String keystore = ethWalletKey.getKeystore();
        return keystore != null;
    }

    @Override
    public EthWalletKey selectOne(EthWalletKey ethWalletKey) {
        EthWalletKey _ethWalletKey = ethWalletKeyMapper.selectOne(ethWalletKey);
        return _ethWalletKey;
    }

    @Override
    @Transactional
    public int insert(EthWalletKey ethWalletKey) {
        RedisWalletAddrUtil.setWalletAddr(ethWalletKey.getAddr(), redisTemplate);
        return ethWalletKeyMapper.insertSelective(ethWalletKey);
    }

    @Override
    @Transactional
    public EthWalletKey insert(String userOpenId) {
        EthWalletKey initRow = findByUserOpenId(userOpenId); // 查询钱包是否初始化过
        // 创建以太坊钱包
        EthWalletKey ethWalletKey = new EthWalletKey();
        Date date = new Date();
        ethWalletKey.setUserOpenId(userOpenId);
        Web3jWalletDTO wallet = walletWeb3j.creationEthWallet("123456");
        ethWalletKey.setAddr(wallet.getAddr());
        ethWalletKey.setPrivateKey(RSACoderUtils.encryptPassword(wallet.getPrivateKey())); // 私钥加密
        ethWalletKey.setUpdateTime(date);
        ethWalletKey.setCreateTime(date);
        int row = ethWalletKeyMapper.insertSelective(ethWalletKey);
        if (row == 0) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        } else {
            RedisWalletAddrUtil.setWalletAddr(ethWalletKey.getAddr(), redisTemplate); // 存入缓存
            return ethWalletKey;
        }
    }

    @Override
    @Transactional
    public int update(EthWalletKey ethWalletKey) {
        return ethWalletKeyMapper.updateByPrimaryKey(ethWalletKey);
    }

    @Override
    public Set<String> selectAddrs() {
        Set<String> addrs = RedisWalletAddrUtil.getWalletAddrs(redisTemplate);
        if (addrs == null || addrs.size() == 0) {
            addrs = ethWalletKeyMapper.selectAllAddrs();
            RedisWalletAddrUtil.setWalletAddrs(addrs, redisTemplate);
        }
        return addrs;
    }

    @Override
    public String isPassword(String userOpenId, String password) {
        // 数据验证
        ExceptionPreconditionUtils.checkStringNotBlank(userOpenId,
                new EthWalletException(EthWalletEnums.NULL_USEROPENID));
        EthWalletKey ethWalletKey = ethWalletKeyMapper.selectByPrimaryKey(userOpenId);
        if (ethWalletKey == null) {
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_WALLET);
        }
        if (ethWalletKey.getKeystore() == null) {
            throw new EthWalletException(EthWalletEnums.NOT_WALLETPASSWORD);
        }
        String _pass = getPassword(userOpenId, password);
        walletWeb3j.isPassword(ethWalletKey.getKeystore(), _pass);
        return ethWalletKey.getAddr();
    }

    @Override
    @Transactional
    public void updatePassword(String userOpenId, String password) {
        // 数据验证
        ExceptionPreconditionUtils.checkStringNotBlank(userOpenId,
                new EthWalletException(EthWalletEnums.NULL_USEROPENID));
        EthWalletKey ethWalletKey = ethWalletKeyMapper.selectByPrimaryKey(userOpenId);
        if (ethWalletKey == null) {
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_WALLET);
        }
        // 重置密码
        String _pass = getPassword(userOpenId, password);
        String privateKey = RSACoderUtils.decryptPassword(ethWalletKey.getPrivateKey()); // 获取解密后的私钥
        Web3jWalletDTO walletDTO = walletWeb3j.updateEthWallet(privateKey, _pass);
        ethWalletKey.setKeystore(walletDTO.getKeystore());
        ethWalletKey.setUpdateTime(new Date());
        int row = ethWalletKeyMapper.updateByPrimaryKeySelective(ethWalletKey);
        if (row == 0) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    /**
     * 获取解密后的密码
     *
     * @param userOpenId 用户ID
     * @param password   加密后的密码
     * @return
     */
    private String getPassword(String userOpenId, String password) {
        ExceptionPreconditionUtils.checkStringNotBlank(password, new EthWalletException(EthWalletEnums.NULL_PASSWORD));
        String key = RedisPrivateUtil.getPrivateKey(userOpenId, redisTemplate);    // 获取私钥
        String _pass = RSACoderUtils.decryptPassword(password, key);    // 获取解密后的密码
        return _pass;
    }
}
