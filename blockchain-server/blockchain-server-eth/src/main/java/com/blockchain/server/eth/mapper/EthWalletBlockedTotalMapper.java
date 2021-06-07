package com.blockchain.server.eth.mapper;

import com.blockchain.server.eth.entity.EthWalletBlockedTotal;
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
public interface EthWalletBlockedTotalMapper extends Mapper<EthWalletBlockedTotal> {
    int updateTotalByIdInRowLock(@Param("id") String id, @Param("optNumber") BigDecimal optNumber, @Param("now") Date now);
}
