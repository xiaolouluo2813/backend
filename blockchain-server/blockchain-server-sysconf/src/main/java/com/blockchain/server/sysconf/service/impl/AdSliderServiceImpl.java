package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.dto.AdSliderDto;
import com.blockchain.server.sysconf.mapper.AdSliderMapper;
import com.blockchain.server.sysconf.service.IAdSliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页广告轮播 服务层实现
 *
 * @author ruoyi
 * @date 2018-09-03
 */
@Service
public class AdSliderServiceImpl implements IAdSliderService {
    @Autowired
    private AdSliderMapper adSliderMapper;

    /**
     * 查询首页广告轮播信息
     *
     * @param id 首页广告轮播ID
     * @return 首页广告轮播信息
     */
    @Override
    public AdSliderDto selectAdSliderById(Integer id) {
        return adSliderMapper.selectAdSliderById(id);
    }

    /**
     * 查询首页广告轮播列表
     *
     * @param adSlider 首页广告轮播信息
     * @return 首页广告轮播集合
     */
    @Override
    public List<AdSliderDto> selectAdSliderList(AdSliderDto adSlider) {
        return adSliderMapper.selectAdSliderList(adSlider);
    }

    /**
     * 新增首页广告轮播
     *
     * @param adSlider 首页广告轮播信息
     * @return 结果
     */
    @Override
    public int insertAdSlider(AdSliderDto adSlider) {
        return adSliderMapper.insertAdSlider(adSlider);
    }

    /**
     * 修改首页广告轮播
     *
     * @param adSlider 首页广告轮播信息
     * @return 结果
     */
    @Override
    public int updateAdSlider(AdSliderDto adSlider) {
        return adSliderMapper.updateAdSlider(adSlider);
    }

    /**
     * 删除首页广告轮播对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAdSliderByIds(String ids) {
        return adSliderMapper.deleteAdSliderByIds(ids.split(","));
    }

}
