package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.dto.coin.ListCoinResultDTO;
import com.blockchain.server.otc.dto.coin.UpdateCoinParamDTO;
import com.blockchain.server.otc.dto.coinhandlelog.InsertCoinHandleLogParamDTO;
import com.blockchain.server.otc.entity.Coin;
import com.blockchain.server.otc.mapper.CoinMapper;
import com.blockchain.server.otc.service.CoinHandleLogService;
import com.blockchain.server.otc.service.CoinService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class CoinServiceImpl implements CoinService {

    @Autowired
    private CoinMapper coinMapper;
    @Autowired
    private CoinHandleLogService coinHandleLogService;

    @Override
    public Coin selectByCoinAndUnit(String coinName, String unitName) {
        return coinMapper.selectByCoinAndUnit(coinName, unitName);
    }

    @Override
    public List<ListCoinResultDTO> listCoin(String coinName, String unitName) {
        return coinMapper.listCoin(coinName, unitName);
    }

    @Override
    @Transactional
    public int updateCoin(UpdateCoinParamDTO paramDTO) {
        //查询币种
        Coin coin = coinMapper.selectByPrimaryKey(paramDTO.getId());
        //判空
        if (coin == null) {
            throw new OtcException(OtcEnums.COIN_NULL);
        }

        //新增币种操作日志数据类
        InsertCoinHandleLogParamDTO coinHandleLogDTO = new InsertCoinHandleLogParamDTO();
        coinHandleLogDTO.setBeforeCoinName(coin.getCoinName());
        coinHandleLogDTO.setBeforeUnitName(coin.getUnitName());

        //币种不为空 TODO 暂时不开放修改币种
        /*if (StringUtils.isNotBlank(paramDTO.getCoinName())) {
            //记录修改前后的值
            coinHandleLogDTO.setBeforeCoinName(coin.getCoinName());
            coinHandleLogDTO.setAfterCoinName(paramDTO.getCoinName());
            //设值
            coin.setCoinName(paramDTO.getCoinName());
        }*/
        //单位不为空 TODO 暂时不开放修改单位
        /*if (StringUtils.isNotBlank(paramDTO.getUnitName())) {
            //记录修改前后的值
            coinHandleLogDTO.setBeforeUnitName(coin.getUnitName());
            coinHandleLogDTO.setAfterUnitName(paramDTO.getUnitName());
            //设值
            coin.setUnitName(paramDTO.getUnitName());
        }*/

        //主网标识不为空
        if (StringUtils.isNotBlank(paramDTO.getCoinNet())) {
            //记录修改前后的值
            coinHandleLogDTO.setBeforeCoinNet(coin.getCoinNet());
            coinHandleLogDTO.setAfterCoinNet(paramDTO.getCoinNet());
            //设值
            coin.setCoinNet(paramDTO.getCoinNet());
        }
        //状态不为空
        if (StringUtils.isNotBlank(paramDTO.getStatus())) {
            //记录修改前后的值
            coinHandleLogDTO.setBeforeStatus(coin.getStatus());
            coinHandleLogDTO.setAfterStatus(paramDTO.getStatus());
            //设值
            coin.setStatus(paramDTO.getStatus());
        }
        //排序不为空并且大于0
        if (paramDTO.getRank() != null && paramDTO.getRank() > 0) {
            //设值
            coin.setRank(paramDTO.getRank());
        }
        //币种小数位不等于空并且大于等于0
        if (paramDTO.getCoinDecimal() != null && paramDTO.getCoinDecimal() >= 0) {
            //记录修改前后的值
            coinHandleLogDTO.setBeforeCoinDecimal(coin.getCoinDecimal());
            coinHandleLogDTO.setAfterCoinDecimal(paramDTO.getCoinDecimal());
            //设值
            coin.setCoinDecimal(paramDTO.getCoinDecimal());
        }
        //单位小数位不等于空并且大于等于0
        if (paramDTO.getUnitDecimal() != null && paramDTO.getUnitDecimal() >= 0) {
            //记录修改前后的值
            coinHandleLogDTO.setBeforeUnitDecimal(coin.getUnitDecimal());
            coinHandleLogDTO.setAfterUnitDecimal(paramDTO.getUnitDecimal());
            //设值
            coin.setUnitDecimal(paramDTO.getUnitDecimal());
        }
        //手续费不等于空并且大于等于0
        if (paramDTO.getCoinServiceCharge() != null && paramDTO.getCoinServiceCharge().compareTo(BigDecimal.ZERO) >= 0) {
            //记录修改前后的值
            coinHandleLogDTO.setBeforeCoinServiceCharge(coin.getCoinServiceCharge());
            coinHandleLogDTO.setAfterCoinServiceCharge(paramDTO.getCoinServiceCharge());
            //设值
            coin.setCoinServiceCharge(paramDTO.getCoinServiceCharge());
        }

        //新增币种操作日志记录
        coinHandleLogDTO.setCoinId(coin.getId());
        coinHandleLogDTO.setSysUserId(paramDTO.getSysUserId());
        coinHandleLogDTO.setIpAddress(paramDTO.getIpAddress());
        coinHandleLogService.insertCoinHandleLog(coinHandleLogDTO);

        //更新币种
        coin.setModifyTime(new Date());
        return coinMapper.updateByPrimaryKeySelective(coin);
    }
}
