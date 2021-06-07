package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.entity.ProjectCenterClassify;

import java.util.List;

public interface ProjectCenterClassifyService {
    List<ProjectCenterClassify> list(String status, String name);

    void add(ProjectCenterClassify projectCenterClassify);

    void update(ProjectCenterClassify projectCenterClassify);

    void updateStatus(String status, String id);

    void delete(String id);
}
