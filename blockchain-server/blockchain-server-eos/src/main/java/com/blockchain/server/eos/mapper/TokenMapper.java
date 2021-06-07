package com.blockchain.server.eos.mapper;

import com.blockchain.server.eos.entity.Token;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.HashSet;

/**
 * @author Harvey
 * @date 2019/2/18 11:13
 * @user WIN10
 */
@Repository
public interface TokenMapper extends Mapper<Token> {
}
