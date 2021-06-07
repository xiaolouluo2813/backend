package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.common.constant.UserConstant;
import com.blockchain.server.sysconf.dto.NewsDTO;
import com.blockchain.server.sysconf.dto.NewsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.News;
import com.blockchain.server.sysconf.mapper.NewsMapper;
import com.blockchain.server.sysconf.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class NewsServiceImpl implements NewsService {

    private NewsMapper newsMapper;

    @Autowired
    public void setNewsMapper(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
    }

    @Override
    @Transactional
    public void insertNews(String userId, String title, Integer type, String content, String url, String languages) {

        News news = new News();
        news.setId(UUID.randomUUID().toString());
        news.setUserId(userId);
        news.setTitle(title);
        news.setType(type);
        news.setContent(content);
        news.setUrl(url);
        news.setLanguages(languages);
        news.setCreateTime(new Date());
        news.setModifyTime(news.getCreateTime());
        newsMapper.insert(news);
    }

    @Override
    @Transactional
    public Integer updateNews(String newsId, String title, String content, String url) {

        News news = newsMapper.selectByPrimaryKey(newsId);
        news.setId(newsId);
        news.setTitle(title);
        news.setContent(content);
        news.setUrl(url);
        news.setModifyTime(new Date());
        return newsMapper.updateByPrimaryKeySelective(news);
    }

    @Override
    public List<NewsDTO> listNews(Integer type, String languages, Date beginTime, Date endTime) {
        NewsQueryConditionDTO condition = new NewsQueryConditionDTO();
        condition.setType(type);
        if (StringUtils.isEmpty(languages)) {
            condition.setLanguages(UserConstant.USER_LOCAL_CHINA);
        }else{
            condition.setLanguages(languages);
        }
        condition.setBeginTime(beginTime);
        condition.setEndTime(endTime);
        List<NewsDTO> news = newsMapper.listNewsDTO(condition);
        return news;
    }

    @Override
    public List<News> listAll(Integer type) {
        NewsQueryConditionDTO condition = new NewsQueryConditionDTO();
        condition.setType(type);
        return newsMapper.listAll(condition);
    }

    @Override
    public News getNewsById(String id) {
        return newsMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public Integer insertNewsForBackend(News news) {
        news.setId(UUID.randomUUID().toString());
        news.setCreateTime(new Date());
        news.setModifyTime(news.getCreateTime());
        return newsMapper.insertSelective(news);
    }

    /**
     * 删除公告/快讯对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNewsByIds(String ids)
    {
        return newsMapper.deleteNewsByIds(ids.split(","));
    }

}
