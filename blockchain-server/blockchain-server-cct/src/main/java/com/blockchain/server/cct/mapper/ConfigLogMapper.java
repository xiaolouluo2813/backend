package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.entity.ConfigLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * PcCctConfigLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-03-06 11:53:27
 */
@Repository
public interface ConfigLogMapper extends Mapper<ConfigLog> {

    /***
     * 查询所有信息配置记录
     * 时间倒序
     * @return
     */
    List<ConfigLog> listConfigLogOrderByTimeDESC();
}