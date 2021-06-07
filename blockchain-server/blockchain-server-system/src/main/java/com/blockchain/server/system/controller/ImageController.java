package com.blockchain.server.system.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.system.controller.api.SystemImageApi;
import com.blockchain.server.system.service.SystemImageService;
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
@Api(SystemImageApi.CONTROLLER_API)
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
    @ApiOperation(value = SystemImageApi.SystemImageList.METHOD_TITLE_NAME, notes = SystemImageApi.SystemImageList.METHOD_TITLE_NOTE)
    public ResultDTO systemImageList(@ApiParam(SystemImageApi.SystemImageList.METHOD_API_STATUS) @RequestParam(required = false, value = "status") String status,
                                      @ApiParam(SystemImageApi.SystemImageList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                                      @ApiParam(SystemImageApi.SystemImageList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(systemImageService.systemImageList(status));
    }

    /**
     * @Description:  新增轮播图
     * @Param: [fileUrl, jumpUrl, status, rank, request]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/3/25
     */
    @PostMapping("/insertSystemImage")
    @ApiOperation(value = SystemImageApi.InsertSystemImage.METHOD_TITLE_NAME, notes = SystemImageApi.InsertSystemImage.METHOD_TITLE_NOTE)
    public ResultDTO insertSystemImage(@ApiParam(SystemImageApi.InsertSystemImage.METHOD_API_DETAILS) @RequestParam("fileUrl") String fileUrl, @ApiParam(SystemImageApi.InsertSystemImage.METHOD_API_JUMPURL) @RequestParam("jumpUrl") String jumpUrl, @ApiParam(SystemImageApi.InsertSystemImage.METHOD_API_STATUS) @RequestParam("status") String status, @ApiParam(SystemImageApi.InsertSystemImage.METHOD_API_RANK) @RequestParam("rank") Integer rank, HttpServletRequest request) {
        systemImageService.insert(fileUrl, jumpUrl, status, rank);
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
    @ApiOperation(value = SystemImageApi.UpdateSystemImage.METHOD_TITLE_NAME, notes = SystemImageApi.UpdateSystemImage.METHOD_TITLE_NOTE)
    public ResultDTO updateSystemImage(@ApiParam(SystemImageApi.UpdateSystemImage.METHOD_API_DETAILS) @RequestParam(required = false, value = "fileUrl") String fileUrl, @ApiParam(SystemImageApi.UpdateSystemImage.METHOD_API_JUMPURL) @RequestParam(required = false, value = "jumpUrl") String jumpUrl, @ApiParam(SystemImageApi.UpdateSystemImage.METHOD_API_RANK) @RequestParam(required = false, value = "rank") Integer rank, @ApiParam(SystemImageApi.UpdateSystemImage.METHOD_API_ID) @RequestParam("id") String id,   HttpServletRequest request) {
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
    @ApiOperation(value = SystemImageApi.UpdateSystemImageStatus.METHOD_TITLE_NAME, notes = SystemImageApi.UpdateSystemImageStatus.METHOD_TITLE_NOTE)
    public ResultDTO updateSystemImageStatus(@ApiParam(SystemImageApi.UpdateSystemImageStatus.METHOD_API_STATUS) @RequestParam("status") String status,
                                              @ApiParam(SystemImageApi.UpdateSystemImageStatus.METHOD_API_ID) @RequestParam("id") String id,
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
    @ApiOperation(value = SystemImageApi.DeleteImage.METHOD_TITLE_NAME, notes = SystemImageApi.DeleteImage.METHOD_TITLE_NOTE)
    public ResultDTO deleteImage(@ApiParam(SystemImageApi.DeleteImage.METHOD_API_ID) @RequestParam("id") String id,
                                  HttpServletRequest request) {
        systemImageService.deleteImage(id);
        return ResultDTO.requstSuccess();
    }
}
