package com.blockchain.server.eos.mapper;

import com.blockchain.server.eos.entity.BlockNumber;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigInteger;
import java.util.List;

/**
 * BlockNumberMapper 数据访问类
 *
 * @version 1.0
 * @date 2018-11-05 15:10:47
 */
@Repository
public interface BlockNumberMapper extends Mapper<BlockNumber> {
}