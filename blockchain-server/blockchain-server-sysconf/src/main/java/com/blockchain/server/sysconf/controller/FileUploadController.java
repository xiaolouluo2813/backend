package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.FileUploadHelper;
import com.blockchain.server.sysconf.common.constant.FileUploadConstant;
import com.blockchain.server.sysconf.common.enums.SysconfResultEnums;
import com.blockchain.server.sysconf.common.exception.SysconfException;
import com.blockchain.server.sysconf.controller.api.FileUploadApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: Liusd
 * @create: 2019-03-25 20:03
 **/
@Api(FileUploadApi.FILE_UPLOAD_API)
@RestController
@CrossOrigin
@RequestMapping("/upload")
public class FileUploadController extends FileUploadHelper {

    @Value("${FILES_DIR.ROOT}")
    private String FILE_ROOT_PATH;//文件上传根目录

    @ApiOperation(value = FileUploadApi.SystemImgUploadFile.METHOD_TITLE_NAME, notes = FileUploadApi.SystemImgUploadFile.METHOD_TITLE_NOTE)
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResultDTO uploadFile(@ApiParam(FileUploadApi.SystemImgUploadFile.METHOD_API_IMG) @RequestParam(value = "sysImg") String sysImg) {
        //返回的状态信息
        //获取图片文件
        String imgName = generateImage(sysImg, FileUploadConstant.UPLOAD_SLIDE_IMAGE);
        return ResultDTO.requstSuccess(imgName);
    }


    @ApiOperation(value = FileUploadApi.SystemImgUploadFile.METHOD_TITLE_NAME, notes = FileUploadApi.SystemImgUploadFile.METHOD_TITLE_NOTE)
    @RequestMapping(value = "/uploadPDF", method = RequestMethod.POST)
    public ResultDTO uploadPDF(@ApiParam(FileUploadApi.SystemImgUploadFile.METHOD_API_IMG) @RequestParam(value = "sysImg") MultipartFile file) {
        //返回的状态信息
        //获取图片文件
        String imgName = saveFile(file, FileUploadConstant.UPLOAD_SLIDE_PDF);
        return ResultDTO.requstSuccess(imgName);
    }

    /** 
    * @Description: 图片文件转存base64
    * @Param: [imgBase64, relativeDir] 
    * @return: java.lang.String 
    * @Author: Liu.sd 
    * @Date: 2019/3/25 
    */ 
    private String generateImage(String imgBase64, String relativeDir) {
        try {
            return generateImage(imgBase64, FILE_ROOT_PATH, relativeDir);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SysconfException(SysconfResultEnums.FILE_UPLOAD_ERROR);
        }
    }


    /**
     * 保存文件
     *
     * @param file        文件
     * @param relativeDir 文件相对目录
     * @return 文件相对路径
     * @throws IOException
     */
    private String saveFile(MultipartFile file, String relativeDir) {
        try {
            return FileUploadHelper.saveFile(file, FILE_ROOT_PATH, relativeDir);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SysconfException(SysconfResultEnums.FILE_UPLOAD_ERROR);
        }
    }
}
