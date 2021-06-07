package com.blockchain.server.databot.mapper;

import com.blockchain.server.databot.dto.matchconfig.ListMatchConfigResultDTO;
import com.blockchain.server.databot.dto.matchconfig.UpdateMatchConfigParamDTO;
import com.blockchain.server.databot.entity.MatchConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * MatchConfigMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-06-26 15:57:36
 */
@Repository
public interface MatchConfigMapper extends Mapper<MatchConfig> {

    /***
     * 查询撮合机器人配置列表
     * @param userId
     * @param coinName
     * @param unitName
     * @param status
     * @param priceType
     * @return
     */
    List<ListMatchConfigResultDTO> listConfig(@Param("userId") String userId, @Param("coinName") String coinName,
                                              @Param("unitName") String unitName, @Param("status") String status,
                                              @Param("priceType") String priceType);

    /***
     * 根据基本货币和二级货币查询
     * @param coinName
     * @param unitName
     * @return
     */
    MatchConfig selectByCoinAndUnit(@Param("coinName") String coinName, @Param("unitName") String unitName);

    /***
     * 更新撮合配置
     * @param paramDTO
     * @param modifyTime
     * @return
     */
    int updateConfig(@Param("paramDTO") UpdateMatchConfigParamDTO paramDTO, @Param("modifyTime") Date modifyTime);
}