package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.sysconf.controller.api.SysconfVersionApi;
import com.blockchain.server.sysconf.controller.api.SysconfWgtVersionApi;
import com.blockchain.server.sysconf.entity.WgtVersion;
import com.blockchain.server.sysconf.service.WgtVersionService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * app应用版本 信息操作处理
 *
 * @author ruoyi
 * @date 2018-10-29
 */
@RestController
@Api(SysconfWgtVersionApi.CONTROLLER_API)
@RequestMapping("/wgtVersion")
public class WgtVersionController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(WgtVersionController.class);

    @Autowired
    private WgtVersionService wgtVersionService;


    /**
     * 查询app应用版本列表
     */
    @GetMapping("/list")
    @ApiOperation(value = SysconfWgtVersionApi.List.METHOD_TITLE_NAME, notes = SysconfWgtVersionApi.List.METHOD_TITLE_NOTE)
    public ResultDTO list(@ApiParam(SysconfWgtVersionApi.List.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                          @ApiParam(SysconfWgtVersionApi.List.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(wgtVersionService.selectVersionList(null));
    }

    /**
     * 新增保存app应用版本
     */
    @PostMapping("/add")
    @ApiOperation(value = SysconfWgtVersionApi.Add.METHOD_TITLE_NAME, notes = SysconfWgtVersionApi.Add.METHOD_TITLE_NOTE)
    public ResultDTO add(@ApiParam(SysconfWgtVersionApi.Add.METHOD_API_WGTVERSION) WgtVersion wgtVersion) {
        wgtVersionService.saveWgtVersion(wgtVersion.getWgtVersion(), wgtVersion.getWgtUrl(), wgtVersion.getRemark());
        return ResultDTO.requstSuccess();
    }

    /**
     * 修改保存app应用版本
     */
    @PostMapping("/edit")
    @ApiOperation(value = SysconfWgtVersionApi.Edit.METHOD_TITLE_NAME, notes = SysconfWgtVersionApi.Edit.METHOD_TITLE_NOTE)
    public ResultDTO edit(@ApiParam(SysconfWgtVersionApi.Edit.METHOD_API_WGTVERSION) WgtVersion wgtVersion) {
        wgtVersionService.updatewgtVersion(wgtVersion.getId(), wgtVersion.getWgtVersion(), wgtVersion.getWgtUrl(), wgtVersion.getRemark());
        return ResultDTO.requstSuccess();
    }

    /**
     * 删除app应用版本
     *
     * @param ids
     * @return
     */
    @PostMapping("/remove")
    @ApiOperation(value = SysconfWgtVersionApi.Remove.METHOD_TITLE_NAME, notes = SysconfWgtVersionApi.Remove.METHOD_TITLE_NOTE)
    public ResultDTO remove(@ApiParam(SysconfWgtVersionApi.Remove.METHOD_API_IDS) String ids) {
        wgtVersionService.deleteWgtVersionById(ids);
        return ResultDTO.requstSuccess();
    }

    /**
     * 保存文件
     *
     * @param appealFile  文件
     * @param fileRootDir 文件存储根目录
     * @throws IOException 存储异常
     */
    private String saveFile(MultipartFile appealFile, String fileRootDir, String version) throws IOException {
        //判断文件夹是否存在
        File filePathDir = new File(fileRootDir);
        if (!filePathDir.exists() && !filePathDir.isDirectory()) {
            filePathDir.mkdir();
        }
        //源文件名
        String name = appealFile.getOriginalFilename();
        //文件后缀名
        String type = name.substring(name.lastIndexOf("."));
        //新文件名
        String fileName = "APP_" + version + type;
        //申诉文件保存到本地/服务器
        String filePath = fileRootDir + fileName;
        appealFile.transferTo(new File(filePath));
        return fileName;
    }
}
