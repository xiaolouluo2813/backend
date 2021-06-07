package com.blockchain.server.btc.service.impl;


import com.blockchain.common.base.constant.DetailConstant;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.btc.common.constants.TotalTxConstants;
import com.blockchain.server.btc.common.enums.BtcWalletEnums;
import com.blockchain.server.btc.common.exception.BtcWalletException;
import com.blockchain.server.btc.dto.BtcTotalInfoDTO;
import com.blockchain.server.btc.dto.BtcWalletDTO;
import com.blockchain.server.btc.dto.BtcWalletTxBillDTO;
import com.blockchain.server.btc.entity.*;
import com.blockchain.server.btc.mapper.BtcClearingTotalMapper;
import com.blockchain.server.btc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 期初期末记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
@Service
public class BtcClearingTotalServiceImpl implements IBtcClearingTotalService {
    @Autowired
    BtcClearingTotalMapper ethClearingTotalMapper;
    @Autowired
    IBtcWalletService btcWalletService;
    @Autowired
    IBtcTokenService btcTokenService;
    @Autowired
    IBtcWalletTransferService btcWalletTransferService;
    @Autowired
    IBtcClearingDetailService btcClearingDetailService;
    @Autowired
    IBtcClearingCorrService btcClearingCorrService;

    @Override
    public BtcClearingTotal findNewByWallet(String addr, String tokenSymbol, String walletType) {
        return ethClearingTotalMapper.selectNewestOne(addr, tokenSymbol, walletType);
    }

    @Override
    public BtcClearingTotal findById(String id) {
        ExceptionPreconditionUtils.checkStringNotBlank(id, new BtcWalletException(BtcWalletEnums.NULL_TOTALID));
        BtcClearingTotal total = ethClearingTotalMapper.selectByPrimaryKey(id);
        ExceptionPreconditionUtils.checkNotNull(total, new BtcWalletException(BtcWalletEnums.NULL_TOKENALL));
        return total;
    }

    @Override
    public List<BtcTotalInfoDTO> selectInfoAll(String userId) {
        List<BtcTotalInfoDTO> infos = new ArrayList<>();
        List<BtcWallet> wallets = btcWalletService.selectByUserId(userId);
        for (BtcWallet wallet : wallets) {
            // 查询最新的统计记录
            BtcClearingTotal total = this.findNewByWallet(wallet.getAddr(), wallet.getTokenSymbol(),
                    wallet.getWalletType());
            if(null == total) continue;
            // 查询统计记录的流水记录
            List<BtcClearingDetail> details = btcClearingDetailService.selectByTotalId(total.getId());
            // 查询统计的更正记录
            List<BtcClearingCorr> corrs = btcClearingCorrService.selectByTotalId(total.getId());
            BtcTotalInfoDTO info = new BtcTotalInfoDTO();
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
        BtcClearingTotal total = this.findById(id); // 获取记录详情
        BtcToken ethToken = btcTokenService.findByTokenName(total.getTokenSymbol()); // 获取币种信息
        // 计算更正的额度
        BigDecimal balance = amount.subtract(total.getBalance());
        // 更正余额
        btcWalletService.updateFreeBalance(total.getUserId(), ethToken.getTokenId().toString(), total.getWalletType()
                , balance, date);
        // 添加更正记录
        BtcClearingCorr corr = getCorr(total, amount, date);
        btcClearingCorrService.insert(corr);

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
    private BtcClearingCorr getCorr(BtcClearingTotal total, BigDecimal amount, Date endDate) {
        BtcClearingCorr corr = new BtcClearingCorr();
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
    public void insert(BtcClearingTotal ethClearingTotal) {
        int row = ethClearingTotalMapper.insertSelective(ethClearingTotal);
        if (row == 0) {
            throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    @Override
    @Transactional
    public void insertTotal(BtcWalletDTO btcWallet, Date endDate) {
        endDate = endDate == null ? new Date() : endDate;
        // 查询最后一次统计结果
        BtcClearingTotal totalLast = this.findNewByWallet(btcWallet.getAddr(), btcWallet.getTokenSymbol(),
                btcWallet.getWalletType());
        endDate = btcWallet.getUpdateTime().compareTo(endDate) > 0 ? btcWallet.getUpdateTime() : endDate; // 期末时间
        Date startDate = totalLast == null ? btcWallet.getCreateTime() : totalLast.getCreateTime(); // 期初时间
        BtcWalletTxBillDTO txData = btcWalletTransferService.selectByAddrAndTime(btcWallet.getAddr(),
                btcWallet.getTokenId().toString(), startDate, endDate);
        BigDecimal lastAmount = totalLast == null ? BigDecimal.ZERO : totalLast.getRealBalance(); // 期初金额
        BigDecimal currentAmount = btcWallet.getBalance(); // 钱包当前的余额
        BigDecimal realBalance = lastAmount.add(txData.getCountAmount()); // 实际金额
        BigDecimal diffBalance = currentAmount.subtract(realBalance).abs(); // 资金偏差
        // 插入统计总记录
        BtcClearingTotal total = new BtcClearingTotal();
        String id = UUID.randomUUID().toString();
        total.setId(id);
        total.setAddr(btcWallet.getAddr());
        total.setBalance(currentAmount);
        total.setRealBalance(realBalance);
        total.setDiffBalance(diffBalance);
        total.setCreateTime(endDate);
        total.setModifyTime(endDate);
        total.setPreBalance(lastAmount);
        total.setPreTime(startDate);
        total.setStatus(TotalTxConstants.NEW);
        total.setTokenSymbol(btcWallet.getTokenSymbol());
        total.setUserId(btcWallet.getUserOpenId());
        total.setWalletLastTime(btcWallet.getUpdateTime());
        total.setWalletType(btcWallet.getWalletType());
        this.insert(total);
        btcClearingDetailService.insert(total, txData.getFromMap(), txData.getToMap());
        btcClearingDetailService.insert(total.getId(), DetailConstant.FREE,btcWallet.getFreeBalance(),total.getCreateTime());
        btcClearingDetailService.insert(total.getId(), DetailConstant.FREERE,btcWallet.getFreezeBalance(),total.getCreateTime());
    }

    @Override
    @Transactional
    public void insertTotal(BtcWallet btcWallet, Date endDate) {
        endDate = endDate == null ? new Date() : endDate;
        // 查询最后一次统计结果
        BtcClearingTotal totalLast = this.findNewByWallet(btcWallet.getAddr(), btcWallet.getTokenSymbol(),
                btcWallet.getWalletType());
        endDate = btcWallet.getUpdateTime().compareTo(endDate) > 0 ? btcWallet.getUpdateTime() : endDate; // 期末时间
        Date startDate = totalLast == null ? btcWallet.getCreateTime() : totalLast.getCreateTime(); // 期初时间
        BtcWalletTxBillDTO txData = btcWalletTransferService.selectByAddrAndTime(btcWallet.getAddr(),
                btcWallet.getTokenId().toString(), startDate, endDate);

        BigDecimal lastAmount = totalLast == null ? BigDecimal.ZERO : totalLast.getRealBalance(); // 期初金额
        BigDecimal currentAmount = btcWallet.getBalance(); // 钱包当前的余额
        BigDecimal realBalance = lastAmount.add(txData.getCountAmount()); // 实际金额
        BigDecimal diffBalance = currentAmount.subtract(realBalance).abs(); // 资金偏差

        // 插入统计总记录
        BtcClearingTotal total = new BtcClearingTotal();
        String id = UUID.randomUUID().toString();
        total.setId(id);
        total.setAddr(btcWallet.getAddr());
        total.setBalance(currentAmount);
        total.setRealBalance(realBalance);
        total.setDiffBalance(diffBalance);
        total.setCreateTime(endDate);
        total.setModifyTime(endDate);
        total.setPreBalance(lastAmount);
        total.setPreTime(startDate);
        total.setStatus(TotalTxConstants.NEW);
        total.setTokenSymbol(btcWallet.getTokenSymbol());
        total.setUserId(btcWallet.getUserOpenId());
        total.setWalletLastTime(btcWallet.getUpdateTime());
        total.setWalletType(btcWallet.getWalletType());
        this.insert(total);
        btcClearingDetailService.insert(total, txData.getFromMap(), txData.getToMap());
        btcClearingDetailService.insert(total.getId(), DetailConstant.FREE,btcWallet.getFreeBalance(),total.getCreateTime());
        btcClearingDetailService.insert(total.getId(), DetailConstant.FREERE,btcWallet.getFreezeBalance(),total.getCreateTime());
    }


    @Override
    @Transactional
    public List<BtcTotalInfoDTO> insertTotals(String userId) {
        Date endDate = new Date();
        List<BtcWallet> wallets = btcWalletService.selectByUserId(userId);
        for (BtcWallet wallet : wallets) {
            this.insertTotal(wallet, endDate);
        }
        List<BtcTotalInfoDTO> list = this.selectInfoAll(userId);
        return list;
    }


}
