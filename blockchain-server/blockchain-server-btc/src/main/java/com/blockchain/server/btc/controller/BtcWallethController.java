package com.blockchain.server.btc.controller;

import com.blockchain.common.base.constant.WalletConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.wallet.WalletBaseDTO;
import com.blockchain.common.base.dto.wallet.WalletParamsDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.base.security.RequiresPermissions;
import com.blockchain.server.btc.controller.api.BtcWalletApi;
import com.blockchain.server.btc.service.IBtcWalletService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-23 10:54
 **/
@Api(BtcWalletApi.API_NAME)
@RestController
@RequestMapping("/wallet")
public class BtcWallethController extends BaseController {

    @Autowired
    IBtcWalletService walletService;

    @ApiOperation(value = BtcWalletApi.CCT.METHOD_TITLE_NAME, notes = BtcWalletApi.CCT.METHOD_TITLE_NOTE)
    @PostMapping("/cct")
    public ResultDTO cct(@ApiParam(BtcWalletApi.METHOD_API_PARAM) WalletParamsDTO paramsDTO,
                         @ApiParam(BtcWalletApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @ApiParam(BtcWalletApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setWalletType(WalletConstant.WALLET_CCT);
        List<WalletBaseDTO> list = walletService.select(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletApi.All.METHOD_TITLE_NAME, notes = BtcWalletApi.All.METHOD_TITLE_NOTE)
    @PostMapping("/all")
    public ResultDTO all(@ApiParam(BtcWalletApi.METHOD_API_PARAM) WalletParamsDTO paramsDTO,
                         @ApiParam(BtcWalletApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @ApiParam(BtcWalletApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WalletBaseDTO> list = walletService.select(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletApi.Add.METHOD_TITLE_NAME, notes = BtcWalletApi.Add.METHOD_TITLE_NOTE)
    @PostMapping("/add")
    @RequiresPermissions("OperationShowAdd")
    public ResultDTO add(@ApiParam(BtcWalletApi.METHOD_API_ADDR) @RequestParam(value = "addr", required = false) String addr,
                         @ApiParam(BtcWalletApi.METHOD_API_COINNAME) @RequestParam(value = "coinName", required = false) String coinName,
                         @ApiParam(BtcWalletApi.METHOD_API_FREEBLANCE) @RequestParam(value = "freeBlance", required = false)String freeBlance,
                         @ApiParam(BtcWalletApi.METHOD_API_FREEZEBLANCE) @RequestParam(value = "freezeBlance", required = false)String freezeBlance) {
        walletService.updateBlance(addr,coinName,freeBlance,freezeBlance, WalletConstant.AMOUNT_ADD);
        return ResultDTO.requstSuccess();
    }
    @ApiOperation(value = BtcWalletApi.Sud.METHOD_TITLE_NAME, notes = BtcWalletApi.Sud.METHOD_TITLE_NOTE)
    @PostMapping("/sud")
    @RequiresPermissions("OperationShowSub")
    public ResultDTO sud(@ApiParam(BtcWalletApi.METHOD_API_ADDR) @RequestParam(value = "addr", required = false) String addr,
                         @ApiParam(BtcWalletApi.METHOD_API_COINNAME) @RequestParam(value = "coinName", required = false) String coinName,
                         @ApiParam(BtcWalletApi.METHOD_API_FREEBLANCE) @RequestParam(value = "freeBlance", required = false)String freeBlance,
                         @ApiParam(BtcWalletApi.METHOD_API_FREEZEBLANCE) @RequestParam(value = "freezeBlance", required = false)String freezeBlance) {
        walletService.updateBlance(addr,coinName,freeBlance,freezeBlance, WalletConstant.AMOUNT_SUD);
        return ResultDTO.requstSuccess();
    }


}
