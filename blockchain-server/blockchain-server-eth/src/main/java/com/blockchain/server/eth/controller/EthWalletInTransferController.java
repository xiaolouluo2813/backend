package com.blockchain.server.eth.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.eth.common.constants.tx.InTxConstants;
import com.blockchain.server.eth.common.constants.tx.OutTxConstants;
import com.blockchain.server.eth.common.util.ParamsUtil;
import com.blockchain.server.eth.controller.api.EthWalletInTxApi;
import com.blockchain.server.eth.controller.api.EthWalletInTxApi;
import com.blockchain.server.eth.service.IEthWalletTransferService;
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
@Api(EthWalletInTxApi.API_NAME)
@RestController
@RequestMapping("/inTx")
public class EthWalletInTransferController extends BaseController {
    @Autowired
    IEthWalletTransferService ethWalletTransferService;

    @ApiOperation(value = EthWalletInTxApi.Success.METHOD_TITLE_NAME, notes = EthWalletInTxApi.Success.METHOD_TITLE_NOTE)
    @PostMapping("/success")
    public ResultDTO success(@ApiParam(EthWalletInTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                             @ApiParam(EthWalletInTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @ApiParam(EthWalletInTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(InTxConstants.SUCCESS);
        List<WalletTxDTO> list = ethWalletTransferService.selectInTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EthWalletInTxApi.Error.METHOD_TITLE_NAME, notes = EthWalletInTxApi.Error.METHOD_TITLE_NOTE)
    @PostMapping("/error")
    public ResultDTO error(@ApiParam(EthWalletInTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                           @ApiParam(EthWalletInTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @ApiParam(EthWalletInTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(InTxConstants.ERROR);
        List<WalletTxDTO> list = ethWalletTransferService.selectInTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EthWalletInTxApi.All.METHOD_TITLE_NAME, notes = EthWalletInTxApi.All.METHOD_TITLE_NOTE)
    @PostMapping("/all")
    public ResultDTO all(@ApiParam(EthWalletInTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                         @ApiParam(EthWalletInTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @ApiParam(EthWalletInTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = ethWalletTransferService.selectInTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EthWalletInTxApi.selectById.METHOD_TITLE_NAME,
            notes = EthWalletInTxApi.selectById.METHOD_TITLE_NOTE)
    @GetMapping("/selectById")
    public ResultDTO selectById(@ApiParam(EthWalletInTxApi.selectById.METHOD_API_ID) @RequestParam("id") String id) {
        WalletTxDTO inTransfer = ethWalletTransferService.findInTransfer(id);
        return ResultDTO.requstSuccess(inTransfer);
    }

}
