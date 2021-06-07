package com.blockchain.server.cct.service;

import com.blockchain.server.cct.dto.coin.CoinParamDTO;
import com.blockchain.server.cct.entity.Coin;

import java.util.List;

public interface CoinService {
    /***
     * 查询交易对信息
     * @param coinName
     * @param unitName
     * @param status
     * @return
     */
    Coin selectCoin(String coinName, String unitName, String status);

    /***
     * 查询交易对列表
     * @param coinName
     * @param unitName
     * @param status
     * @return
     */
    List<Coin> listCoin(String coinName, String unitName, String status);

    /***
     * 更新交易对信息
     * @param param
     * @return
     */
    int updateCoin(CoinParamDTO param);

    /***
     * 用于行情更新时，同步币币币对的数据
     * @param coinName
     * @param unitName
     * @param status
     * @param sysUserId
     * @param ipAddr
     * @return
     */
    int updateCoinOfCurrency(String coinName, String unitName, String status, String sysUserId, String ipAddr);

    /***
     * 插入交易对信息
     * @param param
     * @return
     */
    @Deprecated
    int insertCoin(CoinParamDTO param);

    /***
     * 根据二级货币查询，并以二级货币分组
     * @param unitName
     * @return
     */
    Coin selectByUnitAndGroupByUnit(String unitName);
}
