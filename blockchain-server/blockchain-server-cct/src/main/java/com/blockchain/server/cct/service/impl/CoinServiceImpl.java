package com.blockchain.server.cct.service.impl;

import com.blockchain.server.cct.common.constant.CCTConstant;
import com.blockchain.server.cct.common.enums.CCTEnums;
import com.blockchain.server.cct.common.exception.CCTException;
import com.blockchain.server.cct.dto.coin.CoinParamDTO;
import com.blockchain.server.cct.entity.Coin;
import com.blockchain.server.cct.entity.CoinLog;
import com.blockchain.server.cct.feign.CurrencyFeign;
import com.blockchain.server.cct.mapper.CoinMapper;
import com.blockchain.server.cct.service.CoinLogService;
import com.blockchain.server.cct.service.CoinService;
import com.codingapi.tx.annotation.TxTransaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CoinServiceImpl implements CoinService {

    @Autowired
    private CoinMapper coinMapper;
    @Autowired
    private CoinLogService logService;
    @Autowired
    private CurrencyFeign currencyFeign;

    @Override
    public Coin selectCoin(String coinName, String unitName, String status) {
        return coinMapper.selectByCoinUnitAndStatus(coinName, unitName, status);
    }

    @Override
    public List<Coin> listCoin(String coinName, String unitName, String status) {
        return coinMapper.listCoin(coinName, unitName, status);
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public int updateCoin(CoinParamDTO param) {
        Coin coin = selectCoin(param.getCoinName(), param.getUnitName(), "");
        //防空
        if (coin == null) {
            throw new CCTException(CCTEnums.COIN_NULL);
        }
        //插入币对数据日志
        insertCoinLog(param, checkUpdateLogType(param.getStatus()));

        //更新行情币对
        currencyFeign.updateCurrencyPair(param.getCoinName(), param.getUnitName(), param.getStatus());

        //更新币对
        return updateCoin(coin, param);
    }

    @Override
    @Transactional
    public int updateCoinOfCurrency(String coinName, String unitName, String status, String sysUserId, String ipAddr) {
        //查询币种
        Coin coin = selectCoin(coinName, unitName, "");
        //币种不为空
        if (coin != null) {
            coin.setStatus(status);
            coin.setModifyTime(new Date());

            //新增更新日志
            CoinLog log = new CoinLog();
            log.setId(UUID.randomUUID().toString());
            log.setIpAddress(ipAddr);
            log.setSysUserId(sysUserId);
            log.setCoinName(coinName);
            log.setUnitName(unitName);
            log.setCreateTime(new Date());
            coin.setStatus(status);
            log.setType(checkUpdateLogType(status));
            logService.insertCoinLog(log);

            //更新币币模块币种
            return coinMapper.updateByPrimaryKeySelective(coin);
        }
        return 0;
    }

    @Override
    @Transactional
    public int insertCoin(CoinParamDTO param) {
        Coin coin = new Coin();
        Date now = new Date();
        coin.setId(UUID.randomUUID().toString());
        coin.setCoinName(param.getCoinName());
        coin.setCoinNet(param.getCoinNet());
        coin.setCoinDecimals(param.getCoinDecimals());
        coin.setUnitName(param.getUnitName());
        coin.setUnitNet(param.getUnitNet());
        coin.setUnitDecimals(param.getUnitDecimals());
        coin.setRank(param.getRank());
        coin.setTag(param.getTag());
        coin.setStatus(param.getStatus());
        coin.setModifyTime(now);
        coin.setCreateTime(now);

        //插入操作日志
        insertCoinLog(param, CCTConstant.TYPE_ADD);
        return coinMapper.insertSelective(coin);
    }

    @Override
    public Coin selectByUnitAndGroupByUnit(String unitName) {
        return coinMapper.selectByUnitAndGroupByUnit(unitName);
    }

    /***
     * 更新币对
     * @param coin
     * @param param
     * @return
     */
    private int updateCoin(Coin coin, CoinParamDTO param) {
        if (StringUtils.isNotBlank(param.getCoinNet())) {
            coin.setCoinNet(param.getCoinNet());
        }
        if (StringUtils.isNotBlank(param.getUnitNet())) {
            coin.setUnitNet(param.getUnitNet());
        }
        if (StringUtils.isNotBlank(param.getTag())) {
            coin.setTag(param.getTag());
        }
        if (StringUtils.isNotBlank(param.getStatus())) {
            coin.setStatus(param.getStatus());
        }
        if (param.getCoinDecimals() != null) {
            coin.setCoinDecimals(param.getCoinDecimals());
        }
        if (param.getUnitDecimals() != null) {
            coin.setUnitDecimals(param.getUnitDecimals());
        }
        if (param.getRank() != null) {
            coin.setRank(param.getRank());
        }
        coin.setModifyTime(new Date());
        return coinMapper.updateByPrimaryKeySelective(coin);
    }

    /***
     * 检查更新交易对的操作日志类型（启用、禁用、更新）
     * @param updateCoinStauts
     * @return
     */
    private String checkUpdateLogType(String updateCoinStauts) {
        String logType = "";
        //设置操作状态
        if (StringUtils.isNotBlank(updateCoinStauts)) {
            //启用
            if (updateCoinStauts.equals(CCTConstant.STATUS_YES)) {
                logType = CCTConstant.TYPE_START;
            }
            //禁用
            if (updateCoinStauts.equals(CCTConstant.STATUS_NO)) {
                logType = CCTConstant.TYPE_DISABLE;
            }
        } else {
            logType = CCTConstant.TYPE_UPDATE;
        }

        return logType;
    }

    /***
     * 查询币对操作日志记录
     * @param param
     * @return
     */
    private int insertCoinLog(CoinParamDTO param, String logType) {
        CoinLog log = new CoinLog();
        log.setId(UUID.randomUUID().toString());
        log.setIpAddress(param.getIpAddr());
        log.setSysUserId(param.getSysUserId());
        log.setCoinName(param.getCoinName());
        log.setUnitName(param.getUnitName());
        log.setCoinNet(param.getCoinNet());
        log.setUnitNet(param.getUnitNet());
        log.setCoinDecimals(param.getCoinDecimals());
        log.setUnitDecimals(param.getUnitDecimals());
        log.setTag(param.getTag());
        log.setCreateTime(new Date());
        log.setType(logType);
        return logService.insertCoinLog(log);
    }

}
