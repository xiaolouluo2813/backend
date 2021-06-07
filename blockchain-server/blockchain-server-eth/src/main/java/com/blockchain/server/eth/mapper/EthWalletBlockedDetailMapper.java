package com.blockchain.server.eth.mapper;

import com.blockchain.server.eth.entity.EthWalletBlockedDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author huangxl
 * @create 2019-06-25 11:25
 */
@Repository
public interface EthWalletBlockedDetailMapper extends Mapper<EthWalletBlockedDetail> {
    List<EthWalletBlockedDetail> listByParams(@Param("userOpenId") String userOpenId, @Param("type") String type);
}
