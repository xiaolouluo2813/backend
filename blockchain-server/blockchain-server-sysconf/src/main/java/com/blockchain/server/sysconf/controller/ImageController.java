package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.sysconf.common.constant.ImagesConstant;
import com.blockchain.server.sysconf.controller.api.SysconfImageApi;
import com.blockchain.server.sysconf.service.SystemImageService;
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
 * @create: 2019-03-25 13:45
 **/
@RestController
@RequestMapping("/systemImage")
@Api(SysconfImageApi.CONTROLLER_API)
public class ImageController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private SystemImageService systemImageService;

    /**
     * @Description: 轮播图列表
     * @Param: [status, pageNum, pageSize]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/3/25
     */
    @GetMapping("/systemImageList")
    @ApiOperation(value = SysconfImageApi.SystemImageList.METHOD_TITLE_NAME, notes = SysconfImageApi.SystemImageList.METHOD_TITLE_NOTE)
    public ResultDTO systemImageList(@ApiParam(SysconfImageApi.SystemImageList.METHOD_API_STATUS) @RequestParam(required = false, value = "status") String status,
                                     @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_STATUS) @RequestParam(required = false, value = "type") String type,
                                      @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                                      @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(systemImageService.systemImageList(type,status));
    }

    /**
     * @Description:  新增轮播图
     * @Param: [fileUrl, jumpUrl, status, rank, request]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/3/25
     */
    @PostMapping("/insertSystemImage")
    @ApiOperation(value = SysconfImageApi.InsertSystemImage.METHOD_TITLE_NAME, notes = SysconfImageApi.InsertSystemImage.METHOD_TITLE_NOTE)
    public ResultDTO insertSystemImage(@ApiParam(SysconfImageApi.InsertSystemImage.METHOD_API_DETAILS) @RequestParam("fileUrl") String fileUrl,
                                       @ApiParam(SysconfImageApi.InsertSystemImage.METHOD_API_JUMPURL) @RequestParam("jumpUrl") String jumpUrl,
                                       @ApiParam(SysconfImageApi.InsertSystemImage.METHOD_API_STATUS) @RequestParam("status") String status,
                                       @ApiParam(SysconfImageApi.InsertSystemImage.METHOD_API_RANK) @RequestParam("rank") Integer rank,
                                       @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_STATUS) @RequestParam("type") String type,
                                       HttpServletRequest request) {
        systemImageService.insert(fileUrl, jumpUrl, status, rank,type);
        return ResultDTO.requstSuccess();
    }

    /**
     * @Description:  修改轮播图
     * @Param: [fileUrl, jumpUrl, rank, id, request]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/3/25
     */
    @PostMapping("/updateSystemImage")
    @ApiOperation(value = SysconfImageApi.UpdateSystemImage.METHOD_TITLE_NAME, notes = SysconfImageApi.UpdateSystemImage.METHOD_TITLE_NOTE)
    public ResultDTO updateSystemImage(@ApiParam(SysconfImageApi.UpdateSystemImage.METHOD_API_DETAILS) @RequestParam(required = false, value = "fileUrl") String fileUrl, @ApiParam(SysconfImageApi.UpdateSystemImage.METHOD_API_JUMPURL) @RequestParam(required = false, value = "jumpUrl") String jumpUrl, @ApiParam(SysconfImageApi.UpdateSystemImage.METHOD_API_RANK) @RequestParam(required = false, value = "rank") Integer rank, @ApiParam(SysconfImageApi.UpdateSystemImage.METHOD_API_ID) @RequestParam("id") String id, HttpServletRequest request) {
        systemImageService.update(fileUrl, jumpUrl, rank, id);
        return ResultDTO.requstSuccess();
    }

    /**
     * @Description: 修改轮播图状态
     * @Param: [status, id, request]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/3/25
     */
    @PostMapping("/updateSystemImageStatus")
    @ApiOperation(value = SysconfImageApi.UpdateSystemImageStatus.METHOD_TITLE_NAME, notes = SysconfImageApi.UpdateSystemImageStatus.METHOD_TITLE_NOTE)
    public ResultDTO updateSystemImageStatus(@ApiParam(SysconfImageApi.UpdateSystemImageStatus.METHOD_API_STATUS) @RequestParam("status") String status,
                                              @ApiParam(SysconfImageApi.UpdateSystemImageStatus.METHOD_API_ID) @RequestParam("id") String id,
                                              HttpServletRequest request) {
        systemImageService.updateStatus(status, id);
        return ResultDTO.requstSuccess();
    }

    /**
     * @Description: 删除轮播图
     * @Param: [id, request]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/3/25
     */
    @PostMapping("/deleteImage")
    @ApiOperation(value = SysconfImageApi.DeleteImage.METHOD_TITLE_NAME, notes = SysconfImageApi.DeleteImage.METHOD_TITLE_NOTE)
    public ResultDTO deleteImage(@ApiParam(SysconfImageApi.DeleteImage.METHOD_API_ID) @RequestParam("id") String id,
                                  HttpServletRequest request) {
        systemImageService.deleteImage(id);
        return ResultDTO.requstSuccess();
    }
}
