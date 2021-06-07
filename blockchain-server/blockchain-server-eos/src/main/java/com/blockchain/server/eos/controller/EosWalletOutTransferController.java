package com.blockchain.server.eos.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.eos.constants.tx.OutTxConstants;
import com.blockchain.server.eos.controller.api.EosWalletInTxApi;
import com.blockchain.server.eos.controller.api.EosWalletOutTxApi;
import com.blockchain.server.eos.service.IEosWalletTransferService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 提现记录查询接口
 *
 * @author YH
 * @date 2019年3月9日09:50:24
 */
@Api(EosWalletOutTxApi.API_NAME)
@RestController
@RequestMapping("/outTx")
public class EosWalletOutTransferController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(EosWalletOutTransferController.class);
    @Autowired
    IEosWalletTransferService eosWalletTransferService;

    @ApiOperation(value = EosWalletOutTxApi.Check.METHOD_TITLE_NAME, notes = EosWalletOutTxApi.Check.METHOD_TITLE_NOTE)
    @PostMapping("/check")
    public ResultDTO check(@ApiParam(EosWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                           @ApiParam(EosWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @ApiParam(EosWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(OutTxConstants.CHECK);
        List<WalletTxDTO> list = eosWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EosWalletOutTxApi.Recheck.METHOD_TITLE_NAME, notes =
            EosWalletOutTxApi.Recheck.METHOD_TITLE_NOTE)
    @PostMapping("/recheck")
    public ResultDTO recheck(@ApiParam(EosWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                             @ApiParam(EosWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @ApiParam(EosWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(OutTxConstants.RECHECK);
        List<WalletTxDTO> list = eosWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EosWalletOutTxApi.Pack.METHOD_TITLE_NAME, notes = EosWalletOutTxApi.Pack.METHOD_TITLE_NOTE)
    @PostMapping("/pack")
    public ResultDTO pack(@ApiParam(EosWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                          @ApiParam(EosWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @ApiParam(EosWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(OutTxConstants.PACK);
        List<WalletTxDTO> list = eosWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EosWalletOutTxApi.Success.METHOD_TITLE_NAME, notes =
            EosWalletOutTxApi.Success.METHOD_TITLE_NOTE)
    @PostMapping("/success")
    public ResultDTO success(@ApiParam(EosWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                             @ApiParam(EosWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @ApiParam(EosWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(OutTxConstants.SUCCESS);
        List<WalletTxDTO> list = eosWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EosWalletOutTxApi.Error.METHOD_TITLE_NAME, notes = EosWalletOutTxApi.Error.METHOD_TITLE_NOTE)
    @PostMapping("/error")
    public ResultDTO error(@ApiParam(EosWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                           @ApiParam(EosWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @ApiParam(EosWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(OutTxConstants.ERROR);
        List<WalletTxDTO> list = eosWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EosWalletOutTxApi.Reject.METHOD_TITLE_NAME, notes =
            EosWalletOutTxApi.Reject.METHOD_TITLE_NOTE)
    @PostMapping("/reject")
    public ResultDTO reject(@ApiParam(EosWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                            @ApiParam(EosWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @ApiParam(EosWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(OutTxConstants.REJECT);
        List<WalletTxDTO> list = eosWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EosWalletOutTxApi.All.METHOD_TITLE_NAME, notes = EosWalletOutTxApi.All.METHOD_TITLE_NOTE)
    @PostMapping("/all")
    public ResultDTO all(@ApiParam(EosWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                         @ApiParam(EosWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @ApiParam(EosWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = eosWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EosWalletOutTxApi.SelectById.METHOD_TITLE_NAME,
            notes = EosWalletOutTxApi.SelectById.METHOD_TITLE_NOTE)
    @GetMapping("/selectById")
    public ResultDTO selectById(@ApiParam(EosWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id) {
        WalletTxDTO inTransfer = eosWalletTransferService.findOutTransfer(id);
        return ResultDTO.requstSuccess(inTransfer);
    }

    @ApiOperation(value = EosWalletOutTxApi.CheckPass.METHOD_TITLE_NAME,
            notes = EosWalletOutTxApi.CheckPass.METHOD_TITLE_NOTE)
    @PostMapping("/checkPass")
    public ResultDTO checkPass(@ApiParam(EosWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
        eosWalletTransferService.updateStatus(id,OutTxConstants.RECHECK);
        return  ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EosWalletOutTxApi.RecheckPass.METHOD_TITLE_NAME,
            notes = EosWalletOutTxApi.RecheckPass.METHOD_TITLE_NOTE)
    @PostMapping("/recheckPass")
    public ResultDTO recheckPass(@ApiParam(EosWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
        eosWalletTransferService.handleOut(id,OutTxConstants.PACK);
//        eosWalletTransferService.updateStatus(id,OutTxConstants.PACK);
        return  ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EosWalletOutTxApi.GoReject.METHOD_TITLE_NAME,
            notes = EosWalletOutTxApi.GoReject.METHOD_TITLE_NOTE)
    @PostMapping("/goReject")
    public ResultDTO goReject(@ApiParam(EosWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
        eosWalletTransferService.handReject(id);
        return  ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EosWalletOutTxApi.PackError.METHOD_TITLE_NAME,
            notes = EosWalletOutTxApi.PackError.METHOD_TITLE_NOTE)
    @PostMapping("/packError")
    public ResultDTO packError(@ApiParam(EosWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
        LOG.info("Ignore packError : " + id);
        //eosWalletTransferService.handPackError(id);
        return  ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EosWalletOutTxApi.PackSuccess.METHOD_TITLE_NAME,
            notes = EosWalletOutTxApi.PackSuccess.METHOD_TITLE_NOTE)
    @PostMapping("/packSuccess")
    public ResultDTO packSuccess(@ApiParam(EosWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
        LOG.info("packSuccess : " + id);
        //eosWalletTransferService.handPackSuccess(id);
        return  ResultDTO.requstSuccess();
    }
}
