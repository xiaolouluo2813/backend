package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.MarketUserApi;
import com.blockchain.server.otc.dto.marketuser.ListMarketUserResultDTO;
import com.blockchain.server.otc.service.MarketUserService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Api(MarketUserApi.MARKET_USER_API)
@RestController
@RequestMapping("/marketUser")
public class MarketUserController extends BaseController {

    @Autowired
    private MarketUserService marketUserService;

    @ApiOperation(value = MarketUserApi.List.METHOD_TITLE_NAME,
            notes = MarketUserApi.List.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO list(@ApiParam(MarketUserApi.List.METHOD_API_USER_NAME) @RequestParam(value = "userName", required = false) String userName,
                          @ApiParam(MarketUserApi.List.METHOD_API_USER_NAME) @RequestParam(value = "status", required = false) String status,
                          @ApiParam(MarketUserApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                          @ApiParam(MarketUserApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListMarketUserResultDTO> list = marketUserService.list(userName, status);
        return generatePage(list);
    }

    @ApiOperation(value = MarketUserApi.BecomeMarketUser.METHOD_TITLE_NAME,
            notes = MarketUserApi.BecomeMarketUser.METHOD_TITLE_NOTE)
    @PostMapping("/becomeMarketUser")
    public ResultDTO becomeMarketUser(@ApiParam(MarketUserApi.BecomeMarketUser.METHOD_API_ID) @RequestParam(value = "id") String id,
                                      @ApiParam(MarketUserApi.BecomeMarketUser.METHOD_API_AMOUNT) @RequestParam(value = "amount") BigDecimal amount,
                                      @ApiParam(MarketUserApi.BecomeMarketUser.METHOD_API_COIN) @RequestParam(value = "coin") String coin) {
        marketUserService.becomeMarketUser(id, amount, coin, getIpAddress(), getSysUserId());
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = MarketUserApi.CancelMarketUserById.METHOD_TITLE_NAME,
            notes = MarketUserApi.CancelMarketUserById.METHOD_TITLE_NOTE)
    @PostMapping("/cancelMarketUser")
    public ResultDTO cancelMarketUser(@ApiParam(MarketUserApi.CancelMarketUserById.METHOD_API_ID) @RequestParam(value = "id") String id) {
        marketUserService.cancelMarketUserById(id, getIpAddress(), getSysUserId());
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = MarketUserApi.InsertMarketUser.METHOD_TITLE_NAME,
            notes = MarketUserApi.InsertMarketUser.METHOD_TITLE_NOTE)
    @PostMapping("/insertMarketUser")
    public ResultDTO insertMarketUser(@ApiParam(MarketUserApi.InsertMarketUser.METHOD_API_USER_NAME) @RequestParam(value = "userName") String userName,
                                      @ApiParam(MarketUserApi.InsertMarketUser.METHOD_API_AMOUNT) @RequestParam(value = "amount") BigDecimal amount,
                                      @ApiParam(MarketUserApi.InsertMarketUser.METHOD_API_COIN) @RequestParam(value = "coin") String coin) {
        marketUserService.insertMarketUserByUserName(userName, amount, coin, getIpAddress(), getSysUserId());
        return ResultDTO.requstSuccess();
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
