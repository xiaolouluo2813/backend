package com.blockchain.server.eth.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.base.security.RequiresPermissions;
import com.blockchain.server.eth.controller.api.EthConfigWalletParamControllerApi;
import com.blockchain.server.eth.service.EthConfigWalletParamService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @author: Liusd
 * @create: 2019-03-22 11:43
 **/
@Api(EthConfigWalletParamControllerApi.CONTROLLER_API)
@RestController
@RequestMapping("/eth/gas")
public class EthConfigWalletParamController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(EthConfigWalletParamController.class);

    @Autowired
    private EthConfigWalletParamService ethConfigWalletParamService;

    /**
    * @Description: 提现手续费列表
    * @Param: []
    * @return: com.blockchain.common.base.dto.ResultDTO
    * @Author: Liu.sd
    * @Date: 2019/3/22
    */
    @GetMapping("/ethConfigWalletParamList")
    @ApiOperation(value = EthConfigWalletParamControllerApi.EthConfigWalletParamList.METHOD_TITLE_NAME, notes = EthConfigWalletParamControllerApi.EthConfigWalletParamList.METHOD_TITLE_NOTE)
    public ResultDTO ethConfigWalletParamList() {
        return generatePage(ethConfigWalletParamService.ethConfigWalletParamList());
    }

    /**
    * @Description:  修改提现手续费
    * @Param: [gas, id, request]
    * @return: com.blockchain.common.base.dto.ResultDTO
    * @Author: Liu.sd
    * @Date: 2019/3/22
    */
    @PostMapping("/updateConfigWallet")
    @RequiresPermissions("UpdWithdraw")
    @ApiOperation(value = EthConfigWalletParamControllerApi.UpdateConfigWallet.METHOD_TITLE_NAME, notes = EthConfigWalletParamControllerApi.UpdateConfigWallet.METHOD_TITLE_NOTE)
    public ResultDTO updateConfigWallet(@ApiParam(EthConfigWalletParamControllerApi.UpdateConfigWallet.METHOD_API_GASDTO) @RequestParam("gasPrice") BigDecimal gasPrice,
                                      @ApiParam(EthConfigWalletParamControllerApi.UpdateConfigWallet.METHOD_API_ID) @RequestParam("minWdAmount") BigDecimal minWdAmount,
                                      @ApiParam(EthConfigWalletParamControllerApi.UpdateConfigWallet.METHOD_API_ID) @RequestParam("id") Integer id,
                                      HttpServletRequest request) {
        ethConfigWalletParamService.updateConfigWallet(gasPrice,minWdAmount, id);
        return ResultDTO.requstSuccess();
    }
}
