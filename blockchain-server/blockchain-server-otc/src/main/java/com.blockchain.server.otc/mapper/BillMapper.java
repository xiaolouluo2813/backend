package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.bill.ListBillResultDTO;
import com.blockchain.server.otc.entity.Bill;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * BillMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Repository
public interface BillMapper extends Mapper<Bill> {

    /***
     * 查询用户资金对账记录
     * @param userId
     * @return
     */
    List<ListBillResultDTO> listBill(@Param("userId") String userId, @Param("beginTime") String beginTime,
                                     @Param("endTime") String endTime);
}