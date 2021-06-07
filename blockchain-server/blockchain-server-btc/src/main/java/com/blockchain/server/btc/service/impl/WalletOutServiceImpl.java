package com.blockchain.server.btc.service.impl;

import com.blockchain.server.btc.common.constants.OutTxConstants;
import com.blockchain.server.btc.common.constants.UsdtConstans;
import com.blockchain.server.btc.common.constants.WalletConstants;
import com.blockchain.server.btc.common.enums.BtcWalletEnums;
import com.blockchain.server.btc.common.exception.BtcWalletException;
import com.blockchain.server.btc.dto.BtcWalletDTO;
import com.blockchain.server.btc.dto.BtcWalletOutDTO;
import com.blockchain.server.btc.entity.BtcToken;
import com.blockchain.server.btc.entity.BtcWalletOut;
import com.blockchain.server.btc.entity.BtcWalletTransfer;
import com.blockchain.server.btc.mapper.BtcWalletMapper;
import com.blockchain.server.btc.mapper.BtcWalletOutMapper;
import com.blockchain.server.btc.mapper.BtcWalletTransferMapper;
import com.blockchain.server.btc.rpc.BtcUtils;
import com.blockchain.server.btc.rpc.UsdtUtils;
import com.blockchain.server.btc.service.IBtcTokenService;
import com.blockchain.server.btc.service.IBtcWalletTransferService;
import com.blockchain.server.btc.service.WalletOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author: Liusd
 * @create: 2019-03-27 10:13
 **/
@Service
public class WalletOutServiceImpl implements WalletOutService {

    @Autowired
    BtcWalletOutMapper btcWalletOutMapper;
    @Autowired
    IBtcTokenService iBtcTokenService;
    @Autowired
    UsdtUtils usdtUtils;
    @Autowired
    BtcUtils btcUtils;
    @Autowired
    BtcWalletMapper btcWalletMapper;
    @Autowired
    IBtcWalletTransferService btcWalletTransferService;
    @Autowired
    BtcWalletTransferMapper transferMapper;


    @Override
    public List<BtcWalletOutDTO> list(String tokenSymbol) {
        return btcWalletOutMapper.listByTokenSymbol(tokenSymbol);
    }

    @Override
    public int insert(Integer tokenId, String remark) {
        //从节点中生成钱包地址钱包
        BtcWalletOut walletOut = new BtcWalletOut();
        walletOut.setId(UUID.randomUUID().toString());
        String addr = null;
        try {
            addr = btcUtils.getNewAddress();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BtcWalletException(BtcWalletEnums.GET_NEW_ADDRESS_ERROR);
        }
        walletOut.setAddr(addr);
        walletOut.setTokenId(tokenId);
        walletOut.setTokenSymbol(getTokenSymbol(tokenId));
//        walletOut.setPrivateKey(privateKey);
        walletOut.setPassword("password");
        walletOut.setRemark(remark);
        return btcWalletOutMapper.insert(walletOut);
    }

    @Override
    public int delete(String id) {
        // TODO 移出节点钱包
        BtcWalletOut walletOut = btcWalletOutMapper.selectByPrimaryKey(id);
        if (walletOut == null) {
            throw new BtcWalletException(BtcWalletEnums.INEXISTENCE_TX);
        }
        return btcWalletOutMapper.delete(walletOut);
    }

    @Override
    public String blockTransfer(BtcWalletTransfer tx) {
        //查询站内所有地址
        Set<String> addrs = btcWalletMapper.getAllWalletAddr();
        String hash = null;
        if (!addrs.contains(tx.getToAddr())) {//站外转账
            BtcWalletOut where = new BtcWalletOut();
            where.setTokenId(tx.getTokenId());
            where.setTokenSymbol(tx.getTokenSymbol());
            List<BtcWalletOut> list = btcWalletOutMapper.select(where);
            if (list.size() <= 0) {
                throw new BtcWalletException(BtcWalletEnums.INEXISTENCE_BLOCKTX);
            }
            BigDecimal amount = BigDecimal.valueOf(tx.getAmount()).subtract(BigDecimal.valueOf(tx.getGasPrice()));
            boolean isUsdt = WalletConstants.USDT.equalsIgnoreCase(tx.getTokenSymbol());
            boolean isBtc = WalletConstants.BTC.equalsIgnoreCase(tx.getTokenSymbol());
            try {
                for (BtcWalletOut row : list) {
                    if (isUsdt) {
                        BigDecimal outWalletBal = usdtUtils.getBalance(row.getAddr());
                        if (outWalletBal.compareTo(amount) >= 0) {
                            hash = usdtUtils.send(row.getAddr(), tx.getToAddr(), amount.doubleValue());
                            return hash;
                        }
                    } else if (isBtc) {
                        BigDecimal outWalletBal = btcUtils.getBalanceByAddr(row.getAddr());
                        if (outWalletBal.compareTo(amount) >= 0) {
                            hash = btcUtils.sendWithRaw(row.getAddr(), tx.getToAddr(), amount.doubleValue(), row.getAddr());
                            return hash;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
            }
            throw new BtcWalletException(BtcWalletEnums.OUT_WALLET_BALENCE_INSUFFICIENT);
        } else {
            //站内转账
            //转账人
            BtcWalletDTO btcwalletDto = btcWalletMapper.selectByAddrAndTokenAddr(tx.getFromAddr(), String.valueOf(tx.getTokenId()));
            /*int rowLock = btcWalletMapper.updateBalanceByAddrInRowLock(btcwalletDto.getAddr(), String.valueOf(btcwalletDto.getTokenId()), btcwalletDto.getWalletType(),
                    btcwalletDto.getFreezeBalance().negate(), BigDecimal.ZERO, btcwalletDto.getFreezeBalance().negate(), btcwalletDto.getUpdateTime())*/;//减去余额
            int rowLock = btcWalletMapper.updateBalanceByAddrInRowLock(btcwalletDto.getAddr(), String.valueOf(btcwalletDto.getTokenId()), btcwalletDto.getWalletType(),
                   BigDecimal.valueOf(tx.getAmount()).negate(), BigDecimal.ZERO, BigDecimal.valueOf(tx.getAmount()).negate(), btcwalletDto.getUpdateTime());
            if (rowLock == 0){
                throw new BtcWalletException(BtcWalletEnums.INEXISTENCE_BLOCKTX);
            }
            //收账人
            BtcWalletDTO btcWallet = btcWalletMapper.selectByAddrAndTokenAddr(tx.getToAddr(), String.valueOf(tx.getTokenId()));
            /*int r = btcWalletMapper.updateBalanceByAddrInRowLock(btcWallet.getAddr(), String.valueOf(btcWallet.getTokenId()), btcWallet.getWalletType(),
                    btcwalletDto.getFreezeBalance().subtract(BigDecimal.valueOf(tx.getGasPrice())), btcwalletDto.getFreezeBalance().subtract(BigDecimal.valueOf(tx.getGasPrice())), BigDecimal.ZERO, btcWallet.getUpdateTime());*///增加余额
            int r = btcWalletMapper.updateBalanceByAddrInRowLock(btcWallet.getAddr(), String.valueOf(btcWallet.getTokenId()), btcWallet.getWalletType(),
                 BigDecimal.valueOf(tx.getAmount()).subtract(BigDecimal.valueOf(tx.getGasPrice())), BigDecimal.valueOf(tx.getAmount()).subtract(BigDecimal.valueOf(tx.getGasPrice())), BigDecimal.ZERO, btcWallet.getUpdateTime());
            if (r == 0){
                throw new BtcWalletException(BtcWalletEnums.INEXISTENCE_BLOCKTX);
            }
            tx.setUpdateTime(new Date());
            int row = transferMapper.updateByPrimaryKeySelective(tx);
            if (row == 0){
               throw new  BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
            }
            btcWalletTransferService.updateStatus(tx.getId(), OutTxConstants.SUCCESS);
        }
        return hash;

    }

    /**
     * @Description: 根据tokenId获取tokenSymbol
     * @Param: [tokenName]
     * @return: java.lang.String
     * @Author: Liu.sd
     * @Date: 2019/3/26
     */
    private String getTokenSymbol(Integer tokenId) {
        BtcToken btcToken = iBtcTokenService.findByTokenId(tokenId); // 获取币种信息
        if (btcToken == null) {
            throw new BtcWalletException(BtcWalletEnums.INEXISTENCE_TOKENADDR);
        }
        return btcToken.getTokenSymbol();
    }
}
