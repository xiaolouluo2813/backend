package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.common.enums.SysconfResultEnums;
import com.blockchain.server.sysconf.common.exception.SysconfException;
import com.blockchain.server.sysconf.entity.ProjectCenterClassify;
import com.blockchain.server.sysconf.mapper.ProjectCenterClassifyMapper;
import com.blockchain.server.sysconf.service.ProjectCenterClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class ProjectCenterClassifyServiceImpl implements ProjectCenterClassifyService {

    @Autowired
    private ProjectCenterClassifyMapper projectCenterClassifyMapper;

    @Override
    public List<ProjectCenterClassify> list(String status, String name) {
        return projectCenterClassifyMapper.list(status,name);
    }

    @Override
    public void add(ProjectCenterClassify projectCenterClassify) {
        projectCenterClassify.setId(UUID.randomUUID().toString());
        projectCenterClassify.setCreateTime(new Date());
        projectCenterClassify.setModifyTime(new Date());
        projectCenterClassifyMapper.insert(projectCenterClassify);
    }

    @Override
    public void update(ProjectCenterClassify projectCenterClassify) {
        ProjectCenterClassify oldProjectCenterClassify = projectCenterClassifyMapper.selectByPrimaryKey(projectCenterClassify.getId());
        if (oldProjectCenterClassify ==null){
            throw new SysconfException(SysconfResultEnums.IMAGE_DOES_NOT_EXIST);
        }
        oldProjectCenterClassify.setName(projectCenterClassify.getName());
        oldProjectCenterClassify.setModifyTime(new Date());
        projectCenterClassifyMapper.updateByPrimaryKey(projectCenterClassify);
    }

    @Override
    public void updateStatus(String status, String id) {
        ProjectCenterClassify projectCenterClassify = projectCenterClassifyMapper.selectByPrimaryKey(id);
        if (projectCenterClassify ==null){
            throw new SysconfException(SysconfResultEnums.IMAGE_DOES_NOT_EXIST);
        }
        projectCenterClassify.setStatus(status);
        projectCenterClassifyMapper.updateByPrimaryKey(projectCenterClassify);
    }

    @Override
    public void delete(String id) {
        projectCenterClassifyMapper.deleteByPrimaryKey(id);
    }
}
