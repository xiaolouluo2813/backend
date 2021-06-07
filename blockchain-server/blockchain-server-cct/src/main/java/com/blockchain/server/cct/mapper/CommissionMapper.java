package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.dto.commission.ListCommissionResultDTO;
import com.blockchain.server.cct.entity.Commission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * CommissionMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-07-18 14:28:26
 */
@Repository
public interface CommissionMapper extends Mapper<Commission> {

    /***
     * 查询佣金列表
     * @param userId
     * @param pid
     * @param coinName
     * @param status
     * @return
     */
    List<ListCommissionResultDTO> list(@Param("userId") String userId,
                                       @Param("pid") String pid,
                                       @Param("coinName") String coinName,
                                       @Param("status") String status);

    /***
     * 根据状态查询
     * @param status
     * @param offest
     * @param rowConut
     * @return
     */
    List<String> listByStatus(@Param("status") String status,
                              @Param("offest") Integer offest,
                              @Param("rowConut") Integer rowConut);

    /***
     * 排他锁查询
     * @param id
     * @return
     */
    Commission selectByIdForUpdate(@Param("id") String id);
}