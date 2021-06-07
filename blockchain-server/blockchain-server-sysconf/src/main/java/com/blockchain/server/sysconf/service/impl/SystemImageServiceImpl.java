package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.common.enums.SysconfResultEnums;
import com.blockchain.server.sysconf.common.exception.SysconfException;
import com.blockchain.server.sysconf.entity.SystemImage;
import com.blockchain.server.sysconf.mapper.SystemImageMapper;
import com.blockchain.server.sysconf.service.SystemImageService;
import org.apache.commons.lang3.StringUtils;
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
public class SystemImageServiceImpl implements SystemImageService {

    @Autowired
    private SystemImageMapper systemImageMapper;

    @Override
    public List<SystemImage> systemImageList(String type,String status) {
        return systemImageMapper.selectByTypeAndStatus(type,status);
    }

    @Override
    @Transactional
    public int insert(String fileUrl, String jumpUrl, String status, Integer rank,String type) {
        SystemImage systemImage = new SystemImage();
        systemImage.setId(UUID.randomUUID().toString());
        systemImage.setFileUrl(fileUrl);
        systemImage.setJumpUrl(jumpUrl);
        systemImage.setStatus(status);
        systemImage.setRank(rank);
        systemImage.setType(type);
        systemImage.setCreateTime(new Date());
        systemImage.setModifyTime(new Date());
        return systemImageMapper.insert(systemImage);
    }

    @Override
    @Transactional
    public int update(String fileUrl, String jumpUrl, Integer rank, String id) {
        SystemImage systemImage = systemImageMapper.selectByPrimaryKey(id);
        if (systemImage==null){
            throw new SysconfException(SysconfResultEnums.IMAGE_DOES_NOT_EXIST);
        }
        systemImage.setFileUrl(fileUrl!=null?fileUrl:systemImage.getFileUrl());
        systemImage.setJumpUrl(jumpUrl!=null?jumpUrl:systemImage.getJumpUrl());
        systemImage.setRank(rank!=null?rank:systemImage.getRank());
        systemImage.setModifyTime(new Date());
        return systemImageMapper.updateByPrimaryKey(systemImage);
    }

    @Override
    @Transactional
    public int updateStatus(String status, String id) {
        SystemImage systemImage = systemImageMapper.selectByPrimaryKey(id);
        if (systemImage==null){
            throw new SysconfException(SysconfResultEnums.IMAGE_DOES_NOT_EXIST);
        }
        systemImage.setStatus(status);
        systemImage.setModifyTime(new Date());
        return systemImageMapper.updateByPrimaryKey(systemImage);
    }

    @Override
    @Transactional
    public int deleteImage(String id) {
        SystemImage systemImage = systemImageMapper.selectByPrimaryKey(id);
        if (systemImage==null){
            throw new SysconfException(SysconfResultEnums.IMAGE_DOES_NOT_EXIST);
        }
        return systemImageMapper.delete(systemImage);
    }
}
