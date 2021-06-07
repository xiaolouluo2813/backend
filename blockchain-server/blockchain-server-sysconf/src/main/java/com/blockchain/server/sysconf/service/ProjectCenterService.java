package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.dto.ProjectCenterDto;
import com.blockchain.server.sysconf.entity.ProjectCenterInfo;

import java.util.List;

public interface ProjectCenterService {
    /** 
    * @Description:  
    * @Param: [status] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/6/10 
    */ 
    List<ProjectCenterDto> list(String status, String currencyName);

    /** 
    * @Description: 新增 
    * @Param: [projectCenterInfo]
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/6/10 
    */ 
    void add(ProjectCenterInfo projectCenterInfo);

    /** 
    * @Description: 编辑 
    * @Param: [projectCenterInfo]
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/6/10 
    */ 
    void update(ProjectCenterInfo projectCenterInfo);

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
