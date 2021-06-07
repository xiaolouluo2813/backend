package com.blockchain.server.eos.mapper;

import com.blockchain.server.eos.dto.WalletOutDTO;
import com.blockchain.server.eos.entity.WalletOut;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-26 17:41
 **/
@Repository
public interface WalletOutMapper extends Mapper<WalletOut> {
    List<WalletOutDTO> listByStatus(@Param("status") String status);
}
