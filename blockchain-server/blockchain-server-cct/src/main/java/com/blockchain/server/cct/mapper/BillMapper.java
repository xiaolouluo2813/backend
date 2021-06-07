package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.entity.Bill;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * AppCctBillMapper 数据访问类
 * @date 2019-03-06 11:53:27
 * @version 1.0
 */
@Repository
public interface BillMapper extends Mapper<Bill> {
}