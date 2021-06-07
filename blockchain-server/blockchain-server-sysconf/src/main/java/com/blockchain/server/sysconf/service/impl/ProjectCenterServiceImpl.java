package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.common.enums.SysconfResultEnums;
import com.blockchain.server.sysconf.common.exception.SysconfException;
import com.blockchain.server.sysconf.dto.ProjectCenterDto;
import com.blockchain.server.sysconf.entity.ProjectCenterInfo;
import com.blockchain.server.sysconf.mapper.ProjectCenterMapper;
import com.blockchain.server.sysconf.service.ProjectCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectCenterServiceImpl implements ProjectCenterService {

    @Autowired
    private ProjectCenterMapper projectCenterMapper;

    @Override
    public List<ProjectCenterDto> list(String status, String currencyName) {
        return projectCenterMapper.list(status,currencyName);
    }

    @Override
    public void add(ProjectCenterInfo projectCenterInfo) {
        projectCenterInfo.setId(UUID.randomUUID().toString());
        projectCenterInfo.setCreateTime(new Date());
        projectCenterInfo.setModifyTime(new Date());
        projectCenterMapper.insert(projectCenterInfo);
    }

    @Override
    public void update(ProjectCenterInfo projectCenterInfo) {
        ProjectCenterInfo oldProjectCenterInfo = projectCenterMapper.selectByPrimaryKey(projectCenterInfo.getId());
        oldProjectCenterInfo.setCurrencyName(projectCenterInfo.getCurrencyName()!=null? projectCenterInfo.getCurrencyName(): oldProjectCenterInfo.getCurrencyName());
        oldProjectCenterInfo.setCoinUrl(projectCenterInfo.getCoinUrl()!=null? projectCenterInfo.getCoinUrl(): oldProjectCenterInfo.getCoinUrl());
        oldProjectCenterInfo.setOrderBy(projectCenterInfo.getOrderBy()!=null? projectCenterInfo.getOrderBy(): oldProjectCenterInfo.getOrderBy());
        oldProjectCenterInfo.setIssueTime(projectCenterInfo.getIssueTime()!=null? projectCenterInfo.getIssueTime(): oldProjectCenterInfo.getIssueTime());
        oldProjectCenterInfo.setTotalSupply(projectCenterInfo.getTotalSupply()!=null? projectCenterInfo.getTotalSupply(): oldProjectCenterInfo.getTotalSupply());
        oldProjectCenterInfo.setTotalCirculation(projectCenterInfo.getTotalCirculation()!=null? projectCenterInfo.getTotalCirculation(): oldProjectCenterInfo.getTotalCirculation());
        oldProjectCenterInfo.setIcoAmount(projectCenterInfo.getIcoAmount()!=null? projectCenterInfo.getIcoAmount(): oldProjectCenterInfo.getIcoAmount());
        oldProjectCenterInfo.setWhitePaper(projectCenterInfo.getWhitePaper()!=null? projectCenterInfo.getWhitePaper(): oldProjectCenterInfo.getWhitePaper());
        oldProjectCenterInfo.setDescr(projectCenterInfo.getDescr()!=null? projectCenterInfo.getDescr(): oldProjectCenterInfo.getDescr());
        oldProjectCenterInfo.setType(projectCenterInfo.getType()!=null? projectCenterInfo.getType(): oldProjectCenterInfo.getType());
        oldProjectCenterInfo.setStatus(projectCenterInfo.getStatus()!=null? projectCenterInfo.getStatus(): oldProjectCenterInfo.getStatus());
        oldProjectCenterInfo.setLanguages(projectCenterInfo.getLanguages()!=null? projectCenterInfo.getLanguages(): oldProjectCenterInfo.getLanguages());
        oldProjectCenterInfo.setClassifyId(projectCenterInfo.getClassifyId()!=null? projectCenterInfo.getClassifyId(): oldProjectCenterInfo.getClassifyId());
        oldProjectCenterInfo.setUccn(projectCenterInfo.getUccn()!=null? projectCenterInfo.getUccn(): oldProjectCenterInfo.getUccn());
        oldProjectCenterInfo.setPresentation(projectCenterInfo.getPresentation()!=null? projectCenterInfo.getPresentation(): oldProjectCenterInfo.getPresentation());
        oldProjectCenterInfo.setModifyTime(new Date());
        projectCenterMapper.updateByPrimaryKey(oldProjectCenterInfo);
    }

    @Override
    public void delete(String id) {
        projectCenterMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateStatus(String status, String id) {
        ProjectCenterInfo projectCenterInfo = projectCenterMapper.selectByPrimaryKey(id);
        if (projectCenterInfo ==null){
            throw new SysconfException(SysconfResultEnums.IMAGE_DOES_NOT_EXIST);
        }
        projectCenterInfo.setStatus(status);
        projectCenterMapper.updateByPrimaryKey(projectCenterInfo);
    }
}
