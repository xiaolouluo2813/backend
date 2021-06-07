package com.blockchain.server.system.service.impl;

import com.blockchain.server.system.common.enums.SystemResultEnums;
import com.blockchain.server.system.common.exception.SystemException;
import com.blockchain.server.system.entity.SystemImage;
import com.blockchain.server.system.mapper.SystemImageMapper;
import com.blockchain.server.system.service.SystemImageService;
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
    public List<SystemImage> systemImageList(String status) {
        if (status!=null){
            SystemImage systemImage = new SystemImage();
            systemImage.setStatus(status);
            return systemImageMapper.select(systemImage);
        }else
            return systemImageMapper.selectAll();
    }

    @Override
    @Transactional
    public int insert(String fileUrl, String jumpUrl, String status, Integer rank) {
        SystemImage systemImage = new SystemImage();
        systemImage.setId(UUID.randomUUID().toString());
        systemImage.setFileUrl(fileUrl);
        systemImage.setJumpUrl(jumpUrl);
        systemImage.setStatus(status);
        systemImage.setRank(rank);
        systemImage.setCreateTime(new Date());
        systemImage.setModifyTime(new Date());
        return systemImageMapper.insert(systemImage);
    }

    @Override
    @Transactional
    public int update(String fileUrl, String jumpUrl, Integer rank, String id) {
        SystemImage systemImage = systemImageMapper.selectByPrimaryKey(id);
        if (systemImage==null){
            throw new SystemException(SystemResultEnums.IMAGE_DOES_NOT_EXIST);
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
            throw new SystemException(SystemResultEnums.IMAGE_DOES_NOT_EXIST);
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
            throw new SystemException(SystemResultEnums.IMAGE_DOES_NOT_EXIST);
        }
        return systemImageMapper.delete(systemImage);
    }
}
