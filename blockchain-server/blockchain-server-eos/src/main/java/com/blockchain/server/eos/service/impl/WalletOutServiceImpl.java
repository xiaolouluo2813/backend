package com.blockchain.server.eos.service.impl;

import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.constants.tx.OutTxConstants;
import com.blockchain.server.eos.dto.WalletDTO;
import com.blockchain.server.eos.dto.WalletOutDTO;
import com.blockchain.server.eos.entity.Token;
import com.blockchain.server.eos.entity.WalletOut;
import com.blockchain.server.eos.entity.WalletTransfer;
import com.blockchain.server.eos.eos4j.Rpc;
import com.blockchain.server.eos.eos4j.api.exception.ApiException;
import com.blockchain.server.eos.eos4j.api.exception.Error;
import com.blockchain.server.eos.eos4j.api.exception.ErrorDetails;
import com.blockchain.server.eos.eos4j.api.vo.transaction.Transaction;
import com.blockchain.server.eos.mapper.WalletMapper;
import com.blockchain.server.eos.mapper.WalletOutMapper;
import com.blockchain.server.eos.service.IEosTokenService;
import com.blockchain.server.eos.service.IEosWalletTransferService;
import com.blockchain.server.eos.service.WalletOutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @author: Liusd
 * @create: 2019-03-26 17:40
 **/
@Service
public class WalletOutServiceImpl implements WalletOutService {

    @Autowired
    WalletOutMapper walletOutMapper;
    @Autowired
    IEosTokenService iEosTokenService;
    @Autowired
    WalletMapper walletMapper;
    @Autowired
    IEosWalletTransferService eosWalletTransferService;
    @Value("${rpc_url}")
    private String RPC_URL;
    @Value("${get_block_url}")
    private String GET_BLOCK_URL;

    private static final Logger LOG = LoggerFactory.getLogger(WalletOutServiceImpl.class);

    @Override
    public List<WalletOutDTO> list(String status) {
        return walletOutMapper.listByStatus(status);
    }

    @Override
    public int insert(String accountName, String tokenName, String privateKey, String remark) {
        WalletOut walletOut = new WalletOut();
        walletOut.setId(UUID.randomUUID().toString());
        walletOut.setAccountName(accountName);
        walletOut.setTokenName(tokenName);
        walletOut.setTokenSymbol(getTokenSymbol(tokenName));
        walletOut.setPrivateKey(privateKey);
        walletOut.setPassword("password");
        walletOut.setRemark(remark);
        walletOut.setStatus("1");
        return walletOutMapper.insert(walletOut);
    }

    @Override
    public Transaction blockTransfer(WalletTransfer tx) {
        Integer remark = Integer.valueOf(tx.getRemark());
        WalletDTO wallet = walletMapper.selectByAddrAndTokenName(remark, tx.getTokenName());
        Transaction transaction = null ;
        if (wallet == null) {
            WalletOut where = new WalletOut();
            where.setTokenSymbol(tx.getTokenSymbol());
            where.setTokenName(tx.getTokenName());
            List<WalletOut> list = walletOutMapper.select(where);
            if (list.size() <= 0) {
                throw new EosWalletException(EosWalletEnums.INEXISTENCE_BLOCKTX);
            }
            Rpc rpc = new Rpc(RPC_URL);
            for (WalletOut row : list) {
                try {
                    String amount = tx.getAmount().subtract(tx.getGasPrice()).setScale(4, BigDecimal.ROUND_HALF_UP).toString() + " " + tx.getTokenSymbol();
                    transaction = rpc.transfer(row.getPrivateKey(), tx.getTokenName(), row.getAccountName(), tx.getAccountName(), amount, String.valueOf(remark));
                    if (transaction == null) {
                        continue;
                    } else {
                        return transaction;
                    }
                } catch (ApiException e) {
                    System.out.println("========================" + e);
                    Error err = e.getError().getError();
                    if (err != null) {
                        String errStr = "EOS Transfer ERROR, code: " + err.getCode() + ", what:" + err.getWhat();
                        ErrorDetails[] errDetails = err.getDetails();
                        if (errDetails != null && errDetails.length > 0)
                            errStr += ", msg:" + errDetails[0].getMessage();
                        LOG.error(errStr);
                    }
                    continue;
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
                throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
        } else {
            //
            WalletDTO walletDTO = walletMapper.selectByAddrAndTokenName(tx.getFromId(), tx.getTokenName());
           /* int rowLock = walletMapper.updateBalanceByUserIdInRowLock(walletDTO.getUserOpenId(), walletDTO.getTokenName(), walletDTO.getWalletType(),
                    walletDTO.getFreezeBalance().negate(),  BigDecimal.ZERO,walletDTO.getFreezeBalance().negate(), walletDTO.getUpdateTime());*/
            int rowLock = walletMapper.updateBalanceByUserIdInRowLock(walletDTO.getUserOpenId(), walletDTO.getTokenName(), walletDTO.getWalletType(),
                    tx.getAmount().negate(),  BigDecimal.ZERO,tx.getAmount().negate(), walletDTO.getUpdateTime());
            if (rowLock == 0) {
                throw new EosWalletException(EosWalletEnums.INEXISTENCE_BLOCKTX);
            }
            int r = walletMapper.updateBalanceByAddrInRowLock(String.valueOf(wallet.getId()), wallet.getTokenName(), wallet.getWalletType(),
                    tx.getAmount().subtract(tx.getGasPrice()), tx.getAmount().subtract(tx.getGasPrice()), BigDecimal.ZERO, walletDTO.getUpdateTime());
            if (r == 0) {
                throw new EosWalletException(EosWalletEnums.INEXISTENCE_BLOCKTX);
            }
            eosWalletTransferService.updateStatus(tx.getId(), OutTxConstants.SUCCESS);
        }
        return transaction;
    }


    @Override
    public int update(String accountName, String tokenName, String remark, String status, String id) {
        WalletOut walletOut = walletOutMapper.selectByPrimaryKey(id);
        if (walletOut == null) {
            throw new EosWalletException(EosWalletEnums.INEXISTENCE_WALLET);
        }
        walletOut.setAccountName(accountName != null ? accountName : walletOut.getAccountName());
        walletOut.setTokenName(tokenName != null ? tokenName : walletOut.getTokenName());
        walletOut.setTokenSymbol(tokenName != null ? getTokenSymbol(tokenName) : walletOut.getTokenSymbol());
        walletOut.setRemark(remark != null ? remark : walletOut.getRemark());
        walletOut.setStatus(status != null ? status : walletOut.getStatus());
        return walletOutMapper.updateByPrimaryKey(walletOut);
    }

    @Override
    public int delete(String id) {
        WalletOut walletOut = walletOutMapper.selectByPrimaryKey(id);
        if (walletOut == null) {
            throw new EosWalletException(EosWalletEnums.INEXISTENCE_WALLET);
        }
        return walletOutMapper.delete(walletOut);
    }

    /**
     * @Description: 根据token_name获取tokenSymbol
     * @Param: [tokenName]
     * @return: java.lang.String
     * @Author: Liu.sd
     * @Date: 2019/3/26
     */
    private String getTokenSymbol(String tokenName) {
        Token token = iEosTokenService.findByTokenId(tokenName);
        if (token == null) {
            throw new EosWalletException(EosWalletEnums.INEXISTENCE_TOKENADDR);
        }
        return token.getTokenSymbol();
    }
}
