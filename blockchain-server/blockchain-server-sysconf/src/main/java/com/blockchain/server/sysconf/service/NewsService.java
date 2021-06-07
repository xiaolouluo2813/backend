package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.dto.NewsDTO;
import com.blockchain.server.sysconf.dto.NewsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.News;

import java.util.Date;
import java.util.List;

public interface NewsService {

    /**
     * 新增文章
     * @param userId 发布人ID
     * @param title 文章标题
     * @param type  文章类型
     * @param content 文章内容
     * @param url 原文URL
     * @param languages 语种
     */
    void insertNews(String userId, String title, Integer type, String content, String url, String languages);

    /**
     * 修改文章
     * @param title 标题
     * @param content 文章内容
     * @param url 原文URL
     */
    Integer updateNews(String newsId, String title, String content, String url);

    List<NewsDTO> listNews(Integer type, String languages, Date beginTime, Date endTime);

    List<News> listAll(Integer type);

    News getNewsById(String id);

    /**
     * 新增公告/快讯
     *
     * @param news 公告/快讯信息
     * @return 结果
     */
    Integer insertNewsForBackend(News news);

    /**
     * 删除公告/快讯信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNewsByIds(String ids);

}
