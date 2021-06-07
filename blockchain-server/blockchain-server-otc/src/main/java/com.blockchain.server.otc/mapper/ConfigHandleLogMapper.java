package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.confighandlelog.ListConfigHandleLogResultDTO;
import com.blockchain.server.otc.entity.ConfigHandleLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * ConfigHandleLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Repository
public interface ConfigHandleLogMapper extends Mapper<ConfigHandleLog> {

    /***
     * 查询配置操作日志列表
     * @param dataKey
     * @return
     */
    List<ListConfigHandleLogResultDTO> listConfigHandleLog(@Param("dataKey") String dataKey, @Param("beginTime") String beginTime,
                                                           @Param("endTime") String endTime);
}