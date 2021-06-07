package com.blockchain.server.eos.service.impl;


import com.blockchain.common.base.constant.DetailConstant;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.constants.tx.TotalTxConstants;
import com.blockchain.server.eos.dto.EosTotalInfoDTO;
import com.blockchain.server.eos.dto.EosWalletTxBillDTO;
import com.blockchain.server.eos.dto.WalletDTO;
import com.blockchain.server.eos.entity.*;
import com.blockchain.server.eos.mapper.EosClearingTotalMapper;
import com.blockchain.server.eos.service.*;
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
public class EosClearingTotalServiceImpl implements IEosClearingTotalService {
    @Autowired
    EosClearingTotalMapper eosClearingTotalMapper;
    @Autowired
    IEosWalletService eosWalletService;
    @Autowired
    IEosTokenService eosTokenService;
    @Autowired
    IEosWalletTransferService eosWalletTransferService;
    @Autowired
    IEosClearingDetailService eosClearingDetailService;
    @Autowired
    IEosClearingCorrService eosClearingCorrService;

    @Override
    public EosClearingTotal findNewByWallet(String addr, String tokenSymbol, String walletType) {
        return eosClearingTotalMapper.selectNewestOne(addr, tokenSymbol, walletType);
    }

    @Override
    public EosClearingTotal findById(String id) {
        ExceptionPreconditionUtils.checkStringNotBlank(id, new EosWalletException(EosWalletEnums.NULL_TOTALID));
        EosClearingTotal total = eosClearingTotalMapper.selectByPrimaryKey(id);
        ExceptionPreconditionUtils.checkNotNull(total, new EosWalletException(EosWalletEnums.NULL_TOKENALL));
        return total;
    }

    @Override
    public List<EosTotalInfoDTO> selectInfoAll(String userId) {
        List<EosTotalInfoDTO> infos = new ArrayList<>();
        List<Wallet> wallets = eosWalletService.selectByUserId(userId);
        for (Wallet wallet : wallets) {
            // 查询最新的统计记录
            EosClearingTotal total = this.findNewByWallet(wallet.getId().toString(), wallet.getTokenSymbol(),
                    wallet.getWalletType());
            if(null == total) continue;
            // 查询统计记录的流水记录
            List<EosClearingDetail> details = eosClearingDetailService.selectByTotalId(total.getId());
            // 查询统计的更正记录
            List<EosClearingCorr> corrs = eosClearingCorrService.selectByTotalId(total.getId());
            EosTotalInfoDTO info = new EosTotalInfoDTO();
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
        EosClearingTotal total = this.findById(id); // 获取记录详情
        Token eosToken = eosTokenService.findByTokenSymbol(total.getTokenSymbol()); // 获取币种信息
        // 计算更正的额度
        BigDecimal balance = amount.subtract(total.getBalance());
        // 更正余额
        eosWalletService.updateFreeBalance(total.getUserId(), eosToken.getTokenName(), total.getWalletType(), balance
                , date);

        // 添加更正记录
        EosClearingCorr corr = getCorr(total, amount, date);
        eosClearingCorrService.insert(corr);
        total.setBalance(amount);
        total.setDiffBalance(amount.subtract(total.getRealBalance()).abs());
        total.setStatus(TotalTxConstants.CORR);
        eosClearingTotalMapper.updateByPrimaryKeySelective(total);
    }

    /**
     * 封装更正记录对象的方法
     *
     * @param total   统计对象数据
     * @param amount  更正后的值
     * @param endDate 结束时间
     * @return
     */
    private EosClearingCorr getCorr(EosClearingTotal total, BigDecimal amount, Date endDate) {
        EosClearingCorr corr = new EosClearingCorr();
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
    public void insert(EosClearingTotal ethClearingTotal) {
        int row = eosClearingTotalMapper.insertSelective(ethClearingTotal);
        if (row == 0) {
            throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    @Override
    @Transactional
    public void insertTotal(WalletDTO ethWallet, Date endDate) {
        endDate = endDate == null ? new Date() : endDate;
        // 查询最后一次统计结果
        EosClearingTotal totalLast = this.findNewByWallet(ethWallet.getId().toString(), ethWallet.getTokenSymbol(),
                ethWallet.getWalletType());
        endDate = ethWallet.getUpdateTime().compareTo(endDate) > 0 ? ethWallet.getUpdateTime() : endDate; // 期末时间
        Date startDate = totalLast == null ? ethWallet.getCreateTime() : totalLast.getCreateTime(); // 期初时间
        EosWalletTxBillDTO txData = eosWalletTransferService.selectByAddrAndTime(ethWallet.getId().toString(),
                ethWallet.getTokenName(), startDate, endDate);
        BigDecimal lastAmount = totalLast == null ? BigDecimal.ZERO : totalLast.getRealBalance(); // 期初金额
        BigDecimal currentAmount = ethWallet.getBalance(); // 钱包当前的余额
        BigDecimal realBalance = lastAmount.add(txData.getCountAmount()); // 实际金额
        BigDecimal diffBalance = currentAmount.subtract(realBalance).abs(); // 资金偏差
        // 插入统计总记录
        EosClearingTotal total = new EosClearingTotal();
        String id = UUID.randomUUID().toString();
        total.setId(id);
        total.setAddr(ethWallet.getId().toString());
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
        eosClearingDetailService.insert(total, txData.getFromMap(), txData.getToMap());
    }

    @Override
    @Transactional
    public void insertTotal(Wallet eosWallet, Date endDate) {
        endDate = endDate == null ? new Date() : endDate;
        // 查询最后一次统计结果
        EosClearingTotal totalLast = this.findNewByWallet(eosWallet.getId().toString(), eosWallet.getTokenSymbol(),
                eosWallet.getWalletType());
        endDate = eosWallet.getUpdateTime().compareTo(endDate) > 0 ? eosWallet.getUpdateTime() : endDate; // 期末时间
        Date startDate = totalLast == null ? eosWallet.getCreateTime() : totalLast.getCreateTime(); // 期初时间
        EosWalletTxBillDTO txData = eosWalletTransferService.selectByAddrAndTime(eosWallet.getId().toString(),
                eosWallet.getTokenName(), startDate, endDate);
        BigDecimal lastAmount = totalLast == null ? BigDecimal.ZERO : totalLast.getRealBalance(); // 期初金额
        BigDecimal currentAmount = eosWallet.getBalance(); // 钱包当前的余额
        BigDecimal realBalance = lastAmount.add(txData.getCountAmount()); // 实际金额
        BigDecimal diffBalance = currentAmount.subtract(realBalance).abs(); // 资金偏差
        // 插入统计总记录
        EosClearingTotal total = new EosClearingTotal();
        String id = UUID.randomUUID().toString();
        total.setId(id);
        total.setAddr(eosWallet.getId().toString());
        total.setBalance(currentAmount);
        total.setRealBalance(realBalance);
        total.setDiffBalance(diffBalance);
        total.setCreateTime(endDate);
        total.setModifyTime(endDate);
        total.setPreBalance(lastAmount);
        total.setPreTime(startDate);
        total.setStatus(TotalTxConstants.NEW);
        total.setTokenSymbol(eosWallet.getTokenSymbol());
        total.setUserId(eosWallet.getUserOpenId());
        total.setWalletLastTime(eosWallet.getUpdateTime());
        total.setWalletType(eosWallet.getWalletType());
        this.insert(total);
        eosClearingDetailService.insert(total, txData.getFromMap(), txData.getToMap());
        eosClearingDetailService.insert(total.getId(), DetailConstant.FREE,eosWallet.getFreeBalance(),total.getCreateTime());
        eosClearingDetailService.insert(total.getId(), DetailConstant.FREERE,eosWallet.getFreezeBalance(),total.getCreateTime());
    }


    @Override
    @Transactional
    public List<EosTotalInfoDTO> insertTotals(String userId) {
        Date endDate = new Date();
        List<Wallet> wallets = eosWalletService.selectByUserId(userId);
        for (Wallet wallet : wallets) {
            this.insertTotal(wallet, endDate);
        }
        List<EosTotalInfoDTO> list = this.selectInfoAll(userId);
        return list;
    }


}
