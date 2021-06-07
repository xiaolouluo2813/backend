package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.UserLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Harvey Luo
 * @date 2019/6/16 13:55
 */
@Repository
public interface UserLogMapper extends Mapper<UserLog> {
}
