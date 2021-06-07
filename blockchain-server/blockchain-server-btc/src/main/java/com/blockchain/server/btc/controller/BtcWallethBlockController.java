package com.blockchain.server.btc.controller;

import com.blockchain.common.base.constant.WalletConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.wallet.WalletBaseDTO;
import com.blockchain.common.base.dto.wallet.WalletParamsDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.btc.controller.api.BtcWalletBlockApi;
import com.blockchain.server.btc.rpc.BtcUtils;
import com.blockchain.server.btc.service.IBtcWalletService;
import com.blockchain.server.btc.service.IConfigWalletParamService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Liusd
 * @create: 2019-03-23 10:54
 **/
@Api(BtcWalletBlockApi.API_NAME)
@RestController
@RequestMapping("/walletBlock")
public class BtcWallethBlockController extends BaseController {

    @Autowired
    IBtcWalletService walletService;


    @ApiOperation(value = BtcWalletBlockApi.All.METHOD_TITLE_NAME, notes = BtcWalletBlockApi.All.METHOD_TITLE_NOTE)
    @PostMapping("/all")
    public ResultDTO all(@ApiParam(BtcWalletBlockApi.METHOD_API_PARAM) WalletParamsDTO paramsDTO,
                         @ApiParam(BtcWalletBlockApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @ApiParam(BtcWalletBlockApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WalletBaseDTO> list = walletService.selectBlock(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletBlockApi.TotalBtc.METHOD_TITLE_NAME, notes = BtcWalletBlockApi.TotalBtc.METHOD_TITLE_NOTE)
    @GetMapping("/totalBtc")
    public ResultDTO totalBtc() {
        Map<String, String> map = walletService.totalBtc();
        return ResultDTO.requstSuccess(map);
    }

    @ApiOperation(value = BtcWalletBlockApi.GoBtcTx.METHOD_TITLE_NAME, notes = BtcWalletBlockApi.GoBtcTx.METHOD_TITLE_NOTE)
    @PostMapping("/goBtcTx")
    public ResultDTO goBtcTx(@RequestParam(value = "toAddr") String toAddr) {
        walletService.goBtcTx(toAddr);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = BtcWalletBlockApi.GetGasWallet.METHOD_TITLE_NAME, notes = BtcWalletBlockApi.GetGasWallet.METHOD_TITLE_NOTE)
    @GetMapping("/getGasWallet")
    public ResultDTO getGasWallet() {
        return ResultDTO.requstSuccess(walletService.getGasWallet());
    }

    @GetMapping("/getBtc")
    public ResultDTO getBtc(@RequestParam(value = "addr") String addr){
        return  ResultDTO.requstSuccess(walletService.getBtc(addr));
    }

//    @ApiOperation(value = BtcWalletBlockApi.GetGas.METHOD_TITLE_NAME, notes = BtcWalletBlockApi.GetGas.METHOD_TITLE_NOTE)
//    @PostMapping("/getGas")
//    public ResultDTO getGas(@RequestParam(value = "from") String fromAddr,
//                            @RequestParam(value = "coinName") String tokenName) {
//        return ResultDTO.requstSuccess(walletService.getGas(fromAddr,fromAddr, tokenName));
//    }

    @ApiOperation(value = BtcWalletBlockApi.GoTx.METHOD_TITLE_NAME, notes = BtcWalletBlockApi.GoTx.METHOD_TITLE_NOTE)
    @PostMapping("/goTx")
    public ResultDTO goTx(@RequestParam(value = "from") String fromAddr,
                          @RequestParam(value = "to") String toAddr ) {
        walletService.rpcTx(fromAddr,toAddr);
        return ResultDTO.requstSuccess();
    }


}
