package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.sysconf.controller.api.SysconfImageApi;
import com.blockchain.server.sysconf.controller.api.SysconfNoticeApi;
import com.blockchain.server.sysconf.entity.ProjectCenterClassify;
import com.blockchain.server.sysconf.entity.ProjectCenterInfo;
import com.blockchain.server.sysconf.service.ProjectCenterClassifyService;
import com.blockchain.server.sysconf.service.ProjectCenterService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目类型
 *
 */
@RestController
@RequestMapping("/projectCenterClassify")
public class ProjectCenterClassifyController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectCenterClassifyController.class);

    @Autowired
    private ProjectCenterClassifyService projectCenterClassifyService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = SysconfImageApi.SystemImageList.METHOD_TITLE_NAME, notes = SysconfImageApi.SystemImageList.METHOD_TITLE_NOTE)
    public ResultDTO list(@ApiParam(SysconfNoticeApi.SystemNoticeList.METHOD_API_STATUS) @RequestParam(required = false, value = "status") String status,
                          @ApiParam(SysconfNoticeApi.SystemNoticeList.METHOD_API_STATUS) @RequestParam(required = false, value = "name") String name,
                          @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                          @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        if (status==null){
            PageHelper.startPage(pageNum, pageSize);
        }
        return generatePage(projectCenterClassifyService.list(status,name));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    @ResponseBody
    public ResultDTO add(ProjectCenterClassify projectCenterClassify) {
        projectCenterClassifyService.add(projectCenterClassify);
        return ResultDTO.requstSuccess();
    }

    /**
     * 修改
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResultDTO editSave(ProjectCenterClassify projectCenterClassify) {
        projectCenterClassifyService.update(projectCenterClassify);
        return ResultDTO.requstSuccess();
    }
    /**
     * 修改
     */
    @PostMapping("/editStatus")
    @ResponseBody
    public ResultDTO editStatus(@ApiParam(SysconfImageApi.UpdateSystemImageStatus.METHOD_API_STATUS) @RequestParam("status") String status,
                                @ApiParam(SysconfImageApi.UpdateSystemImageStatus.METHOD_API_ID) @RequestParam("id") String id,
                                HttpServletRequest request) {
        projectCenterClassifyService.updateStatus(status, id);
        return ResultDTO.requstSuccess();
    }

    /**
     * 删除
     */
    @PostMapping( "/remove")
    @ResponseBody
    public ResultDTO remove(@ApiParam(SysconfImageApi.DeleteImage.METHOD_API_ID) @RequestParam("id") String id)
    {
        projectCenterClassifyService.delete(id);
        return ResultDTO.requstSuccess();
    }


}
