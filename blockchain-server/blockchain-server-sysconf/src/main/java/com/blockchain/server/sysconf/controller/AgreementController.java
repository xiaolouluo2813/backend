package com.blockchain.server.sysconf.controller;


import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.sysconf.common.constant.AgreementConstant;
import com.blockchain.server.sysconf.controller.api.AgreementApi;
import com.blockchain.server.sysconf.controller.api.SysconfImageApi;
import com.blockchain.server.sysconf.controller.api.SysconfNoticeApi;
import com.blockchain.server.sysconf.dto.AboutUsQueryConditionDTO;
import com.blockchain.server.sysconf.service.AgreementService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(AgreementApi.AGREEMENT_API)
@RestController
@RequestMapping("/agreement")
public class AgreementController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AgreementController.class);

    @Autowired
    private AgreementService agreementService;
    /**
     * 查询用户协议  (客户端）
     * @return
     */
    @ApiOperation(value = AgreementApi.FINDAGREEMENT.METHOD_TITLE_NAME, notes = AgreementApi.FINDAGREEMENT.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO list(@ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGENUM) @RequestParam(value = "type", defaultValue = AgreementConstant.TYPE_USER, required = false) String type,
                          @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                          @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(agreementService.list(type));
    }


    /** 
    * @Description: 新增 
    * @Param: [textContent, languages, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/4/12 
    */ 
    @PostMapping("/add")
    @ApiOperation(value = SysconfNoticeApi.InsertSystemNotice.METHOD_TITLE_NAME, notes = SysconfNoticeApi.InsertSystemNotice.METHOD_TITLE_NOTE)
    public ResultDTO insertSystemNotice(@ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGENUM) @RequestParam(value = "type", defaultValue = AgreementConstant.TYPE_USER, required = false) String type,@ApiParam(SysconfNoticeApi.InsertSystemNotice.METHOD_API_DETAILS) @RequestParam("textContent") String textContent, @ApiParam(SysconfNoticeApi.InsertSystemNotice.METHOD_API_LANGUAGES) @RequestParam("languages") String languages,HttpServletRequest request) {
        agreementService.insert(type,textContent, languages);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 编辑
    * @Param: [textContent, id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/4/12 
    */ 
    @PostMapping("/edit")
    @ApiOperation(value = SysconfNoticeApi.UpdateSystemNotice.METHOD_TITLE_NAME, notes = SysconfNoticeApi.UpdateSystemNotice.METHOD_TITLE_NOTE)
    public ResultDTO updateSystemNotice(@ApiParam(SysconfNoticeApi.InsertSystemNotice.METHOD_API_DETAILS) @RequestParam("textContent") String textContent, @ApiParam(SysconfNoticeApi.UpdateSystemNotice.METHOD_API_ID) @RequestParam("id") String id, HttpServletRequest request) {
        agreementService.update(textContent,id);
        return ResultDTO.requstSuccess();
    }

    @PostMapping("/remove")
    @ApiOperation(value = SysconfNoticeApi.DeleteNotice.METHOD_TITLE_NAME, notes = SysconfNoticeApi.DeleteNotice.METHOD_TITLE_NOTE)
    public ResultDTO deleteNotice(@ApiParam(SysconfNoticeApi.DeleteNotice.METHOD_API_ID) @RequestParam("id") String id,
                                  javax.servlet.http.HttpServletRequest request) {
        agreementService.remove(id);
        return ResultDTO.requstSuccess();
    }
}
