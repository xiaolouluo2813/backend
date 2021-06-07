package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.MarketApplyApi;
import com.blockchain.server.otc.dto.marketapply.ListMarketApplyResultDTO;
import com.blockchain.server.otc.service.MarketApplyService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(MarketApplyApi.MARKET_APPLY_API)
@RestController
@RequestMapping("/marketApply")
public class MarketApplyController extends BaseController {

    @Autowired
    private MarketApplyService marketApplyService;

    @ApiOperation(value = MarketApplyApi.list.METHOD_TITLE_NAME,
            notes = MarketApplyApi.list.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO list(@ApiParam(MarketApplyApi.list.METHOD_API_USER_NAME) @RequestParam(value = "userName", required = false) String userName,
                          @ApiParam(MarketApplyApi.list.METHOD_API_COIN_NAME) @RequestParam(value = "coinName", required = false) String coinName,
                          @ApiParam(MarketApplyApi.list.METHOD_API_STATUS) @RequestParam(value = "status", required = false) String status,
                          @ApiParam(MarketApplyApi.list.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                          @ApiParam(MarketApplyApi.list.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                          @ApiParam(MarketApplyApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                          @ApiParam(MarketApplyApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListMarketApplyResultDTO> list = marketApplyService.list(userName, coinName, status, beginTime, endTime);
        return generatePage(list);
    }

    @ApiOperation(value = MarketApplyApi.AgreeApply.METHOD_TITLE_NAME,
            notes = MarketApplyApi.AgreeApply.METHOD_TITLE_NOTE)
    @PostMapping("/agreeApply")
    public ResultDTO agreeApply(@ApiParam(MarketApplyApi.AgreeApply.METHOD_API_ID) @RequestParam(value = "id") String id) {
        marketApplyService.agreeApply(id, getSysUserId(), getIpAddress());
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = MarketApplyApi.RejectApply.METHOD_TITLE_NAME,
            notes = MarketApplyApi.RejectApply.METHOD_TITLE_NOTE)
    @PostMapping("/rejectApply")
    public ResultDTO rejectApply(@ApiParam(MarketApplyApi.RejectApply.METHOD_API_ID) @RequestParam(value = "id") String id) {
        marketApplyService.rejectApply(id, getSysUserId(), getIpAddress());
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = MarketApplyApi.GetById.METHOD_TITLE_NAME,
            notes = MarketApplyApi.GetById.METHOD_TITLE_NOTE)
    @GetMapping("/getById")
    public ResultDTO getById(@ApiParam(MarketApplyApi.GetById.METHOD_API_ID) @RequestParam(value = "id") String id) {
        return ResultDTO.requstSuccess(marketApplyService.getById(id));
    }

    /***
     * 获取管理员ip地址
     * @return
     */
    private String getIpAddress() {
        return HttpRequestUtil.getIpAddr();
    }

    /***
     * 获取管理员id
     * @return
     */
    private String getSysUserId() {
        return SecurityUtils.getUserId();
    }
}
