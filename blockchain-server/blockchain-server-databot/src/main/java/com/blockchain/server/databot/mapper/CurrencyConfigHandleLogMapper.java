package com.blockchain.server.databot.mapper;

import com.blockchain.server.databot.dto.currencyconfighandlelog.ListConfigHandleLogResultDTO;
import com.blockchain.server.databot.entity.CurrencyConfigHandleLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * CurrencyConfigHandleLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-06-04 10:03:37
 */
@Repository
public interface CurrencyConfigHandleLogMapper extends Mapper<CurrencyConfigHandleLog> {

    /***
     * 查询操作日志列表
     * @param currencyPair
     * @param handleType
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListConfigHandleLogResultDTO> listConfigHandleLog(@Param("currencyPair") String currencyPair,
                                                           @Param("handleType") String handleType,
                                                           @Param("beginTime") String beginTime,
                                                           @Param("endTime") String endTime);
}