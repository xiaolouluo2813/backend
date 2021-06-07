package com.blockchain.server.eos.service.impl;


import com.blockchain.common.base.constant.DetailConstant;
import com.blockchain.common.base.dto.wallet.BalanceDTO;
import com.blockchain.common.base.dto.wallet.WalletCountTotalDTO;
import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.constants.tx.TotalTxConstants;
import com.blockchain.server.eos.constants.tx.TxTypeConstants;
import com.blockchain.server.eos.dto.EosCountTotalInfoDTO;
import com.blockchain.server.eos.dto.EosWalletCountTxBillDTO;
import com.blockchain.server.eos.entity.EosClearingCountDetail;
import com.blockchain.server.eos.entity.EosClearingCountTotal;
import com.blockchain.server.eos.entity.Token;
import com.blockchain.server.eos.mapper.EosClearingCountTotalMapper;
import com.blockchain.server.eos.service.IEosClearingCountDetailService;
import com.blockchain.server.eos.service.IEosClearingCountTotalService;
import com.blockchain.server.eos.service.IEosTokenService;
import com.blockchain.server.eos.service.IEosWalletService;
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
public class EosClearingCountTotalServiceImpl implements IEosClearingCountTotalService {
    @Autowired
    IEosTokenService tokenService;
    @Autowired
    EosClearingCountTotalMapper countTotalMapper;
    @Autowired
    IEosClearingCountDetailService eosClearingCountDetailService;
    @Autowired
    IEosWalletService eosWalletService;


    @Override
    public EosClearingCountTotal findTotalLast(String coinName) {
        EosClearingCountTotal countTotal = countTotalMapper.selectNewestOne(coinName);
        if (null == countTotal) {
            countTotal = new EosClearingCountTotal();
            countTotal.setBalance(BigDecimal.ZERO);
            countTotal.setDiffBalance(BigDecimal.ZERO);
            countTotal.setRealBalance(BigDecimal.ZERO);
            countTotal.setCreateTime(null);
            countTotal.setTokenSymbol(coinName);
        }
        return countTotal;
    }

    @Override
    public List<EosCountTotalInfoDTO> selectInfoAll() {
        List<EosCountTotalInfoDTO> infos = new ArrayList<>();
        List<Token> coins = tokenService.selectAll();
        for (Token coin : coins) {
            // 查询最新的统计记录
            EosClearingCountTotal countTotal = this.findTotalLast(coin.getTokenSymbol());
            // 查询统计记录的流水记录
            List<EosClearingCountDetail> countDetails = eosClearingCountDetailService.selectByTotalId(countTotal.getId());
            EosCountTotalInfoDTO info = new EosCountTotalInfoDTO();
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
        EosClearingCountTotal countTotal = this.findTotalLast(coinName);
        List<WalletCountTotalDTO> basicsRecord = countTotalMapper.selectBasicsRecord(coinName,null, countTotal.getCreateTime(), endDate);
        BalanceDTO balanceDTO = getBalance(basicsRecord); // 统计用户余额
        EosWalletCountTxBillDTO dataDTO = getData(basicsRecord); // 统计基础数据
        BigDecimal lastAmount = countTotal.getRealBalance(); // 期初金额
        BigDecimal currentAmount = balanceDTO.getBalance(); // 当前的余额
        BigDecimal realBalance = lastAmount.add(dataDTO.getCountAmount()); // 实际金额
        BigDecimal diffBalance = currentAmount.subtract(realBalance).abs(); // 资金偏差
        // 插入统计总记录
        EosClearingCountTotal txData = new EosClearingCountTotal();
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
        eosClearingCountDetailService.insert(txData, dataDTO.getFromMap(), dataDTO.getToMap());
        eosClearingCountDetailService.insert(txData.getId(), DetailConstant.FREE, balanceDTO.getFreeBalance(), txData.getCreateTime());
        eosClearingCountDetailService.insert(txData.getId(), DetailConstant.FREERE, balanceDTO.getFreezeBalance(), txData.getCreateTime());
    }

    @Override
    @Transactional
    public List<EosCountTotalInfoDTO> insertTotals() {
        List<Token> coins = tokenService.selectAll();
        for (Token coin : coins) {
            this.insertTotal(coin.getTokenSymbol());
        }
        List<EosCountTotalInfoDTO> list = this.selectInfoAll();
        return list;
    }

    @Override
    @Transactional
    public void insert(EosClearingCountTotal countTotal) {
        int row = countTotalMapper.insertSelective(countTotal);
        if (row == 0) {
            throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
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

    private EosWalletCountTxBillDTO getData(List<WalletCountTotalDTO> txs) {
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
                if (isAddr(row.getFromAddr())) {
                    BigDecimal amount = row.getAmount();
                    if (formMap.containsKey(row.getTransferType())) {
                        amount = formMap.get(row.getTransferType()).add(amount);
                    }
                    formMap.put(row.getTransferType(), amount);
                    fromAmount = fromAmount.add(row.getAmount());
                }
                if (isAddr(row.getToAddr())) {
                    BigDecimal amount = row.getAmount();
                    if (toMap.containsKey(row.getTransferType())) {
                        amount = toMap.get(row.getTransferType()).add(amount);
                    }
                    toMap.put(row.getTransferType(), amount);
                    toAmount = toAmount.add(row.getAmount());
                }
            }
        }
        EosWalletCountTxBillDTO data = new EosWalletCountTxBillDTO();
        data.setFromMap(formMap);
        data.setToMap(toMap);
        data.setCountFromAmount(fromAmount);
        data.setCountToAmount(toAmount);
        data.setCountAmount(toAmount.subtract(fromAmount));
        return data;
    }

    /**
     * 验证是否为钱包地址
     *
     * @param str 地址
     * @return boolean
     */
    private boolean isAddr(String str) {
        return eosWalletService.isId(str);
    }

}
