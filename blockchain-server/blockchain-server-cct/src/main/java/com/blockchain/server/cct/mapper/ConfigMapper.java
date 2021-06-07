package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.entity.Config;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * AppCctConfigMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-03-06 11:53:27
 */
@Repository
public interface ConfigMapper extends Mapper<Config> {

    /**
     * 根据key查询配置信息
     *
     * @param key
     * @return
     */
    Config selectByKey(@Param("key") String key);
}