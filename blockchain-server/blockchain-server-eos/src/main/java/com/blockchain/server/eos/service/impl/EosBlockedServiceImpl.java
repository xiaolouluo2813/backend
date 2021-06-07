package com.blockchain.server.eos.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.constants.tx.WalletBlockedConstants;
import com.blockchain.server.eos.entity.EosWalletBlockedDetail;
import com.blockchain.server.eos.entity.EosWalletBlockedTotal;
import com.blockchain.server.eos.entity.Wallet;
import com.blockchain.server.eos.feign.UserFeign;
import com.blockchain.server.eos.mapper.EosWalletBlockedDetailMapper;
import com.blockchain.server.eos.mapper.EosWalletBlockedTotalMapper;
import com.blockchain.server.eos.service.IEosBlockedService;
import com.blockchain.server.eos.service.IEosWalletService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author huangxl
 * @create 2019-06-25 11:39
 */
@Service
public class EosBlockedServiceImpl implements IEosBlockedService {
    @Autowired
    private IEosWalletService eosWalletService;
    @Autowired
    private EosWalletBlockedTotalMapper eosWalletBlockedTotalMapper;
    @Autowired
    private EosWalletBlockedDetailMapper eosWalletBlockedDetailMapper;
    @Autowired
    private UserFeign userFeign;

    @Override
    @Transactional
    public void blockBalance(String walletUid, String tokenSymbol, BigDecimal optNumber, String remark) {
        //校验参数不能为空
        ExceptionPreconditionUtils.notEmpty(walletUid, tokenSymbol, optNumber);
        //输入金额必须大于0
        if (optNumber.compareTo(BigDecimal.ZERO) <= 0) {
            throw new EosWalletException(EosWalletEnums.NUMBER_BLOCKED_ERROR);
        }
        int id = Integer.parseInt(walletUid);
        //查询钱包是否存在
        Wallet wallet = eosWalletService.findByAddrAndCoinName(id, tokenSymbol);
        if (wallet == null) {
            throw new EosWalletException(EosWalletEnums.INEXISTENCE_WALLET);
        }
        //判断可用余额是否充足
        if (wallet.getFreeBalance().compareTo(optNumber) < 0) {
            throw new EosWalletException(EosWalletEnums.NUMBER_INSUFFICIENT_ERROR);
        }

        //查询冻结记录
        EosWalletBlockedTotal total = findByWalletUidAndTokenSymbol(walletUid, tokenSymbol);

        String userOpenId = wallet.getUserOpenId();
        Date now = new Date();
        //冻结余额,可用余额 -= #{optNumber} ，冻结余额 += #{optNumber}
        eosWalletService.updateBlance(walletUid, tokenSymbol, optNumber.multiply(new BigDecimal("-1")), optNumber, now);

        //插入 | 更新记录
        if (total == null) {
            insertTotal(walletUid, tokenSymbol, optNumber, userOpenId);
        } else {
            updateTotal(total.getId(), optNumber, WalletBlockedConstants.TYPE_BLOCK);
        }
        //插入详情
        insertDetail(walletUid, tokenSymbol, optNumber, userOpenId, WalletBlockedConstants.TYPE_BLOCK, remark);
    }

    @Override
    public void unblockBalance(String walletUid, String tokenSymbol, BigDecimal optNumber, String remark) {
        //校验参数不能为空
        ExceptionPreconditionUtils.notEmpty(walletUid, tokenSymbol, optNumber);
        //输入金额必须大于0
        if (optNumber.compareTo(BigDecimal.ZERO) <= 0) {
            throw new EosWalletException(EosWalletEnums.NUMBER_BLOCKED_ERROR);
        }
        int id = Integer.parseInt(walletUid);
        //查询钱包是否存在
        Wallet wallet = eosWalletService.findByAddrAndCoinName(id, tokenSymbol);
        //判断可用余额是否充足
        if (wallet == null) {
            throw new EosWalletException(EosWalletEnums.INEXISTENCE_WALLETTYPE);
        }
        //判断冻结余额是否充足
        if (wallet.getFreezeBalance().compareTo(optNumber) < 0) {
            throw new EosWalletException(EosWalletEnums.NUMBER_INSUFFICIENTZE_ERROR);
        }
        //查询冻结记录
        EosWalletBlockedTotal total = findByWalletUidAndTokenSymbol(walletUid, tokenSymbol);
        if (total == null) {
            throw new EosWalletException(EosWalletEnums.NUMBER_UNBLOCK_INSUFFICIENTZE_ERROR);
        }
        if (total.getBlockedTotal().compareTo(optNumber) < 0) {
            throw new EosWalletException(EosWalletEnums.NUMBER_INSUFFICIENTZE_ERROR);
        }
        String userOpenId = wallet.getUserOpenId();
        Date now = new Date();
        //冻结余额,可用余额 += #{optNumber} ，冻结余额 -= #{optNumber}
        eosWalletService.updateBlance(walletUid, tokenSymbol, optNumber, optNumber.multiply(new BigDecimal("-1")), now);
        //更新
        updateTotal(total.getId(), optNumber, WalletBlockedConstants.TYPE_UNBLOCK);
        //插入记录
        insertDetail(walletUid, tokenSymbol, optNumber, userOpenId, WalletBlockedConstants.TYPE_UNBLOCK, remark);
    }

    @Override
    public List<EosWalletBlockedDetail> listDetails(String username, String type) {
        String userOpenId = null;
        if (StringUtils.isNotEmpty(username)) {
            ResultDTO<UserBaseInfoDTO> result = userFeign.selectUserInfoByUserName(username);
            if (result.getData() != null) {
                userOpenId = result.getData().getUserId();
            }
        }
        return eosWalletBlockedDetailMapper.listByParams(userOpenId, type);
    }

    @Override
    public EosWalletBlockedTotal selectTotal(String walletUid, String tokenSymbol) {
        return findByWalletUidAndTokenSymbol(walletUid, tokenSymbol);
    }

    //根据钱包id、代币名称查询钱包冻结总表记录
    private EosWalletBlockedTotal findByWalletUidAndTokenSymbol(String walletUid, String tokenSymbol) {
        EosWalletBlockedTotal total = new EosWalletBlockedTotal();
        total.setWalletUid(walletUid);
        total.setTokenSymbol(tokenSymbol);
        return eosWalletBlockedTotalMapper.selectOne(total);
    }

    //插入冻结总表记录
    private void insertTotal(String walletUid, String tokenSymbol, BigDecimal optNumber, String userOpenId) {
        EosWalletBlockedTotal total = new EosWalletBlockedTotal();
        total.setId(UUID.randomUUID().toString());
        total.setWalletUid(walletUid);
        total.setTokenSymbol(tokenSymbol);
        total.setBlockedTotal(optNumber);
        total.setUserOpenId(userOpenId);
        Date now = new Date();
        total.setCreateTime(now);
        total.setModifyTime(now);
        try {
            //并发处理
            eosWalletBlockedTotalMapper.insert(total);
        } catch (Exception e) {
            throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    private void updateTotal(String id, BigDecimal optNumber, String type) {
        Date now = new Date();
        if (type.equals(WalletBlockedConstants.TYPE_BLOCK)) {
            //增加冻结余额总额
            eosWalletBlockedTotalMapper.updateTotalByIdInRowLock(id, optNumber, now);
        } else if (type.equals(WalletBlockedConstants.TYPE_UNBLOCK)) {
            //减少冻结余额总额
            int row = eosWalletBlockedTotalMapper.updateTotalByIdInRowLock(id, optNumber.multiply(new BigDecimal("-1")), now);
            if (row == 0) {
                throw new EosWalletException(EosWalletEnums.NUMBER_INSUFFICIENTZE_ERROR);
            }
        } else {
            throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    //插入冻结明细记录
    private void insertDetail(String walletUid, String tokenSymbol, BigDecimal optNumber, String userOpenId, String type, String remark) {
        EosWalletBlockedDetail detail = new EosWalletBlockedDetail();

        detail.setId(UUID.randomUUID().toString());
        detail.setWalletUid(walletUid);
        detail.setTokenSymbol(tokenSymbol);
        detail.setUserOpenId(userOpenId);
        detail.setOptTotal(optNumber);
        detail.setType(type);
        detail.setSystemUserId(SecurityUtils.getUserId());
        detail.setIpAddr(HttpRequestUtil.getIpAddr());
        detail.setRemark(remark);
        Date now = new Date();
        detail.setCreateTime(now);
        detail.setModifyTime(now);
        eosWalletBlockedDetailMapper.insert(detail);
    }
}
