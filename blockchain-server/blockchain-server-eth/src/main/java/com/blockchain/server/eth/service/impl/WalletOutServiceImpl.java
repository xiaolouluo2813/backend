package com.blockchain.server.eth.service.impl;

import com.blockchain.server.eth.common.constants.wallet.WalletTypeConstants;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.common.util.DataCheckUtil;
import com.blockchain.server.eth.dto.wallet.EthWalletOutDto;
import com.blockchain.server.eth.entity.EthToken;
import com.blockchain.server.eth.entity.EthWalletOut;
import com.blockchain.server.eth.entity.EthWalletTransfer;
import com.blockchain.server.eth.mapper.EthWalletOutMapper;
import com.blockchain.server.eth.service.IEthTokenService;
import com.blockchain.server.eth.service.WalletOutService;
import com.blockchain.server.eth.web3j.IBaseWeb3j;
import com.blockchain.server.eth.web3j.IWalletWeb3j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

/**
 * @author: Liusd
 * @create: 2019-03-27 09:27
 **/
@Service
public class WalletOutServiceImpl implements WalletOutService {

    @Autowired
    EthWalletOutMapper ethWalletOutMapper;
    @Autowired
    IEthTokenService ethTokenService;
    @Autowired
    IWalletWeb3j walletWeb3j;

    @Override
    public List<EthWalletOutDto> list(String status) {
        return ethWalletOutMapper.listByTokenSymbol(status);
    }

    @Override
    public int insert(String addr, String tokenAddr, String privateKey, String remark) {
        EthWalletOut walletOut = new EthWalletOut();
        walletOut.setId(UUID.randomUUID().toString());
        walletOut.setAddr(addr);
        walletOut.setTokenAddr(tokenAddr);
        EthToken ethToken = ethTokenService.findByTokenAddr(tokenAddr); // 获取币种信息
        if (ethToken==null){
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_WALLETTYPE);
        }
        walletOut.setTokenSymbol(ethToken.getTokenSymbol());
        walletOut.setTokenDecimals(ethToken.getTokenDecimals());
        walletOut.setPrivateKey(privateKey);
        walletOut.setPassword("password");
        walletOut.setKeystore("keystore");
        walletOut.setRemark(remark);
        return ethWalletOutMapper.insert(walletOut);
    }

    @Override
    public int delete(String id) {
        EthWalletOut walletOut = ethWalletOutMapper.selectByPrimaryKey(id);
        if (walletOut==null){
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_TX);
        }
        return ethWalletOutMapper.delete(walletOut);
    }

    @Override
    public String blockTransfer(EthWalletTransfer tx) {
        EthWalletOut where = new EthWalletOut();
        where.setTokenSymbol(tx.getTokenSymbol());
        where.setTokenAddr(tx.getTokenAddr());
        List<EthWalletOut> list = ethWalletOutMapper.select(where);
        if (list.size() <= 0) {
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_BLOCKTX);
        }
        boolean isEth = WalletTypeConstants.ETH.equalsIgnoreCase(tx.getGasTokenSymbol());
        for (EthWalletOut row: list) {
            if(isEth){
            	BigDecimal amount = tx.getAmount().subtract(tx.getGasPrice()).multiply(BigDecimal.TEN.pow(row.getTokenDecimals()));
                String hash = walletWeb3j.ethWalletTransfer(row.getAddr(),row.getPrivateKey(),tx.getToAddr(), amount);
                if(hash == null) continue;
                return hash;
            }else{
                BigInteger amount = tx.getAmount().subtract(tx.getGasPrice()).multiply(BigDecimal.TEN.pow(row.getTokenDecimals())).toBigInteger();
                String hash = walletWeb3j.ethWalletTokenTransfer(row.getAddr(),row.getTokenAddr(),row.getPrivateKey(),tx.getToAddr(),amount);
                if(hash == null) continue;
                return hash;
            }
        }
        throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
    }

}