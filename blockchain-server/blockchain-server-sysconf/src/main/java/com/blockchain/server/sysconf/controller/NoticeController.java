package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.sysconf.controller.api.SysconfNoticeApi;
import com.blockchain.server.sysconf.service.SystemNoticeService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:46
 **/
@RestController
@RequestMapping("/systemNotice")
@Api(SysconfNoticeApi.CONTROLLER_API)
public class NoticeController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private SystemNoticeService systemNoticeService;

    /** 
    * @Description: 公告列表 
    * @Param: [status, pageNum, pageSize] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/25 
    */ 
    @GetMapping("/systemNoticeList")
    @ApiOperation(value = SysconfNoticeApi.SystemNoticeList.METHOD_TITLE_NAME, notes = SysconfNoticeApi.SystemNoticeList.METHOD_TITLE_NOTE)
    public ResultDTO systemNoticeList(@ApiParam(SysconfNoticeApi.SystemNoticeList.METHOD_API_STATUS) @RequestParam(required = false, value = "status") String status,
                                      @ApiParam(SysconfNoticeApi.SystemNoticeList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                                      @ApiParam(SysconfNoticeApi.SystemNoticeList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(systemNoticeService.systemNoticeList(status));
    }

    /**
    * @Description:  新增公告
    * @Param: [details, jumpUrl, status, rank, request]
    * @return: com.blockchain.common.base.dto.ResultDTO
    * @Author: Liu.sd
    * @Date: 2019/3/25
    */
    @PostMapping("/insertSystemNotice")
    @ApiOperation(value = SysconfNoticeApi.InsertSystemNotice.METHOD_TITLE_NAME, notes = SysconfNoticeApi.InsertSystemNotice.METHOD_TITLE_NOTE)
    public ResultDTO insertSystemNotice(@ApiParam(SysconfNoticeApi.InsertSystemNotice.METHOD_API_TITLE) @RequestParam("title") String title, @ApiParam(SysconfNoticeApi.InsertSystemNotice.METHOD_API_DETAILS) @RequestParam("details") String details, @ApiParam(SysconfNoticeApi.InsertSystemNotice.METHOD_API_JUMPURL) @RequestParam(required = false, value = "jumpUrl") String jumpUrl, @ApiParam(SysconfNoticeApi.InsertSystemNotice.METHOD_API_STATUS) @RequestParam("status") String status, @ApiParam(SysconfNoticeApi.InsertSystemNotice.METHOD_API_RANK) @RequestParam("rank") Integer rank,@ApiParam(SysconfNoticeApi.InsertSystemNotice.METHOD_API_LANGUAGES) @RequestParam("languages") String languages, HttpServletRequest request) {
        systemNoticeService.insert(title,details,jumpUrl, status, rank,languages);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description:  修改公告
    * @Param: [details, jumpUrl, rank, id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/25 
    */ 
    @PostMapping("/updateSystemNotice")
    @ApiOperation(value = SysconfNoticeApi.UpdateSystemNotice.METHOD_TITLE_NAME, notes = SysconfNoticeApi.UpdateSystemNotice.METHOD_TITLE_NOTE)
    public ResultDTO updateSystemNotice(@ApiParam(SysconfNoticeApi.UpdateSystemNotice.METHOD_API_TITLE) @RequestParam("title") String title, @ApiParam(SysconfNoticeApi.UpdateSystemNotice.METHOD_API_DETAILS) @RequestParam(required = false, value = "details") String details, @ApiParam(SysconfNoticeApi.UpdateSystemNotice.METHOD_API_JUMPURL) @RequestParam(required = false, value = "jumpUrl") String jumpUrl, @ApiParam(SysconfNoticeApi.UpdateSystemNotice.METHOD_API_RANK) @RequestParam(required = false, value = "rank") Integer rank, @ApiParam(SysconfNoticeApi.UpdateSystemNotice.METHOD_API_ID) @RequestParam("id") String id,@ApiParam(SysconfNoticeApi.InsertSystemNotice.METHOD_API_LANGUAGES) @RequestParam("languages") String languages, HttpServletRequest request) {
        systemNoticeService.update(title,details, jumpUrl, rank, id,languages);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 修改公告状态 
    * @Param: [status, id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/25 
    */ 
    @PostMapping("/updateSystemNoticeStatus")
    @ApiOperation(value = SysconfNoticeApi.UpdateSystemNoticeStatus.METHOD_TITLE_NAME, notes = SysconfNoticeApi.UpdateSystemNoticeStatus.METHOD_TITLE_NOTE)
    public ResultDTO updateSystemNoticeStatus(@ApiParam(SysconfNoticeApi.UpdateSystemNoticeStatus.METHOD_API_STATUS) @RequestParam("status") String status,
                                              @ApiParam(SysconfNoticeApi.UpdateSystemNoticeStatus.METHOD_API_ID) @RequestParam("id") String id,
                                              HttpServletRequest request) {
        systemNoticeService.updateStatus(status, id);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 删除公告
    * @Param: [id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/25 
    */ 
    @PostMapping("/deleteNotice")
    @ApiOperation(value = SysconfNoticeApi.DeleteNotice.METHOD_TITLE_NAME, notes = SysconfNoticeApi.DeleteNotice.METHOD_TITLE_NOTE)
    public ResultDTO deleteNotice(@ApiParam(SysconfNoticeApi.DeleteNotice.METHOD_API_ID) @RequestParam("id") String id,
                                  HttpServletRequest request) {
        systemNoticeService.deleteNotice(id);
        return ResultDTO.requstSuccess();
    }
}
