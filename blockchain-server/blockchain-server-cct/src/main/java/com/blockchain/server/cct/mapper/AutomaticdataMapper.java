package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.entity.Automaticdata;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * AutomaticdataMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-04-08 13:55:48
 */
@Repository
public interface AutomaticdataMapper extends Mapper<Automaticdata> {
    /***
     * 根据基本货币、二级货币、订单类型查询盘口规则
     * @param coinName
     * @param unitName
     * @param orderType
     * @return
     */
    Automaticdata selectByCoinAndUnitAndType(@Param("coinName") String coinName, @Param("unitName") String unitName, @Param("orderType") String orderType);
}