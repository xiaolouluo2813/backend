package com.blockchain.server.btc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.btc.common.constants.InTxConstants;
import com.blockchain.server.btc.controller.api.BtcWalletInTxApi;
import com.blockchain.server.btc.controller.api.BtcWalletOutTxApi;
import com.blockchain.server.btc.service.IBtcWalletTransferService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 充值记录查询接口
 *
 * @author YH
 * @date 2019年3月9日09:50:24
 */
@Api(BtcWalletInTxApi.API_NAME)
@RestController
@RequestMapping("/inTx")
public class BtcWalletInTransferController extends BaseController {
    @Autowired
    IBtcWalletTransferService btcWalletTransferService;

    @ApiOperation(value = BtcWalletInTxApi.Success.METHOD_TITLE_NAME, notes = BtcWalletInTxApi.Success.METHOD_TITLE_NOTE)
    @PostMapping("/success")
    public ResultDTO success(@ApiParam(BtcWalletInTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                             @ApiParam(BtcWalletInTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @ApiParam(BtcWalletInTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        paramsDTO.setTxStatus(InTxConstants.SUCCESS);
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = btcWalletTransferService.selectInTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletInTxApi.Error.METHOD_TITLE_NAME, notes = BtcWalletInTxApi.Error.METHOD_TITLE_NOTE)
    @PostMapping("/error")
    public ResultDTO error(@ApiParam(BtcWalletInTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                           @ApiParam(BtcWalletInTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @ApiParam(BtcWalletInTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        paramsDTO.setTxStatus(InTxConstants.ERROR);
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = btcWalletTransferService.selectInTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletInTxApi.All.METHOD_TITLE_NAME, notes = BtcWalletInTxApi.All.METHOD_TITLE_NOTE)
    @PostMapping("/all")
    public ResultDTO all(@ApiParam(BtcWalletInTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                         @ApiParam(BtcWalletInTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @ApiParam(BtcWalletInTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = btcWalletTransferService.selectInTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletInTxApi.selectById.METHOD_TITLE_NAME,
            notes = BtcWalletInTxApi.selectById.METHOD_TITLE_NOTE)
    @GetMapping("/selectById")
    public ResultDTO selectById(@ApiParam(BtcWalletInTxApi.selectById.METHOD_API_ID) @RequestParam("id") String id) {
        WalletTxDTO inTransfer = btcWalletTransferService.findInTransfer(id);
        return ResultDTO.requstSuccess(inTransfer);
    }
}
