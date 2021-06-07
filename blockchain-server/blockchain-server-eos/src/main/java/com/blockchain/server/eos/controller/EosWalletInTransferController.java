package com.blockchain.server.eos.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.eos.constants.tx.InTxConstants;
import com.blockchain.server.eos.controller.api.EosWalletInTxApi;
import com.blockchain.server.eos.controller.api.EosWalletOutTxApi;
import com.blockchain.server.eos.service.IEosWalletTransferService;
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
@Api(EosWalletInTxApi.API_NAME)
@RestController
@RequestMapping("/inTx")
public class EosWalletInTransferController extends BaseController {
    @Autowired
    IEosWalletTransferService ethWalletTransferService;

    @ApiOperation(value = EosWalletInTxApi.Success.METHOD_TITLE_NAME, notes =
            EosWalletInTxApi.Success.METHOD_TITLE_NOTE)
    @PostMapping("/success")
    public ResultDTO success(@ApiParam(EosWalletInTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                             @ApiParam(EosWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @ApiParam(EosWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(InTxConstants.SUCCESS);
        List<WalletTxDTO> list = ethWalletTransferService.selectInTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EosWalletInTxApi.Error.METHOD_TITLE_NAME, notes = EosWalletInTxApi.Error.METHOD_TITLE_NOTE)
    @PostMapping("/error")
    public ResultDTO error(@ApiParam(EosWalletInTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                           @ApiParam(EosWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @ApiParam(EosWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(InTxConstants.ERROR);
        List<WalletTxDTO> list = ethWalletTransferService.selectInTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EosWalletInTxApi.All.METHOD_TITLE_NAME, notes = EosWalletInTxApi.All.METHOD_TITLE_NOTE)
    @PostMapping("/all")
    public ResultDTO all(@ApiParam(EosWalletInTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                         @ApiParam(EosWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @ApiParam(EosWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = ethWalletTransferService.selectInTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EosWalletInTxApi.selectById.METHOD_TITLE_NAME,
            notes = EosWalletInTxApi.selectById.METHOD_TITLE_NOTE)
    @GetMapping("/selectById")
    public ResultDTO selectById(@ApiParam(EosWalletInTxApi.selectById.METHOD_API_ID) @RequestParam("id") String id) {
        WalletTxDTO inTransfer = ethWalletTransferService.findInTransfer(id);
        return ResultDTO.requstSuccess(inTransfer);
    }

}
