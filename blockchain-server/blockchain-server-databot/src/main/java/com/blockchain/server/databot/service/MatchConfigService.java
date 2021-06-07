package com.blockchain.server.databot.service;

import com.blockchain.server.databot.dto.matchconfig.InsertMatchConfigParamDTO;
import com.blockchain.server.databot.dto.matchconfig.ListMatchConfigResultDTO;
import com.blockchain.server.databot.dto.matchconfig.UpdateMatchConfigParamDTO;

import java.util.List;

public interface MatchConfigService {

    /***
     * 查询撮合机器人配置列表
     * @param userName
     * @param coinName
     * @param unitName
     * @param status
     * @return
     */
    List<ListMatchConfigResultDTO> listConfig(String userName, String coinName,
                                              String unitName, String status,
                                              String priceType);

    /***
     * 新增撮合机器人配置
     * @param sysUserId
     * @param ipAddr
     * @param paramDTO
     * @return
     */
    int insertConfig(String sysUserId, String ipAddr, InsertMatchConfigParamDTO paramDTO);

    /***
     * 更新撮合机器人配置
     * @param sysUserId
     * @param ipAddr
     * @param paramDTO
     * @return
     */
    int updateConfig(String sysUserId, String ipAddr, UpdateMatchConfigParamDTO paramDTO);

    /***
     * 删除撮合机器人配置
     * @param sysUserId
     * @param ipAddr
     * @param id
     * @return
     */
    int deleteConfig(String sysUserId, String ipAddr, String id);
}
