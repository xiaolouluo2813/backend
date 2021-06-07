package com.blockchain.server.eth.service.impl;


import com.blockchain.common.base.constant.DetailConstant;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.eth.common.constants.tx.TotalTxConstants;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.dto.tx.EthWalletTxBillDTO;
import com.blockchain.server.eth.dto.wallet.EthTotalInfoDTO;
import com.blockchain.server.eth.dto.wallet.EthWalletBillDTO;
import com.blockchain.server.eth.dto.wallet.EthWalletDTO;
import com.blockchain.server.eth.entity.*;
import com.blockchain.server.eth.mapper.EthClearingTotalMapper;
import com.blockchain.server.eth.mapper.EthWalletMapper;
import com.blockchain.server.eth.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 期初期末记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
@Service
public class EthClearingTotalServiceImpl implements IEthClearingTotalService {
    @Autowired
    EthClearingTotalMapper ethClearingTotalMapper;
    @Autowired
    IEthWalletService ethWalletService;
    @Autowired
    IEthTokenService ethTokenService;
    @Autowired
    IEthWalletTransferService ethWalletTransferService;
    @Autowired
    IEthClearingDetailService ethClearingDetailService;
    @Autowired
    IEthClearingCorrService ethClearingCorrService;

    @Override
    public EthClearingTotal findNewByWallet(String addr, String tokenSymbol, String walletType) {
        return ethClearingTotalMapper.selectNewestOne(addr, tokenSymbol, walletType);
    }

    @Override
    public EthClearingTotal findById(String id) {
        ExceptionPreconditionUtils.checkStringNotBlank(id, new EthWalletException(EthWalletEnums.NULL_TOTALID));
        EthClearingTotal total = ethClearingTotalMapper.selectByPrimaryKey(id);
        ExceptionPreconditionUtils.checkNotNull(total, new EthWalletException(EthWalletEnums.NULL_TOKENALL));
        return total;
    }

    @Override
    public List<EthTotalInfoDTO> selectInfoAll(String userId) {
        List<EthTotalInfoDTO> infos = new ArrayList<>();
        List<EthWallet> wallets = ethWalletService.selectByUserId(userId);
        for (EthWallet wallet : wallets) {
            // 查询最新的统计记录
            EthClearingTotal total = this.findNewByWallet(wallet.getAddr(), wallet.getTokenSymbol(),
                    wallet.getWalletType());
            if(null == total) continue;
            // 查询统计记录的流水记录
            List<EthClearingDetail> details = ethClearingDetailService.selectByTotalId(total.getId());
            // 查询统计的更正记录
            List<EthClearingCorr> corrs = ethClearingCorrService.selectByTotalId(total.getId());
            EthTotalInfoDTO info = new EthTotalInfoDTO();
            info.setTotal(total);
            info.setCorrs(corrs);
            info.setDetails(details);
            infos.add(info);
        }
        return infos;
    }


    @Override
    @Transactional
    public void updateCorr(String id, BigDecimal amount) {
        Date date = new Date();
        EthClearingTotal total = this.findById(id); // 获取记录详情
        EthToken ethToken = ethTokenService.findByTokenName(total.getTokenSymbol()); // 获取币种信息
        // 计算更正的额度
        BigDecimal balance = amount.subtract(total.getBalance());
        // 更正余额
        ethWalletService.updateFreeBalance(total.getUserId(), ethToken.getTokenAddr(), total.getWalletType(), balance
                , date);

        // 添加更正记录
        EthClearingCorr corr = getCorr(total, amount, date);
        ethClearingCorrService.insert(corr);
        total.setBalance(amount);
        total.setDiffBalance(amount.subtract(total.getRealBalance()).abs());
        total.setStatus(TotalTxConstants.CORR);
        ethClearingTotalMapper.updateByPrimaryKeySelective(total);
    }

    /**
     * 封装更正记录对象的方法
     *
     * @param total   统计对象数据
     * @param amount  更正后的值
     * @param endDate 结束时间
     * @return
     */
    private EthClearingCorr getCorr(EthClearingTotal total, BigDecimal amount, Date endDate) {
        EthClearingCorr corr = new EthClearingCorr();
        corr.setTotalId(total.getId());
        corr.setUserId(total.getUserId());
        corr.setTokenSymbol(total.getTokenSymbol());
        corr.setWalletType(total.getWalletType());
        corr.setPreBalance(total.getBalance());
        corr.setAfterBalance(amount);
        corr.setClearingTime(total.getCreateTime());
        corr.setCreateTime(endDate);
        return corr;
    }

    @Override
    public void insert(EthClearingTotal ethClearingTotal) {
        int row = ethClearingTotalMapper.insertSelective(ethClearingTotal);
        if (row == 0) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    @Override
    @Transactional
    public void insertTotal(EthWalletDTO ethWallet, Date endDate) {
        endDate = endDate == null ? new Date() : endDate;
        // 查询最后一次统计结果
        EthClearingTotal totalLast = this.findNewByWallet(ethWallet.getAddr(), ethWallet.getTokenSymbol(),
                ethWallet.getWalletType());
        endDate = ethWallet.getUpdateTime().compareTo(endDate) > 0 ? ethWallet.getUpdateTime() : endDate; // 期末时间
        Date startDate = totalLast == null ? ethWallet.getCreateTime() : totalLast.getCreateTime(); // 期初时间
        EthWalletTxBillDTO txData = ethWalletTransferService.selectByAddrAndTime(ethWallet.getAddr(),
                ethWallet.getTokenAddr(), startDate, endDate);
        BigDecimal lastAmount = totalLast == null ? BigDecimal.ZERO : totalLast.getRealBalance(); // 期初金额
        BigDecimal currentAmount = ethWallet.getBalance(); // 钱包当前的余额
        BigDecimal realBalance = lastAmount.add(txData.getCountAmount()); // 实际金额
        BigDecimal diffBalance = currentAmount.subtract(realBalance).abs(); // 资金偏差
        // 插入统计总记录
        EthClearingTotal total = new EthClearingTotal();
        String id = UUID.randomUUID().toString();
        total.setId(id);
        total.setAddr(ethWallet.getAddr());
        total.setBalance(currentAmount);
        total.setRealBalance(realBalance);
        total.setDiffBalance(diffBalance);
        total.setCreateTime(endDate);
        total.setModifyTime(endDate);
        total.setPreBalance(lastAmount);
        total.setPreTime(startDate);
        total.setStatus(TotalTxConstants.NEW);
        total.setTokenSymbol(ethWallet.getTokenSymbol());
        total.setUserId(ethWallet.getUserOpenId());
        total.setWalletLastTime(ethWallet.getUpdateTime());
        total.setWalletType(ethWallet.getWalletType());
        this.insert(total);
        ethClearingDetailService.insert(total, txData.getFromMap(), txData.getToMap());
        ethClearingDetailService.insert(total.getId(), DetailConstant.FREE,ethWallet.getFreeBalance(),total.getCreateTime());
        ethClearingDetailService.insert(total.getId(), DetailConstant.FREERE,ethWallet.getFreezeBalance(),total.getCreateTime());
    }

    @Override
    @Transactional
    public void insertTotal(EthWallet ethWallet, Date endDate) {
        endDate = endDate == null ? new Date() : endDate;
        // 查询最后一次统计结果
        EthClearingTotal totalLast = this.findNewByWallet(ethWallet.getAddr(), ethWallet.getTokenSymbol(),
                ethWallet.getWalletType());
        endDate = ethWallet.getUpdateTime().compareTo(endDate) > 0 ? ethWallet.getUpdateTime() : endDate; // 期末时间
        Date startDate = totalLast == null ? ethWallet.getCreateTime() : totalLast.getCreateTime(); // 期初时间
        EthWalletTxBillDTO txData = ethWalletTransferService.selectByAddrAndTime(ethWallet.getAddr(),
                ethWallet.getTokenAddr(), startDate, endDate);
        BigDecimal lastAmount = totalLast == null ? BigDecimal.ZERO : totalLast.getRealBalance(); // 期初金额
        BigDecimal currentAmount = ethWallet.getBalance(); // 钱包当前的余额
        BigDecimal realBalance = lastAmount.add(txData.getCountAmount()); // 实际金额
        BigDecimal diffBalance = currentAmount.subtract(realBalance).abs(); // 资金偏差
        // 插入统计总记录
        EthClearingTotal total = new EthClearingTotal();
        String id = UUID.randomUUID().toString();
        total.setId(id);
        total.setAddr(ethWallet.getAddr());
        total.setBalance(currentAmount);
        total.setRealBalance(realBalance);
        total.setDiffBalance(diffBalance);
        total.setCreateTime(endDate);
        total.setModifyTime(endDate);
        total.setPreBalance(lastAmount);
        total.setPreTime(startDate);
        total.setStatus(TotalTxConstants.NEW);
        total.setTokenSymbol(ethWallet.getTokenSymbol());
        total.setUserId(ethWallet.getUserOpenId());
        total.setWalletLastTime(ethWallet.getUpdateTime());
        total.setWalletType(ethWallet.getWalletType());
        this.insert(total);
        ethClearingDetailService.insert(total, txData.getFromMap(), txData.getToMap());
        ethClearingDetailService.insert(total.getId(), DetailConstant.FREE,ethWallet.getFreeBalance(),total.getCreateTime());
        ethClearingDetailService.insert(total.getId(), DetailConstant.FREERE,ethWallet.getFreezeBalance(),total.getCreateTime());
    }


    @Override
    @Transactional
    public List<EthTotalInfoDTO> insertTotals(String userId) {
        Date endDate = new Date();
        List<EthWallet> wallets = ethWalletService.selectByUserId(userId);
        for (EthWallet wallet : wallets) {
            this.insertTotal(wallet, endDate);
        }
        List<EthTotalInfoDTO> list = this.selectInfoAll(userId);
        return list;
    }


}
