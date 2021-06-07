package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.sysconf.controller.api.HelpCenterApi;
import com.blockchain.server.sysconf.entity.HelpCenter;
import com.blockchain.server.sysconf.service.HelpCenterService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(HelpCenterApi.CONTROLLER_API)
@RequestMapping("/helpCenter")
public class HelpCenterController extends BaseController{

    private static final Logger LOG = LoggerFactory.getLogger(HelpCenterController.class);

    @Autowired
    private HelpCenterService helpCenterService;

    /** 
    * @Description: 查询帮助中心列表
    * @Param: [helpCenter, pageNum, pageSize] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/4/2 
    */ 
    @GetMapping("/list")
    public ResultDTO selectHelpCenterForApp(HelpCenter helpCenter, @ApiParam(HelpCenterApi.List.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                                            @ApiParam(HelpCenterApi.List.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(helpCenterService.selectHelpCenterList(helpCenter));
    }

    /** 
    * @Description: 新增帮助中心 
    * @Param: [helpCenter] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/4/2 
    */ 
    @PostMapping("/add")
    @ApiOperation(value = HelpCenterApi.Add.METHOD_TITLE_NAME, notes = HelpCenterApi.Add.METHOD_TITLE_NOTE)
    public ResultDTO add(@ApiParam(HelpCenterApi.Add.METHOD_API_HELPCENTER) HelpCenter helpCenter) {
        helpCenterService.insertHelpCenter(helpCenter);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 修改帮助中心 
    * @Param: [helpCenter] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/4/2 
    */ 
    @PostMapping("/edit")
    @ApiOperation(value = HelpCenterApi.Edit.METHOD_TITLE_NAME, notes = HelpCenterApi.Edit.METHOD_TITLE_NOTE)
    public ResultDTO edit(@ApiParam(HelpCenterApi.Edit.METHOD_API_HELPCENTER) HelpCenter helpCenter) {
        helpCenterService.updateHelpCenter(helpCenter);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 删除帮助中心 
    * @Param: [id] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/4/2 
    */ 
    @PostMapping("/remove")
    @ApiOperation(value = HelpCenterApi.Remove.METHOD_TITLE_NAME, notes = HelpCenterApi.Remove.METHOD_TITLE_NOTE)
    public ResultDTO remove(@ApiParam(HelpCenterApi.Remove.METHOD_API_ID) String id) {
        helpCenterService.deleteHelpCenterByIds(id);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 根据id查询帮助中心内容 
    * @Param: [id] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/4/2 
    */ 
    @GetMapping("/selectContentById")
    @ApiOperation(value = HelpCenterApi.Select.METHOD_TITLE_NAME, notes = HelpCenterApi.Select.METHOD_TITLE_NOTE)
    public ResultDTO selectContentById(@ApiParam(HelpCenterApi.Select.METHOD_API_ID) String id) {
        return ResultDTO.requstSuccess(helpCenterService.selectContentById(id));
    }
}
