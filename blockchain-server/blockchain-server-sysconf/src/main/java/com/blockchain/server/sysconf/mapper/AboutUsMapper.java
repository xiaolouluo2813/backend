package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.dto.AboutUsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.AboutUs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

@Repository
public interface AboutUsMapper extends Mapper<AboutUs> {

    Integer updateAboutUs(@Param("id") String id, @Param("content") String content, @Param("languages") String languages, @Param("modifyTime") Date modifyTime);

    List<AboutUs> listAllOrderByCreateTimeDesc();

    AboutUs findNewestAboutUs(@Param("languages") String languages);

    void deleteAboutUsById(@Param("id") String id);

    /**
     * 查询关于我们信息
     *
     * @param id 关于我们ID
     * @return 关于我们信息
     */
    AboutUs selectAboutUsById(String id);

    /**
     * 查询关于我们列表
     *
     * @param aboutUsQueryConditionDTO 关于我们信息
     * @return 关于我们集合
     */
    List<AboutUs> selectAboutUsList(AboutUsQueryConditionDTO aboutUsQueryConditionDTO);
}
