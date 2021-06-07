package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.dto.tradingDetail.ListDetailResultDTO;
import com.blockchain.server.cct.dto.tradingDetail.SelectStatParamDTO;
import com.blockchain.server.cct.dto.tradingDetail.SelectStatResultDTO;
import com.blockchain.server.cct.entity.TradingDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * AppCctTradingDetailMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-03-06 11:53:27
 */
@Repository
public interface TradingDetailMapper extends Mapper<TradingDetail> {

    /***
     * 根据订单id查询成交详情列表
     * @param orderId
     * @return
     */
    List<ListDetailResultDTO> listDetailByOrderId(@Param("orderId") String orderId);

    /***
     * 统计用户交易数据
     * @param param
     * @return
     */
    SelectStatResultDTO selectStatByUserId(@Param("param") SelectStatParamDTO param);
}