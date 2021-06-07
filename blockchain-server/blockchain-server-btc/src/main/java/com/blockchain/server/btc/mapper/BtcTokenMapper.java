package com.blockchain.server.btc.mapper;

import com.blockchain.server.btc.entity.BtcToken;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * BtcTokenMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Repository
public interface BtcTokenMapper extends Mapper<BtcToken> {

}