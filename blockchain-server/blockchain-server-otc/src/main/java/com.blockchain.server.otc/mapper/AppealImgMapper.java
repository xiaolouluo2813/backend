package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.appealimg.ListAppealImgResultDTO;
import com.blockchain.server.otc.entity.AppealImg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * AppealImgMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Repository
public interface AppealImgMapper extends Mapper<AppealImg> {

    /***
     * 根据申诉详情记录id查询申诉图片
     * @param appealDetailId
     * @return
     */
    List<ListAppealImgResultDTO> listAppealImgByAppealDetailId(@Param("appealDetailId") String appealDetailId);
}