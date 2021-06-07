package com.blockchain.server.sysconf.service;


import com.blockchain.server.sysconf.dto.AboutUsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.AboutUs;

import java.util.List;

public interface AboutUsService {
    /**
     * 保存关于我们（新建保存）
     * @param content
     * @param languages
     */
    Integer saveAboutUs(String content, String languages);

    /**
     * 更新关于我们信息(编辑)
     * @param id
     * @param content
     * @param languages
     */
    Integer updateAboutUs(String id, String content, String languages);

    /**
     * 查询关于我们信息列表 按照创建时间倒叙（后台）
     * @return
     */
    List<AboutUs> listAll();

    /**
     * 查询关于我们信息 (客户端)
     * @param languages
     * @return
     */
    AboutUs findNewestAboutUs(String languages);

    /**
     * 根据id删除关于我们信息
     * @param id
     */
    void deleteAboutUsById(String id);

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
