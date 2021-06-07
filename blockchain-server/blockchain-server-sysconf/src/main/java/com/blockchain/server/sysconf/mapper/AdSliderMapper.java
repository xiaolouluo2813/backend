package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.dto.AdSliderDto;
import com.blockchain.server.sysconf.entity.AdSlider;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 首页广告轮播 数据层
 *
 * @author ruoyi
 * @date 2018-09-03
 */
@Repository
public interface AdSliderMapper extends Mapper<AdSlider> {

    /**
     * 获取首页轮播广告图片
     *
     * @return
     */
    List<AdSliderDto> listAdSliderForApp();

    /**
     * 查询首页广告轮播信息
     *
     * @param id 首页广告轮播ID
     * @return 首页广告轮播信息
     */
    AdSliderDto selectAdSliderById(Integer id);

    /**
     * 查询首页广告轮播列表
     *
     * @param adSlider 首页广告轮播信息
     * @return 首页广告轮播集合
     */
    List<AdSliderDto> selectAdSliderList(AdSliderDto adSlider);

    /**
     * 新增首页广告轮播
     *
     * @param adSlider 首页广告轮播信息
     * @return 结果
     */
    int insertAdSlider(AdSliderDto adSlider);

    /**
     * 修改首页广告轮播
     *
     * @param adSlider 首页广告轮播信息
     * @return 结果
     */
    int updateAdSlider(AdSliderDto adSlider);

    /**
     * 删除首页广告轮播
     *
     * @param id 首页广告轮播ID
     * @return 结果
     */
    int deleteAdSliderById(Integer id);

    /**
     * 批量删除首页广告轮播
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteAdSliderByIds(String[] ids);

}