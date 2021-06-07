package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.marketuser.ListMarketUserResultDTO;
import com.blockchain.server.otc.entity.MarketUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * MarketUserMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-07-13 11:48:11
 */
@Repository
public interface MarketUserMapper extends Mapper<MarketUser> {

    /***
     * 根据参数查询市商用户列表
     * @param userId
     * @param status
     * @return
     */
    List<ListMarketUserResultDTO> list(@Param("userId") String userId, @Param("status") String status);

    /***
     * 根据用户查询
     * @param userId
     * @return
     */
    MarketUser selectByUser(@Param("userId") String userId);

    /***
     * 根据id查询，使用排他锁
     * @param id
     * @return
     */
    MarketUser selectByIdForUpdate(@Param("id") String id);
}