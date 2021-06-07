package com.blockchain.server.btc.mapper;

import com.blockchain.server.btc.entity.BtcWalletBlockedDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author huangxl
 * @create 2019-06-25 11:25
 */
@Repository
public interface BtcWalletBlockedDetailMapper extends Mapper<BtcWalletBlockedDetail> {
    List<BtcWalletBlockedDetail> listByParams(@Param("userOpenId") String userOpenId,@Param("type") String type);
}
