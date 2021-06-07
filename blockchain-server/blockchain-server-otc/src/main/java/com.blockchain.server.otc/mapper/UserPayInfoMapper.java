package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.userpayinfo.ListUserPayInfoResultDTO;
import com.blockchain.server.otc.entity.UserPayInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * UserPayInfoMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:23
 */
@Repository
public interface UserPayInfoMapper extends Mapper<UserPayInfo> {

    /***
     * 查询用户支付信息列表
     * @param userId
     * @param payType
     * @return
     */
    List<ListUserPayInfoResultDTO> listUserPayInfo(@Param("userId") String userId, @Param("payType") String payType);
}