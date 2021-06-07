package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.dto.NewsDTO;
import com.blockchain.server.sysconf.dto.NewsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.News;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Repository
public interface NewsMapper extends BaseMapper<News> {
    /**
     * 用于前端展示
     * @param condition 查询条件
     * @return
     */
    List<NewsDTO> listNewsDTO(NewsQueryConditionDTO condition);

    List<News> listAll(NewsQueryConditionDTO condition);

    /**
     * 批量删除公告/快讯
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNewsByIds(String[] ids);

}
