package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.entity.HelpCenter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 帮助中心 数据层
 *
 * @author ruoyi
 * @date 2018-10-30
 */
@Repository
public interface HelpCenterMapper extends Mapper<HelpCenter> {
    /**
     * 查询帮助中心信息
     *
     * @param id 帮助中心ID
     * @return 帮助中心信息
     */
    public HelpCenter selectHelpCenterById(String id);

    /**
     * 查询帮助中心列表
     *
     * @param helpCenter 帮助中心信息
     * @return 帮助中心集合
     */
    public List<HelpCenter> selectHelpCenterList(HelpCenter helpCenter);

    /**
     * 新增帮助中心
     *
     * @param helpCenter 帮助中心信息
     * @return 结果
     */
    public int insertHelpCenter(HelpCenter helpCenter);

    /**
     * 修改帮助中心
     *
     * @param helpCenter 帮助中心信息
     * @return 结果
     */
    public int updateHelpCenter(HelpCenter helpCenter);

    /**
     * 删除帮助中心
     *
     * @param id 帮助中心ID
     * @return 结果
     */
    public int deleteHelpCenterById(String id);

    /**
     * 批量删除帮助中心
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHelpCenterByIds(String[] ids);


    List<HelpCenter> selectHelpCenterForApp(@Param("showStatus") Integer showStatus, @Param("userLocal") String userLocal);

    String selectContentById(String id);
}