package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.config.ListConfigResultDTO;
import com.blockchain.server.otc.entity.Config;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * ConfigMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Repository
public interface ConfigMapper extends Mapper<Config> {

    /***
     * 查询配置信息列表
     * @return
     */
    List<ListConfigResultDTO> listConfig();

    /***
     * 根据key查询配置信息列表
     * @param dataKeys
     * @return
     */
    List<ListConfigResultDTO> listConfigByKeys(@Param("dataKeys") String[] dataKeys);

    /***
     * 根据key查询配置信息
     * @param key
     * @return
     */
    Config selectByKey(@Param("key") String key);
}