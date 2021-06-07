package com.blockchain.server.system.service.impl;

import com.blockchain.server.system.common.enums.SystemResultEnums;
import com.blockchain.server.system.common.exception.SystemException;
import com.blockchain.server.system.entity.SystemNotice;
import com.blockchain.server.system.mapper.SystemNoticeMapper;
import com.blockchain.server.system.service.SystemNoticeService;
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
    public int insert(String details, String jumpUrl, String status, Integer rank) {
        SystemNotice systemNotice = new SystemNotice();
        systemNotice.setId(UUID.randomUUID().toString());
        systemNotice.setDetails(details);
        systemNotice.setJumpUrl(jumpUrl);
        systemNotice.setStatus(status);
        systemNotice.setRank(rank);
        systemNotice.setCreateTime(new Date());
        systemNotice.setModifyTime(new Date());
        return systemNoticeMapper.insert(systemNotice);
    }

    @Override
    @Transactional
    public int update(String details, String jumpUrl, Integer rank, String id) {
        SystemNotice systemNotice = systemNoticeMapper.selectByPrimaryKey(id);
        if (systemNotice==null){
            throw new SystemException(SystemResultEnums.NOTICE_DOES_NOT_EXIST);
        }
        systemNotice.setDetails(details!=null?details:systemNotice.getDetails());
        systemNotice.setJumpUrl(jumpUrl!=null?jumpUrl:systemNotice.getJumpUrl());
        systemNotice.setRank(rank!=null?rank:systemNotice.getRank());
        systemNotice.setModifyTime(new Date());
        return systemNoticeMapper.updateByPrimaryKey(systemNotice);
    }

    @Override
    @Transactional
    public int updateStatus(String status, String id) {
        SystemNotice systemNotice = systemNoticeMapper.selectByPrimaryKey(id);
        if (systemNotice==null){
            throw new SystemException(SystemResultEnums.NOTICE_DOES_NOT_EXIST);
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
            throw new SystemException(SystemResultEnums.NOTICE_DOES_NOT_EXIST);
        }
        return systemNoticeMapper.delete(systemNotice);
    }

    @Override
    public List<SystemNotice> systemNoticeList(String status) {
        if (status!=null){
            SystemNotice systemNotice = new SystemNotice();
            systemNotice.setStatus(status);
            return systemNoticeMapper.select(systemNotice);
        }else
            return systemNoticeMapper.selectAll();
    }
}
