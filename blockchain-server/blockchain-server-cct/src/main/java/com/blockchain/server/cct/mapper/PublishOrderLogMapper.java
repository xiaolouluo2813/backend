package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.entity.PublishOrderLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * CctPublishOrderLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-03-06 14:49:01
 */
@Repository
public interface PublishOrderLogMapper extends Mapper<PublishOrderLog> {

    /***
     * 查询所有订单操作记录
     * 时间倒序
     * @return
     */
    List<PublishOrderLog> listAllOrderByTimeDESC();
}