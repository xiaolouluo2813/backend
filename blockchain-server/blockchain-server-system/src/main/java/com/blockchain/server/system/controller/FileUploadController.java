package com.blockchain.server.system.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.FileUploadHelper;
import com.blockchain.server.system.common.constant.FileUploadConstant;
import com.blockchain.server.system.common.enums.SystemResultEnums;
import com.blockchain.server.system.common.exception.SystemException;
import com.blockchain.server.system.controller.api.FileUploadApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
            throw new SystemException(SystemResultEnums.FILE_UPLOAD_ERROR);
        }
    }
}
