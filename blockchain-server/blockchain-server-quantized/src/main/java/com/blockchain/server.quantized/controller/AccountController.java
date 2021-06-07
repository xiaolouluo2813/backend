package com.blockchain.server.quantized.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.quantized.controller.api.TradingOnApi;
import com.blockchain.server.quantized.service.AccountService;
import com.blockchain.server.quantized.service.TradingOnService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Liusd
 * @create: 2019-04-18 15:54
 **/
@RestController
@RequestMapping("/account")
@Api(TradingOnApi.CONTROLLER_API)
public class AccountController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    /**
    * @Description: 列表
    * @Param: [pageNum, pageSize]
    * @return: com.blockchain.common.base.dto.ResultDTO
    * @Author: Liu.sd
    * @Date: 2019/4/18
    */
    @GetMapping("/list")
    @ApiOperation(value = TradingOnApi.List.METHOD_TITLE_NAME, notes = TradingOnApi.List.METHOD_TITLE_NOTE)
    public ResultDTO list(@ApiParam(TradingOnApi.List.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                                      @ApiParam(TradingOnApi.List.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(accountService.list());
    }
    /** 
    * @Description: 修改
    * @Param: [id, state, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/4/24 
    */ 
    @PostMapping("/update")
    @ApiOperation(value = TradingOnApi.UpdateState.METHOD_TITLE_NAME, notes = TradingOnApi.UpdateState.METHOD_TITLE_NOTE)
    public ResultDTO update(@ApiParam(TradingOnApi.UpdateState.METHOD_API_ID) @RequestParam("id") String id,@ApiParam(TradingOnApi.UpdateState.METHOD_API_STATE) @RequestParam("apiKey") String apiKey,@ApiParam(TradingOnApi.UpdateState.METHOD_API_ID) @RequestParam("secretKey") String secretKey,HttpServletRequest request) {
        accountService.update(id,apiKey,secretKey);
        return ResultDTO.requstSuccess();
    }


}
