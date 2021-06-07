package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.common.enums.SysconfResultEnums;
import com.blockchain.server.sysconf.common.exception.SysconfException;
import com.blockchain.server.sysconf.entity.ProjectCenterReport;
import com.blockchain.server.sysconf.mapper.ProjectCenterReportMapper;
import com.blockchain.server.sysconf.service.ProjectCenterReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Service
public class ProjectCenterReportServiceImpl implements ProjectCenterReportService {

    @Autowired
    private ProjectCenterReportMapper projectCenterReportMapper;
    @Override
    public List<ProjectCenterReport> list(String status, String projectId) {
        return projectCenterReportMapper.list(status,projectId);
    }

    @Override
    public void add(ProjectCenterReport projectCenterReport) {
        projectCenterReport.setId(UUID.randomUUID().toString());
        projectCenterReport.setCreateTime(new Date());
        projectCenterReportMapper.insert(projectCenterReport);
    }

    @Override
    public void update(ProjectCenterReport projectCenterReport) {

    }

    @Override
    public void delete(String id) {
        projectCenterReportMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateStatus(String status, String id) {
        ProjectCenterReport projectCenterReport = projectCenterReportMapper.selectByPrimaryKey(id);
        if (projectCenterReport ==null){
            throw new SysconfException(SysconfResultEnums.IMAGE_DOES_NOT_EXIST);
        }
        projectCenterReport.setStatus(status);
        projectCenterReportMapper.updateByPrimaryKey(projectCenterReport);
    }
}
