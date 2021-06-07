package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.dto.tradingRecord.ListRecordParamDTO;
import com.blockchain.server.cct.dto.tradingRecord.ListRecordResultDTO;
import com.blockchain.server.cct.entity.TradingRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * AppCctTradingRecordMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-03-06 11:53:27
 */
@Repository
public interface TradingRecordMapper extends Mapper<TradingRecord> {

    /***
     * 查询成交记录
     * @param orderId
     * @return
     */
    List<ListRecordResultDTO> listRecord(@Param("orderId") String orderId);

    /***
     * 根据id查询
     * @param id
     * @return
     */
    ListRecordResultDTO selectById(@Param("id") String id);
}