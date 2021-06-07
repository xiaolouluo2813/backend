package com.blockchain.server.eth.service.impl;


import com.alibaba.druid.sql.visitor.functions.Bin;
import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.constant.WalletConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.common.base.dto.wallet.WalletBaseDTO;
import com.blockchain.common.base.dto.wallet.WalletParamsDTO;
import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.RSACoderUtils;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.eth.common.constants.tx.OutTxConstants;
import com.blockchain.server.eth.common.constants.tx.TxTypeConstants;
import com.blockchain.server.eth.common.constants.wallet.WalletTypeConstants;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.common.util.DataCheckUtil;
import com.blockchain.server.eth.common.util.RedisPrivateUtil;
import com.blockchain.server.eth.dto.tx.EthWalletTransferDTO;
import com.blockchain.server.eth.dto.tx.EthWalletTxBillDTO;
import com.blockchain.server.eth.dto.wallet.EthWalletBillDTO;
import com.blockchain.server.eth.dto.wallet.EthWalletDTO;
import com.blockchain.server.eth.entity.*;
import com.blockchain.server.eth.feign.UserFeign;
import com.blockchain.server.eth.mapper.EthWalletMapper;
import com.blockchain.server.eth.service.*;
import com.blockchain.server.eth.web3j.IWalletWeb3j;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class EthWalletServiceImpl implements IEthWalletService, ITxTransaction {
    @Autowired
    UserFeign userFeign;
    @Autowired
    EthWalletMapper ethWalletMapper;
    @Autowired
    IEthTokenService ethTokenService;
    @Autowired
    IEthWalletTransferService ethWalletTransferService;
    @Autowired
    IEthClearingTotalService ethClearingTotalService;
    @Autowired
    IEthWalletKeyService ethWalletKeyService;
    @Autowired
    IEthCollectionTransferService ethCollectionTransferService;
    @Autowired
    IEthGasWalletService ethGasWalletService;

    @Autowired
    IWalletWeb3j walletWeb3j;

    public static final String DEFAULT = "";
    public static final String ETH = "ETH";
    private static final BigInteger DEFAULT_GAS = new BigInteger("210000000000000");


    @Override
    public List<WalletBaseDTO> select(WalletParamsDTO paramsDTO) {
        if (paramsDTO != null && paramsDTO.getUserName() != null && !paramsDTO.getUserName().equals("")) {
            UserBaseInfoDTO userBaseInfoDTO = ConditionsUserId(paramsDTO.getUserName());
            //用户存在，添加条件
            if (userBaseInfoDTO != null) {
                paramsDTO.setUserId(userBaseInfoDTO.getUserId());
            } else {
                //用户不存在，返回空
                return null;
            }
        }
        List<WalletBaseDTO> wallets = ethWalletMapper.selectQuery(paramsDTO);
        return fillUserInfos(wallets);
    }

    @Override
    public List<WalletBaseDTO> selectBlock(WalletParamsDTO paramsDTO) {
        List<WalletBaseDTO> list = this.select(paramsDTO);
        boolean isEth = WalletTypeConstants.ETH.equalsIgnoreCase(paramsDTO.getCoinName());
        for (WalletBaseDTO row : list) {
            row.setFreezeBalance(null);
            row.setFreeBalance(null);
            row.setBalance(this.getBalance(row.getAddr(), row.getCoinType(), row.getCoinDecimals()));
        }
        return list;
    }

    /**
     * 获取用户链上余额
     *
     * @param addr     钱包地址
     * @param coinAddr 合约地址
     * @param decimal  小数位
     * @return
     */
    private String getBalance(String addr, String coinAddr, Integer decimal) {
        BigInteger amount = BigInteger.ZERO;
        try {
            if (ETH.equalsIgnoreCase(coinAddr)) {
                amount = walletWeb3j.getEthBalance(addr);
            } else {
                amount = walletWeb3j.getTokenBalance(addr, coinAddr);
            }
        } catch (Exception e) {
        }
        return DataCheckUtil.bitToBigDecimal(amount, decimal).toString();
    }

    @Override
    public List<EthWallet> selectByUserId(String userId) {
        ExceptionPreconditionUtils.checkNotNull(userId, new EthWalletException(EthWalletEnums.NULL_USEROPENID));
        EthWallet where = new EthWallet();
        where.setUserOpenId(userId);
        List<EthWallet> wallets = ethWalletMapper.select(where);
        if (wallets.size() <= 0) {
            throw new EthWalletException(EthWalletEnums.NULL_TOKENADDR);
        }
        return wallets;
    }

    @Override
    public EthWalletDTO findByUserIdAndTokenAddrAndWalletType(String userId, String tokenAddr, String walletType) {
        return ethWalletMapper.selectByUserOpenIdAndTokenAddrAndWalletType(userId, tokenAddr, walletType);
    }

    @Override
    public EthWallet findByAddrAndCoinName(String addr, String coinName) {
        EthWallet where = new EthWallet();
        where.setAddr(addr);
        where.setTokenSymbol(coinName);
        EthWallet ethWallet = ethWalletMapper.selectOne(where);
        if (null == ethWallet) {
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_WALLET);
        }
        return ethWallet;
    }
    
    @Override
    public EthWallet findByAddrAndTokenAddr(String addr, String tokenAddr) {
        EthWallet where = new EthWallet();
        where.setAddr(addr);
        where.setTokenAddr(tokenAddr);
        EthWallet ethWallet = ethWalletMapper.selectOne(where);
        if (null == ethWallet) {
            throw new EthWalletException(EthWalletEnums.INEXISTENCE_WALLET);
        }
        return ethWallet;
    }

    @Override
    @Transactional
    public void updateFreeBalance(String userId, String tokenAddr, String walletType, BigDecimal amount, Date date) {
        EthWalletDTO wallet = this.findByUserIdAndTokenAddrAndWalletType(userId, tokenAddr, walletType);
        int row = ethWalletMapper.updateBalanceByUserIdInRowLock(userId, tokenAddr, walletType, amount, amount,
                BigDecimal.ZERO, date);
        if (row == 0) {
            throw new EthWalletException(EthWalletEnums.NUMBER_INSUFFICIENT_ERROR);
        }
    }

    @Override
    @TxTransaction
    @Transactional
    public void updateBlanceTransform(WalletOrderDTO orderDTO) {
        ExceptionPreconditionUtils.checkNotNull(orderDTO, new EthWalletException(EthWalletEnums.NULL_ERROR));
        EthToken ethToken = ethTokenService.findByTokenName(orderDTO.getTokenName());
        ExceptionPreconditionUtils.checkNotNull(orderDTO.getFreeBalance(),
                new EthWalletException(EthWalletEnums.NULL_FREEBLANCE));
        ExceptionPreconditionUtils.checkNotNull(orderDTO.getFreezeBalance(),
                new EthWalletException(EthWalletEnums.NULL_FREEZEBLANCE));
        if (orderDTO.getFreeBalance().compareTo(orderDTO.getFreezeBalance().negate()) != 0) {
            throw new EthWalletException(EthWalletEnums.DATA_EXCEPTION_ERROR);
        }
        this.updateBalanceByUserOpenId(orderDTO.getUserId(), ethToken.getTokenAddr(), orderDTO.getWalletType(),
                orderDTO.getFreeBalance(), orderDTO.getFreezeBalance(), new Date());
    }

    @Override
    @TxTransaction
    @Transactional
    public void updateBlance(WalletChangeDTO changeDTO) {
        ExceptionPreconditionUtils.checkNotNull(changeDTO, new EthWalletException(EthWalletEnums.NULL_ERROR));
        EthToken ethToken = ethTokenService.findByTokenName(changeDTO.getTokenName());
        Date date = new Date();
        EthWalletDTO ethWalletDTO = this.updateBalanceByUserOpenId(changeDTO.getUserId(), ethToken.getTokenAddr(),
                changeDTO.getWalletType(), changeDTO.getFreeBalance(), changeDTO.getFreezeBalance(), date);
        BigDecimal count = changeDTO.getFreeBalance().add(changeDTO.getFreezeBalance());
        if (count.compareTo(BigDecimal.ZERO) >= 0) {
            ethWalletTransferService.insert(changeDTO.getRecordId(), DEFAULT, ethWalletDTO.getAddr(), count.abs(),
                    ethToken, changeDTO.getWalletType(), date);
        } else {
            ethWalletTransferService.insert(changeDTO.getRecordId(), ethWalletDTO.getAddr(), DEFAULT, count.abs(),
                    ethToken, changeDTO.getWalletType(), date);
        }
    }

    @Override
    @Transactional
    public void updateBlance(String addr, String coinName, String freeBlance, String freezeBlance, String txType) {
        Date date = new Date();
        // 验证余额数据格式
        BigDecimal _freeBlance = BigDecimal.ZERO;
        BigDecimal _freezeBlance = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        try {
            _freeBlance = new BigDecimal(freeBlance);
            _freezeBlance = new BigDecimal(freezeBlance);
            total = _freeBlance.add(_freezeBlance);
        } catch (Exception e) {
            throw new EthWalletException(EthWalletEnums.DATA_EXCEPTION_ERROR);
        }
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            throw new EthWalletException(EthWalletEnums.DATA_EXCEPTION_ERROR);
        }
        // 获取用户钱包
        EthWallet wallet = this.findByAddrAndCoinName(addr, coinName);
        EthToken token = ethTokenService.findByTokenName(coinName);
        this.updateBalanceByUserOpenId(wallet.getUserOpenId(), wallet.getTokenAddr(), wallet.getWalletType(),
                _freeBlance, _freezeBlance, date);
        // 插入加币减币的记录
        String userId = null == SecurityUtils.getUser() ? WalletConstant.BACKEND : SecurityUtils.getUser().getId();
        if (total.compareTo(BigDecimal.ZERO) >= 0) {
            ethWalletTransferService.insert(UUID.randomUUID().toString(), userId, wallet.getAddr(), total.abs(), token, txType, date);
        } else {
            ethWalletTransferService.insert(UUID.randomUUID().toString(), wallet.getAddr(), userId, total.abs(), token, txType, date);
        }
    }

    @Override
    @Transactional
    public void updateBlance(String addr, String coinName, BigDecimal freeBlance, BigDecimal freezeBlance, Date date) {
        // 获取用户钱包
        EthWallet wallet = this.findByAddrAndCoinName(addr, coinName);
        EthToken token = ethTokenService.findByTokenName(coinName);
        this.updateBalanceByUserOpenId(wallet.getUserOpenId(), wallet.getTokenAddr(), wallet.getWalletType(),
                freeBlance, freezeBlance, date);
    }

    @Override
    @Transactional
    public void web3jTx(String fromAddr, String toAddr, String tokenName) {
        ExceptionPreconditionUtils.checkStringNotBlank(toAddr, new EthWalletException(EthWalletEnums.NULL_ADDR));
        Date endDate = new Date();
        // 验证地址是否存在
        EthWalletKey walletKey = ethWalletKeyService.findByAddr(fromAddr);
        // 请求代币信息
        EthToken ethToken = ethTokenService.findByTokenName(tokenName);
        // 请求钱包ETH,代币余额与手续费估算
        BigInteger ethAmount = walletWeb3j.getEthBalance(fromAddr);
        BigInteger tokenAmount = BigInteger.ZERO;
        BigInteger gasAmount = BigInteger.ZERO;
        boolean isEth = WalletTypeConstants.ETH.equalsIgnoreCase(ethToken.getTokenSymbol());
        if (!isEth) {
            tokenAmount = walletWeb3j.getTokenBalance(fromAddr, ethToken.getTokenAddr());
            gasAmount = walletWeb3j.estimateGas(fromAddr, toAddr, ethToken.getTokenAddr(), tokenAmount);
        } else {
            gasAmount = walletWeb3j.estimateGas(fromAddr, toAddr, ethToken.getTokenAddr(), ethAmount);
        }
        // 以太坊转账
        String hash;
        EthCollectionTransfer tx = new EthCollectionTransfer();
        if (isEth) {
            BigInteger amount = ethAmount.subtract(gasAmount);
            hash = walletWeb3j.ethWalletTransfer(fromAddr, walletKey.getPrivateKey(), toAddr, amount);
            tx.setAmount(DataCheckUtil.bitToBigDecimal(amount));
        } else {
            tokenAmount = walletWeb3j.getTokenBalance(fromAddr, ethToken.getTokenAddr());
            hash = walletWeb3j.ethWalletTokenTransfer(fromAddr, ethToken.getTokenAddr(), walletKey.getPrivateKey(), toAddr, tokenAmount);
            tx.setAmount(DataCheckUtil.bitToBigDecimal(tokenAmount));
        }
        if (hash == null) {
            if (ethAmount.compareTo(gasAmount) < 0) {
                throw new EthWalletException(EthWalletEnums.NUMBER_GASAMOUNT_ERROR);
            }
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        } else {
            SessionUserDTO userDTO = SecurityUtils.getUser();
            tx.setCreateTime(endDate);
            tx.setUpdateTime(endDate);
            tx.setFromAddr(fromAddr);
            tx.setToAddr(toAddr);
            tx.setTokenAddr(ethToken.getTokenAddr());
            tx.setUserId(userDTO == null ? "BAM" : userDTO.getId());
            tx.setTokenSymbol(ethToken.getTokenSymbol());
            tx.setGasPrice(DataCheckUtil.bitToBigDecimal(gasAmount));
            tx.setHash(hash);
            tx.setId(UUID.randomUUID().toString());
            tx.setStatus(OutTxConstants.PACK);
            ethCollectionTransferService.insert(tx);
        }


    }

    @Override
    public Map<String, BigDecimal> getGas(String fromAddr, String toAddr, String tokenName) {
        // 验证地址是否存在
        EthWalletKey walletKey = ethWalletKeyService.findByAddr(fromAddr);
        // 请求代币信息
        EthToken ethToken = ethTokenService.findByTokenName(tokenName);
        // 请求钱包ETH,代币余额与手续费估算
        BigInteger ethAmount = walletWeb3j.getEthBalance(fromAddr);
        BigInteger tokenAmount = BigInteger.ZERO;
        BigInteger gasAmount = BigInteger.ZERO;
        boolean isEth = WalletTypeConstants.ETH.equalsIgnoreCase(ethToken.getTokenSymbol());
        if (!isEth) {
            tokenAmount = walletWeb3j.getTokenBalance(fromAddr, ethToken.getTokenAddr());
            gasAmount = walletWeb3j.estimateGas(fromAddr, toAddr, ethToken.getTokenAddr(), tokenAmount);
        } else {
            gasAmount = walletWeb3j.estimateGas(fromAddr, toAddr, ethToken.getTokenAddr(), ethAmount);
        }
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("gas", DataCheckUtil.bitToBigDecimal(gasAmount));
        map.put("eth", DataCheckUtil.bitToBigDecimal(ethAmount));
        return map;
    }

    @Override
    public void addGas(String userId, String walletType, String coinName, String amount) {
        // 用户信息查询
        EthToken token = ethTokenService.findByTokenName(coinName);
        EthWalletDTO walletDTO = this.findByUserIdAndTokenAddrAndWalletType(userId, token.getTokenAddr(), walletType);
        if (walletDTO == null) {
            throw new EthWalletException(EthWalletEnums.NULL_WALLETS);
        }
        List<EthGasWallet> list = ethGasWalletService.select();
        if (list.size() <= 0) {
            throw new EthWalletException(EthWalletEnums.NULL_GASWALLETNULL);
        }
        BigInteger tokenBalance = walletWeb3j.getTokenBalance(walletDTO.getAddr(), walletDTO.getTokenAddr());
        // ETH油费查询
        BigInteger ethGas = (new BigDecimal(amount).abs().multiply(BigDecimal.TEN.pow(18))).toBigInteger();
        for (EthGasWallet gasWallet : list) {
            String pk = RSACoderUtils.decryptPassword(gasWallet.getPrivateKey());
            String hash = walletWeb3j.ethWalletTransfer(gasWallet.getAddr(), pk, walletDTO.getAddr(), ethGas);
            if (hash == null) {
                throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
            }
            return;
        }

    }

    /**
     * 填充用户详情
     *
     * @param list
     * @return
     */
    private List<WalletBaseDTO> fillUserInfos(List<WalletBaseDTO> list) {
        Set<String> userIds = new HashSet<>();
        for (WalletBaseDTO row : list) {
            userIds.add(row.getUserId());
        }
        //  获取用户信息
        ResultDTO<Map<String, UserBaseInfoDTO>> result = userFeign.userInfos(userIds);
        if (result == null) throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        if (result.getCode() != BaseConstant.REQUEST_SUCCESS) throw new RPCException(result);
        Map<String, UserBaseInfoDTO> userMap = result.getData();
        for (WalletBaseDTO row : list) {
            String userId = row.getUserId();
            if (userMap.containsKey(userId)) row.setUserBaseInfoDTO(userMap.get(userId));
        }
        return list;
    }

    /**
     * @Description: 根据手机号获取用户信息
     * @Param: [userName]
     * @return: com.blockchain.common.base.dto.user.UserBaseInfoDTO
     * @Author: Liu.sd
     * @Date: 2019/3/23
     */
    private UserBaseInfoDTO ConditionsUserId(String userName) {
        //  获取用户信息
        ResultDTO<UserBaseInfoDTO> result = userFeign.selectUserInfoByUserName(userName);
        if (result == null) throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        if (result.getCode() != BaseConstant.REQUEST_SUCCESS) throw new RPCException(result);
        return result.getData();
    }

    /**
     * 修改指定应用钱包修改余额的简洁方法
     *
     * @param userOpenId
     * @param tokenAddr
     * @param walletType
     * @param tokenAddr
     * @param walletType
     */
    private EthWalletDTO updateBalanceByUserOpenId(String userOpenId, String tokenAddr, String walletType,
                                                   BigDecimal freeBlance, BigDecimal freezeBlance, Date date) {
        // 数据验证
        ExceptionPreconditionUtils.checkNotNull(freeBlance, new EthWalletException(EthWalletEnums.NULL_FREEBLANCE));
        ExceptionPreconditionUtils.checkNotNull(freezeBlance, new EthWalletException(EthWalletEnums.NULL_FREEZEBLANCE));
        EthWalletDTO walletDTO = findByUserIdAndTokenAddrAndWalletType(userOpenId, tokenAddr, walletType);
        if (freeBlance.add(walletDTO.getFreeBalance()).compareTo(BigDecimal.ZERO) < 0) {
            throw new EthWalletException(EthWalletEnums.NUMBER_INSUFFICIENT_ERROR);
        }
        if (freezeBlance.add(walletDTO.getFreezeBalance()).compareTo(BigDecimal.ZERO) < 0) {
            throw new EthWalletException(EthWalletEnums.NUMBER_INSUFFICIENTZE_ERROR);
        }
        // 进行行锁转账（应用钱包-应用钱包）
        int row = ethWalletMapper.updateBalanceByAddrInRowLock(walletDTO.getAddr(), tokenAddr, walletType,
                freeBlance.add(freezeBlance),
                freeBlance, freezeBlance, date);
        if (row == 0) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
        return findByUserIdAndTokenAddrAndWalletType(userOpenId, tokenAddr, walletType);
    }


}
