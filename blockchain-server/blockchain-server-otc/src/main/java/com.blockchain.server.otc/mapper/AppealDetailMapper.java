package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.appealdetail.ListAppealDetailResultDTO;
import com.blockchain.server.otc.entity.AppealDetail;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * AppealDetailMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:21
 */
@Repository
public interface AppealDetailMapper extends Mapper<AppealDetail> {
    List<ListAppealDetailResultDTO> listAppealDetailByAppealId(@Param("appealId") String appealId);
}