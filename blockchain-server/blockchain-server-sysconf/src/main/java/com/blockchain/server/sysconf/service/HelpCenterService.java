package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.entity.HelpCenter;

import java.util.List;

/**
 * 帮助中心 服务层
 * 
 * @author ruoyi
 * @date 2018-10-30
 */
public interface HelpCenterService
{
	/**
     * 查询帮助中心信息
     * 
     * @param id 帮助中心ID
     * @return 帮助中心信息
     */
	HelpCenter selectHelpCenterById(String id);
	
	/**
     * 查询帮助中心列表
     * 
     * @param helpCenter 帮助中心信息
     * @return 帮助中心集合
     */
	List<HelpCenter> selectHelpCenterList(HelpCenter helpCenter);
	
	/**
     * 新增帮助中心
     * 
     * @param helpCenter 帮助中心信息
     * @return 结果
     */
	int insertHelpCenter(HelpCenter helpCenter);
	
	/**
     * 修改帮助中心
     * 
     * @param helpCenter 帮助中心信息
     * @return 结果
     */
	int updateHelpCenter(HelpCenter helpCenter);
		
	/**
     * 删除帮助中心信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	int deleteHelpCenterByIds(String ids);


	List<HelpCenter> selectHelpCenterForApp(String userLocal);

	String selectContentById(String id);
	
}
