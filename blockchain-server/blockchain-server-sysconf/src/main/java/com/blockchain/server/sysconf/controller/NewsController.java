package com.blockchain.server.sysconf.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.sysconf.common.constant.NewsConstant;
import com.blockchain.server.sysconf.dto.NewsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.News;
import com.blockchain.server.sysconf.service.NewsService;
import com.blockchain.server.sysconf.controller.api.SysconfImageApi;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 公告/快讯 信息操作处理
 *
 * @author ruoyi
 * @date 2018-10-29
 */
@RestController
@RequestMapping("/news")
public class NewsController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    /**
     * 查询公告/快讯列表
     */
    @GetMapping("/list")
    @ApiOperation(value = SysconfImageApi.SystemImageList.METHOD_TITLE_NAME, notes = SysconfImageApi.SystemImageList.METHOD_TITLE_NOTE)
    public ResultDTO list(@ApiParam(SysconfImageApi.SystemImageList.METHOD_API_STATUS) @RequestParam("type") Integer type,
                          @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                          @ApiParam(SysconfImageApi.SystemImageList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(newsService.listAll(type));
    }

    /**
     * 新增保存公告/快讯
     */
    @PostMapping("/add")
    @ResponseBody
    public ResultDTO addSave(News news) {
        String userId = SecurityUtils.getUserId();
        news.setUserId(userId);
        newsService.insertNewsForBackend(news);
        return ResultDTO.requstSuccess();
    }

    /**
     * 修改保存公告/快讯
     */
    @PostMapping("/edit")
    @ResponseBody
    public ResultDTO editSave(News news) {
        newsService.updateNews(news.getId(), news.getTitle(), news.getContent(), news.getUrl());
        return ResultDTO.requstSuccess();
    }

    /**
     * 删除公告/快讯
     */
    @PostMapping( "/remove")
    @ResponseBody
    public ResultDTO remove(String ids)
    {
        newsService.deleteNewsByIds(ids);
        return ResultDTO.requstSuccess();
    }


}
