package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.entity.ProjectCenterStar;
import com.blockchain.server.sysconf.mapper.ProjectCenterReportMapper;
import com.blockchain.server.sysconf.mapper.ProjectCenterStarMapper;
import com.blockchain.server.sysconf.service.ProjectCenterStarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
@Service
public class ProjectCenterStarServiceImpl implements ProjectCenterStarService {

    @Autowired
    private ProjectCenterStarMapper projectCenterStarMapper;
    @Override
    public int countByProjectId(String projectId) {
        return projectCenterStarMapper.countByProjectId(projectId);
    }

    @Override
    public void add(ProjectCenterStar projectCenterStar) {
        projectCenterStar.setId(UUID.randomUUID().toString());
        projectCenterStar.setCreateTime(new Date());
        projectCenterStarMapper.insert(projectCenterStar);
    }
}
