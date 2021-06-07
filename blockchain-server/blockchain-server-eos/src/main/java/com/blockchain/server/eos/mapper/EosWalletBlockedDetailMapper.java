package com.blockchain.server.eos.mapper;

import com.blockchain.server.eos.entity.EosWalletBlockedDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author huangxl
 * @create 2019-06-25 11:25
 */
@Repository
public interface EosWalletBlockedDetailMapper extends Mapper<EosWalletBlockedDetail> {
    List<EosWalletBlockedDetail> listByParams(@Param("userOpenId") String userOpenId, @Param("type") String type);
}
