package com.blockchain.server.btc.service.impl;


import com.blockchain.common.base.constant.DetailConstant;
import com.blockchain.common.base.dto.wallet.BalanceDTO;
import com.blockchain.common.base.dto.wallet.WalletCountTotalDTO;
import com.blockchain.server.btc.common.constants.TotalTxConstants;
import com.blockchain.server.btc.common.constants.TxTypeConstants;
import com.blockchain.server.btc.common.enums.BtcWalletEnums;
import com.blockchain.server.btc.common.exception.BtcWalletException;
import com.blockchain.server.btc.common.util.BtcAddressSetRedisUtils;
import com.blockchain.server.btc.dto.BtcCountTotalInfoDTO;
import com.blockchain.server.btc.dto.BtcWalletCountTxBillDTO;
import com.blockchain.server.btc.entity.BtcApplication;
import com.blockchain.server.btc.entity.BtcClearingCountDetail;
import com.blockchain.server.btc.entity.BtcClearingCountTotal;
import com.blockchain.server.btc.entity.BtcToken;
import com.blockchain.server.btc.mapper.BtcClearingCountTotalMapper;
import com.blockchain.server.btc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 期初期末记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
@Service
public class BtcClearingCountTotalServiceImpl implements IBtcClearingCountTotalService {
    @Autowired
    IBtcTokenService tokenService;
    @Autowired
    IBtcWalletService btcWalletService;
    @Autowired
    BtcClearingCountTotalMapper countTotalMapper;
    @Autowired
    IBtcClearingCountDetailService ethClearingCountDetailService;
    @Autowired
    BtcAddressSetRedisUtils btcAddressSetRedisUtils;



    @Override
    public BtcClearingCountTotal findTotalLast(String coinName) {
        BtcClearingCountTotal countTotal = countTotalMapper.selectNewestOne(coinName);
        if (null == countTotal) {
            countTotal = new BtcClearingCountTotal();
            countTotal.setBalance(BigDecimal.ZERO);
            countTotal.setDiffBalance(BigDecimal.ZERO);
            countTotal.setRealBalance(BigDecimal.ZERO);
            countTotal.setCreateTime(null);
            countTotal.setTokenSymbol(coinName);
        }
        return countTotal;
    }

    @Override
    public List<BtcCountTotalInfoDTO> selectInfoAll() {
        List<BtcCountTotalInfoDTO> infos = new ArrayList<>();
        List<BtcToken> coins = tokenService.selectAll();
        for (BtcToken coin : coins) {
            // 查询最新的统计记录
            BtcClearingCountTotal countTotal = this.findTotalLast(coin.getTokenSymbol());
            // 查询统计记录的流水记录
            List<BtcClearingCountDetail> countDetails = ethClearingCountDetailService.selectByTotalId(countTotal.getId());
            BtcCountTotalInfoDTO info = new BtcCountTotalInfoDTO();
            info.setTotal(countTotal);
            info.setDetails(countDetails);
            infos.add(info);
        }
        return infos;
    }

    @Override
    @Transactional
    public void insertTotal(String coinName) {
        // 获取结束时间
        Date endDate = new Date();
        // 查询上一次最新的统计
        BtcClearingCountTotal countTotal = this.findTotalLast(coinName);
        List<WalletCountTotalDTO> basicsRecord = countTotalMapper.selectBasicsRecord(coinName,null, countTotal.getCreateTime(), endDate);
        BalanceDTO balanceDTO = getBalance(basicsRecord); // 统计用户余额
        BtcWalletCountTxBillDTO dataDTO = getData(basicsRecord); // 统计基础数据
        BigDecimal lastAmount = countTotal.getRealBalance(); // 期初金额
        BigDecimal currentAmount = balanceDTO.getBalance(); // 当前的余额
        BigDecimal realBalance = lastAmount.add(dataDTO.getCountAmount()); // 实际金额
        BigDecimal diffBalance = currentAmount.subtract(realBalance).abs(); // 资金偏差
        // 插入统计总记录
        BtcClearingCountTotal txData = new BtcClearingCountTotal();
        String id = UUID.randomUUID().toString();
        txData.setId(id);
        txData.setBalance(currentAmount);
        txData.setRealBalance(realBalance);
        txData.setDiffBalance(diffBalance);
        txData.setCreateTime(endDate);
        txData.setModifyTime(endDate);
        txData.setPreBalance(lastAmount);
        txData.setPreTime(countTotal.getCreateTime());
        txData.setStatus(TotalTxConstants.NEW);
        txData.setTokenSymbol(coinName);
        this.insert(txData);
        ethClearingCountDetailService.insert(txData, dataDTO.getFromMap(), dataDTO.getToMap());
        ethClearingCountDetailService.insert(txData.getId(), DetailConstant.FREE, balanceDTO.getFreeBalance(), txData.getCreateTime());
        ethClearingCountDetailService.insert(txData.getId(), DetailConstant.FREERE, balanceDTO.getFreezeBalance(), txData.getCreateTime());
    }

    @Override
    @Transactional
    public List<BtcCountTotalInfoDTO> insertTotals() {
        List<BtcToken> coins = tokenService.selectAll();
        for (BtcToken coin : coins) {
            this.insertTotal(coin.getTokenSymbol());
        }
        List<BtcCountTotalInfoDTO> list = this.selectInfoAll();
        return list;
    }

    @Override
    @Transactional
    public void insert(BtcClearingCountTotal countTotal) {
        int row = countTotalMapper.insertSelective(countTotal);
        if (row == 0) {
            throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    /**
     * 获取统计的用户总余额
     *
     * @param list
     * @return
     */
    private BalanceDTO getBalance(List<WalletCountTotalDTO> list) {
        // 标记用户资产是否已结算
        Set<String> addrs = new HashSet<>();
        BigDecimal totalBalance = BigDecimal.ZERO;  // 总额
        BigDecimal totalFreeBalance = BigDecimal.ZERO;  // 可用额度
        BigDecimal totalFreezeBalance = BigDecimal.ZERO;    // 冻结额度
        for (WalletCountTotalDTO row : list) {
            if (!addrs.contains(row.getAddr())) {
                addrs.add(row.getAddr());
                totalBalance = totalBalance.add(new BigDecimal(row.getBalance()));
                totalFreeBalance = totalFreeBalance.add(new BigDecimal(row.getFreeBalance()));
                totalFreezeBalance = totalFreezeBalance.add(new BigDecimal(row.getFreezeBalance()));
            }
        }
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setBalance(totalBalance);
        balanceDTO.setFreeBalance(totalFreeBalance);
        balanceDTO.setFreezeBalance(totalFreezeBalance);
        return balanceDTO;
    }

    private BtcWalletCountTxBillDTO getData(List<WalletCountTotalDTO> txs) {
        // 定义数据MAP，整合数据
        // 第一层以{币种地址}为KEY，数据详情为Value
        // 第二层以{记录类型}为KEY,总和为Value
        Map<String, BigDecimal> formMap = new HashMap<>();
        Map<String, BigDecimal> toMap = new HashMap<>();
        BigDecimal fromAmount = BigDecimal.ZERO;
        BigDecimal toAmount = BigDecimal.ZERO;
        for (WalletCountTotalDTO row : txs) {
            if (null == row.getTxId()) continue;
            if (TxTypeConstants.IN.equalsIgnoreCase(row.getTransferType())) {
                BigDecimal amount = row.getAmount();
                if (toMap.containsKey(row.getTransferType())) {
                    amount = toMap.get(row.getTransferType()).add(amount);
                }
                toMap.put(row.getTransferType(), amount);
                toAmount = toAmount.add(row.getAmount());
            } else if (TxTypeConstants.OUT.equalsIgnoreCase(row.getTransferType())) {
                BigDecimal amount = row.getAmount();
                if (formMap.containsKey(row.getTransferType())) {
                    amount = formMap.get(row.getTransferType()).add(amount);
                }
                formMap.put(row.getTransferType(), amount);
                fromAmount = fromAmount.add(row.getAmount());
            } else if (TxTypeConstants.FAST.equalsIgnoreCase(row.getTransferType())) {
                BigDecimal amount = row.getAmount();
                if (formMap.containsKey(row.getTransferType())) {
                    amount = formMap.get(row.getTransferType()).add(amount);
                }
                formMap.put(row.getTransferType(), amount);
                amount = row.getAmount();
                if (toMap.containsKey(row.getTransferType())) {
                    amount = toMap.get(row.getTransferType()).add(amount);
                }
                toMap.put(row.getTransferType(), amount);
                fromAmount = fromAmount.add(row.getAmount());
                toAmount = toAmount.add(row.getAmount());
            } else {
                if (btcAddressSetRedisUtils.isExistsAddr(row.getFromAddr())) {
                    BigDecimal amount = row.getAmount();
                    if (formMap.containsKey(row.getTransferType())) {
                        amount = formMap.get(row.getTransferType()).add(amount);
                    }
                    formMap.put(row.getTransferType(), amount);
                    fromAmount = fromAmount.add(row.getAmount());
                }
                if (btcAddressSetRedisUtils.isExistsAddr(row.getToAddr())) {
                    BigDecimal amount = row.getAmount();
                    if (toMap.containsKey(row.getTransferType())) {
                        amount = toMap.get(row.getTransferType()).add(amount);
                    }
                    toMap.put(row.getTransferType(), amount);
                    toAmount = toAmount.add(row.getAmount());
                }
            }
        }
        BtcWalletCountTxBillDTO data = new BtcWalletCountTxBillDTO();
        data.setFromMap(formMap);
        data.setToMap(toMap);
        data.setCountFromAmount(fromAmount);
        data.setCountToAmount(toAmount);
        data.setCountAmount(toAmount.subtract(fromAmount));
        return data;
    }
}
