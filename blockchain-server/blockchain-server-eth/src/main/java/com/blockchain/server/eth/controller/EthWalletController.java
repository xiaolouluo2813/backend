package com.blockchain.server.eth.controller;

import com.blockchain.common.base.constant.WalletConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.wallet.WalletBaseDTO;
import com.blockchain.common.base.dto.wallet.WalletParamsDTO;
import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.base.security.RequiresPermissions;
import com.blockchain.server.eth.controller.api.EthTokenApi;
import com.blockchain.server.eth.controller.api.EthWalletApi;
import com.blockchain.server.eth.controller.api.EthWalletInTxApi;
import com.blockchain.server.eth.entity.EthToken;
import com.blockchain.server.eth.entity.EthWallet;
import com.blockchain.server.eth.service.IEthTokenService;
import com.blockchain.server.eth.service.IEthWalletService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * 提现记录查询接口
 *
 * @author YH
 * @date 2019年3月9日09:50:24
 */
@Api(EthWalletApi.API_NAME)
@RestController
@RequestMapping("/wallet")
public class EthWalletController extends BaseController {
    @Autowired
    IEthWalletService walletService;


    @ApiOperation(value = EthWalletApi.CCT.METHOD_TITLE_NAME, notes = EthWalletApi.CCT.METHOD_TITLE_NOTE)
    @PostMapping("/cct")
    public ResultDTO cct(@ApiParam(EthWalletApi.METHOD_API_PARAM) WalletParamsDTO paramsDTO,
                         @ApiParam(EthWalletApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @ApiParam(EthWalletApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setWalletType(WalletConstant.WALLET_CCT);
        List<WalletBaseDTO> list = walletService.select(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EthWalletApi.All.METHOD_TITLE_NAME, notes = EthWalletApi.All.METHOD_TITLE_NOTE)
    @PostMapping("/all")
    public ResultDTO all(@ApiParam(EthWalletApi.METHOD_API_PARAM) WalletParamsDTO paramsDTO,
                         @ApiParam(EthWalletApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @ApiParam(EthWalletApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WalletBaseDTO> list = walletService.select(paramsDTO);
        return generatePage(list);
    }
    @ApiOperation(value = EthWalletApi.Add.METHOD_TITLE_NAME, notes = EthWalletApi.Add.METHOD_TITLE_NOTE)
    @PostMapping("/add")
    @RequiresPermissions("OperationShowAdd")
    public ResultDTO add(@ApiParam(EthWalletApi.METHOD_API_ADDR) @RequestParam(value = "addr", required = false) String addr,
                         @ApiParam(EthWalletApi.METHOD_API_COINNAME) @RequestParam(value = "coinName", required = false) String coinName,
                         @ApiParam(EthWalletApi.METHOD_API_FREEBLANCE) @RequestParam(value = "freeBlance", required = false)String freeBlance,
                         @ApiParam(EthWalletApi.METHOD_API_FREEZEBLANCE) @RequestParam(value = "freezeBlance", required = false)String freezeBlance) {
         walletService.updateBlance(addr,coinName,freeBlance,freezeBlance, WalletConstant.AMOUNT_ADD);
         return ResultDTO.requstSuccess();
    }
    @ApiOperation(value = EthWalletApi.Sud.METHOD_TITLE_NAME, notes = EthWalletApi.Sud.METHOD_TITLE_NOTE)
    @PostMapping("/sud")
    @RequiresPermissions("OperationShowSub")
    public ResultDTO sud(@ApiParam(EthWalletApi.METHOD_API_ADDR) @RequestParam(value = "addr", required = false) String addr,
                         @ApiParam(EthWalletApi.METHOD_API_COINNAME) @RequestParam(value = "coinName", required = false) String coinName,
                         @ApiParam(EthWalletApi.METHOD_API_FREEBLANCE) @RequestParam(value = "freeBlance", required = false)String freeBlance,
                         @ApiParam(EthWalletApi.METHOD_API_FREEZEBLANCE) @RequestParam(value = "freezeBlance", required = false)String freezeBlance) {
        walletService.updateBlance(addr,coinName,freeBlance,freezeBlance, WalletConstant.AMOUNT_SUD);
        return ResultDTO.requstSuccess();
    }


}
