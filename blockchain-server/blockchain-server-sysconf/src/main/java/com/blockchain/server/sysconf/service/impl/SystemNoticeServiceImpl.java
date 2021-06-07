package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.common.enums.SysconfResultEnums;
import com.blockchain.server.sysconf.common.exception.SysconfException;
import com.blockchain.server.sysconf.entity.SystemNotice;
import com.blockchain.server.sysconf.mapper.SystemNoticeMapper;
import com.blockchain.server.sysconf.service.SystemNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:55
 **/
@Service
public class SystemNoticeServiceImpl implements SystemNoticeService {

    @Autowired
    private SystemNoticeMapper systemNoticeMapper;

    @Override
    @Transactional
    public int updateStatus(String status, String id) {
        SystemNotice systemNotice = systemNoticeMapper.selectByPrimaryKey(id);
        if (systemNotice==null){
            throw new SysconfException(SysconfResultEnums.NOTICE_DOES_NOT_EXIST);
        }
        systemNotice.setStatus(status);
        systemNotice.setModifyTime(new Date());
        return systemNoticeMapper.updateByPrimaryKey(systemNotice);
    }

    @Override
    @Transactional
    public int deleteNotice(String id) {
        SystemNotice systemNotice = systemNoticeMapper.selectByPrimaryKey(id);
        if (systemNotice==null){
            throw new SysconfException(SysconfResultEnums.NOTICE_DOES_NOT_EXIST);
        }
        return systemNoticeMapper.delete(systemNotice);
    }

    @Override
    public List<SystemNotice> systemNoticeList(String status) {
        return systemNoticeMapper.selectByStatus(status);
    }

    @Override
    @Transactional
    public int insert(String title, String details, String jumpUrl, String status, Integer rank, String languages) {
        SystemNotice systemNotice = new SystemNotice();
        systemNotice.setId(UUID.randomUUID().toString());
        systemNotice.setTitle(title);
        systemNotice.setDetails(details);
        systemNotice.setJumpUrl(jumpUrl);
        systemNotice.setStatus(status);
        systemNotice.setRank(rank);
        systemNotice.setLanguages(languages);
        systemNotice.setCreateTime(new Date());
        systemNotice.setModifyTime(new Date());
        return systemNoticeMapper.insert(systemNotice);
    }

    @Override
    @Transactional
    public int update(String title, String details, String jumpUrl, Integer rank, String id, String languages) {
        SystemNotice systemNotice = systemNoticeMapper.selectByPrimaryKey(id);
        if (systemNotice==null){
            throw new SysconfException(SysconfResultEnums.NOTICE_DOES_NOT_EXIST);
        }
        systemNotice.setTitle(title!=null?title:systemNotice.getTitle());
        systemNotice.setDetails(details!=null?details:systemNotice.getDetails());
        systemNotice.setJumpUrl(jumpUrl!=null?jumpUrl:systemNotice.getJumpUrl());
        systemNotice.setRank(rank!=null?rank:systemNotice.getRank());
        systemNotice.setLanguages(languages!=null?languages:systemNotice.getLanguages());
        systemNotice.setModifyTime(new Date());
        return systemNoticeMapper.updateByPrimaryKey(systemNotice);
    }
}
