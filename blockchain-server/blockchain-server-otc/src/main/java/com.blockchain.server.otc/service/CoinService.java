package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.coin.ListCoinResultDTO;
import com.blockchain.server.otc.dto.coin.UpdateCoinParamDTO;
import com.blockchain.server.otc.entity.Coin;

import java.math.BigDecimal;
import java.util.List;

public interface CoinService {

    /***
     * 根据币种、单位查询币种信息
     * @param coinName
     * @return
     */
    Coin selectByCoinAndUnit(String coinName, String unitName);

    /***
     * 查询币种列表
     * @param coinName
     * @param unitName
     * @return
     */
    List<ListCoinResultDTO> listCoin(String coinName, String unitName);

    /**
     * 更新币种
     *
     * @param paramDTO
     * @return
     */
    int updateCoin(UpdateCoinParamDTO paramDTO);
}
