package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.dto.AboutUsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.AboutUs;
import com.blockchain.server.sysconf.mapper.AboutUsMapper;
import com.blockchain.server.sysconf.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("aboutUsService")
public class AboutUsServiceImpl implements AboutUsService {


    @Autowired
    private AboutUsMapper aboutUsMapper;

    @Override
    @Transactional
    public Integer saveAboutUs(String content, String languages) {
        AboutUs aboutUs = new AboutUs();
        aboutUs.setId(UUID.randomUUID().toString());
        aboutUs.setContent(content);
        aboutUs.setLanguages(languages);
        aboutUs.setCreateTime(new Date());
        aboutUs.setModifyTime(new Date());

        return aboutUsMapper.insert(aboutUs);

    }

    @Override
    @Transactional
    public Integer updateAboutUs(String id, String content, String languages) {
       return aboutUsMapper.updateAboutUs(id,content,languages,new Date());

    }

    @Override
    public List<AboutUs> listAll() {
        return aboutUsMapper.listAllOrderByCreateTimeDesc();
    }

    @Override
    public AboutUs findNewestAboutUs(String languages) {
        return aboutUsMapper.findNewestAboutUs(languages);
    }

    @Override
    @Transactional
    public void deleteAboutUsById(String id) {
        aboutUsMapper.deleteAboutUsById(id);
    }

    /**
     * 查询关于我们信息
     *
     * @param id 关于我们ID
     * @return 关于我们信息
     */
    @Override
    public AboutUs selectAboutUsById(String id)
    {
        return aboutUsMapper.selectAboutUsById(id);
    }

    /**
     * 查询关于我们列表
     *
     * @param aboutUsQueryConditionDTO 关于我们信息
     * @return 关于我们集合
     */
    @Override
    public List<AboutUs> selectAboutUsList(AboutUsQueryConditionDTO aboutUsQueryConditionDTO)
    {
        return aboutUsMapper.selectAboutUsList(aboutUsQueryConditionDTO);
    }

}
