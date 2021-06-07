package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.sysconf.common.constant.UserConstant;
import com.blockchain.server.sysconf.controller.api.SysconfImageApi;
import com.blockchain.server.sysconf.dto.AboutUsQueryConditionDTO;
import com.blockchain.server.sysconf.service.AboutUsService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 关于我们 信息操作处理
 *
 * @author ruoyi
 * @date 2018-10-29
 */
@RestController
@RequestMapping("/backend/aboutUs")
public class AboutUsController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(AboutUsController.class);

    @Autowired
    private AboutUsService aboutUsService;

    /**
     * 查询关于我们列表
     */
    @GetMapping("/list")
    @ApiOperation(value = SysconfImageApi.SystemImageList.METHOD_TITLE_NAME, notes = SysconfImageApi.SystemImageList.METHOD_TITLE_NOTE)
    public ResultDTO list(@ApiParam(SysconfImageApi.SystemImageList.METHOD_API_STATUS) AboutUsQueryConditionDTO aboutUsQueryConditionDTO,
                                     @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                                     @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(aboutUsService.selectAboutUsList(aboutUsQueryConditionDTO));
    }


    /**
     * 新增保存关于我们
     */
    @PostMapping("/add")
    @ResponseBody
    public ResultDTO addSave(@RequestParam("content") String content) {
        aboutUsService.saveAboutUs(content, UserConstant.USER_LOCAL_CHINA);
        return ResultDTO.requstSuccess();
    }

    /**
     * 修改保存关于我们
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResultDTO editSave(
            @RequestParam("id") String id,
            @RequestParam("content") String content,
            @RequestParam("languages") String languages) {

       aboutUsService.updateAboutUs(id, content, languages);
        return ResultDTO.requstSuccess();
    }

//	/**
//	 * 删除关于我们
//	 */
//	@RequiresPermissions("app:aboutUs:remove")
//	@Log(title = "关于我们", businessType = BusinessType.DELETE)
//	@PostMapping( "/remove")
//	@ResponseBody
//	public AjaxResult remove(String ids)
//	{
//		return toAjax(aboutUsService.deleteAboutUsByIds(ids));
//	}

}
