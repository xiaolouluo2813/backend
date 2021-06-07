package com.blockchain.server.btc.mapper;

import com.blockchain.server.btc.entity.BtcWalletBlockedTotal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huangxl
 * @create 2019-06-25 11:25
 */
@Repository
public interface BtcWalletBlockedTotalMapper extends Mapper<BtcWalletBlockedTotal> {
    int updateTotalByIdInRowLock(@Param("id") String id, @Param("optNumber")BigDecimal optNumber, @Param("now")Date now);
}
