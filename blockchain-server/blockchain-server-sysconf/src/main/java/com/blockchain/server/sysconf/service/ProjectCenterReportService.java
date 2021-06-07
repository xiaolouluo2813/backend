package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.entity.ProjectCenterReport;

import java.util.List;

public interface ProjectCenterReportService {
    /** 
    * @Description:  
    * @Param: [status] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/6/10 
    */ 
    List<ProjectCenterReport> list(String status, String projectId);

    /** 
    * @Description: 新增 
    * @Param: [projectCenterInfo]
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/6/10 
    */ 
    void add(ProjectCenterReport projectCenterInfo);

    /** 
    * @Description: 编辑 
    * @Param: [projectCenterInfo]
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/6/10 
    */ 
    void update(ProjectCenterReport projectCenterInfo);

    /** 
    * @Description: 删除
    * @Param: [id] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/6/10 
    */ 
    void delete(String id);

    /** 
    * @Description: 修改状态 
    * @Param: [status, id] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/6/10 
    */ 
    void updateStatus(String status, String id);
}
