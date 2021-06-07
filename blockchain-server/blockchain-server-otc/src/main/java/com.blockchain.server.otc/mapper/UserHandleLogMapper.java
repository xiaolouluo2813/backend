package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.userhandlelog.ListUserHandleLogResultDTO;
import com.blockchain.server.otc.entity.UserHandleLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * UserHandleLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Repository
public interface UserHandleLogMapper extends Mapper<UserHandleLog> {
    /***
     * 根据参数查询
     * @param userId
     * @param handleNumber
     * @param handleNumberType
     * @param handleType
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListUserHandleLogResultDTO> listUserHandleLog(@Param("userId") String userId, @Param("handleNumber") String handleNumber,
                                                       @Param("handleNumberType") String handleNumberType, @Param("handleType") String handleType,
                                                       @Param("beginTime") String beginTime, @Param("endTime") String endTime);
}