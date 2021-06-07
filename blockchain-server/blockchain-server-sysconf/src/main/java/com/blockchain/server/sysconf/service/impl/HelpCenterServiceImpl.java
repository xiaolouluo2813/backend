package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.common.constant.ContactUsConstant;
import com.blockchain.server.sysconf.entity.HelpCenter;
import com.blockchain.server.sysconf.mapper.HelpCenterMapper;
import com.blockchain.server.sysconf.service.HelpCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 帮助中心 服务层实现
 *
 * @author ruoyi
 * @date 2018-10-30
 */
@Service
public class HelpCenterServiceImpl implements HelpCenterService
{
	@Autowired
	private HelpCenterMapper helpCenterMapper;

	/**
     * 查询帮助中心信息
     *
     * @param id 帮助中心ID
     * @return 帮助中心信息
     */
    @Override
	public HelpCenter selectHelpCenterById(String id)
	{
	    return helpCenterMapper.selectHelpCenterById(id);
	}

	/**
     * 查询帮助中心列表
     *
     * @param helpCenter 帮助中心信息
     * @return 帮助中心集合
     */
	@Override
	public List<HelpCenter> selectHelpCenterList(HelpCenter helpCenter)
	{
	    return helpCenterMapper.selectHelpCenterList(helpCenter);
	}

    /**
     * 新增帮助中心
     *
     * @param helpCenter 帮助中心信息
     * @return 结果
     */
	@Override
    @Transactional
	public int insertHelpCenter(HelpCenter helpCenter)
	{
		helpCenter.setId(UUID.randomUUID().toString());
		Date dateNow = new Date();
		helpCenter.setCreateTime(dateNow);
		helpCenter.setModifyTime(dateNow);
	    return helpCenterMapper.insertSelective(helpCenter);
	}

	/**
     * 修改帮助中心
     *
     * @param helpCenter 帮助中心信息
     * @return 结果
     */
	@Override
    @Transactional
	public int updateHelpCenter(HelpCenter helpCenter)
	{
	    return helpCenterMapper.updateHelpCenter(helpCenter);
	}

	/**
     * 删除帮助中心对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
    @Transactional
	public int deleteHelpCenterByIds(String ids)
	{
		return helpCenterMapper.deleteHelpCenterByIds(ids.split(","));
	}


	@Override
	public List<HelpCenter> selectHelpCenterForApp(String userLocal) {
		return helpCenterMapper.selectHelpCenterForApp(ContactUsConstant.STATUS_SHOW, userLocal);
	}

	@Override
	public String selectContentById(String id) {
		return helpCenterMapper.selectContentById(id);
	}
}
