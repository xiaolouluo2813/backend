package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.sysconf.dto.ContactUsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.ContactUs;
import com.blockchain.server.sysconf.service.ContactUsService;
import com.blockchain.server.sysconf.controller.api.ContactUsApi;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 联系我们 信息操作处理
 *
 * @author ruoyi
 * @date 2018-10-29
 */
@RestController
@Api(ContactUsApi.CONTROLLER_API)
@RequestMapping("/contactUs")
public class ContactUsController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ContactUsController.class);

    @Autowired
    private ContactUsService contactUsService;

    /**
     * 查询联系我们列表
     */
    @GetMapping("/list")
    @ApiOperation(value = ContactUsApi.List.METHOD_TITLE_NAME, notes = ContactUsApi.List.METHOD_TITLE_NOTE)
    public ResultDTO list(@ApiParam(ContactUsApi.List.METHOD_API_CONTACTUSDTO) ContactUsQueryConditionDTO contactUsQueryConditionDTO,
                          @ApiParam(ContactUsApi.List.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                          @ApiParam(ContactUsApi.List.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(contactUsService.selectContactUsList(contactUsQueryConditionDTO));
    }

    /**
     * 新增保存联系我们
     */
    @PostMapping("/add")
    @ResponseBody
    public ResultDTO addSave(ContactUs contactUs) {
        contactUsService.saveContactUs(contactUs.getContactName(), contactUs.getContactValue(), contactUs.getUserLocal(), contactUs.getShowStatus(), contactUs.getRank());
        return ResultDTO.requstSuccess();
    }

    /**
     * 修改保存联系我们
     */
    @PostMapping("/edit")
    @ApiOperation(value = ContactUsApi.Edit.METHOD_TITLE_NAME, notes = ContactUsApi.Edit.METHOD_TITLE_NOTE)
    public ResultDTO editSave(@ApiParam(ContactUsApi.Edit.METHOD_API_CONTACTUS) ContactUs contactUs) {
        contactUsService.updateContactUs(contactUs.getId(), contactUs.getContactName(), contactUs.getContactValue(), contactUs.getUserLocal(), contactUs.getShowStatus(), contactUs.getRank());
        return ResultDTO.requstSuccess();
    }

    /**
     * 删除联系我们
     */
    @PostMapping("/remove")
    @ApiOperation(value = ContactUsApi.Remove.METHOD_TITLE_NAME, notes = ContactUsApi.Remove.METHOD_TITLE_NOTE)
    public ResultDTO remove(@ApiParam(ContactUsApi.Remove.METHOD_API_ID) @RequestParam("id") String id) {
        contactUsService.deleteContactUs(id);
        return ResultDTO.requstSuccess();
    }

}
