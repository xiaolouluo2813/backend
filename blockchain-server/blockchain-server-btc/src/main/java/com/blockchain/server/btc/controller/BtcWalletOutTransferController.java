package com.blockchain.server.btc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.btc.common.constants.OutTxConstants;
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
 * 提现记录查询接口
 *
 * @author YH
 * @date 2019年3月9日09:50:24
 */
@Api(BtcWalletOutTxApi.API_NAME)
@RestController
@RequestMapping("/outTx")
public class BtcWalletOutTransferController extends BaseController {
    @Autowired
    IBtcWalletTransferService btcWalletTransferService;

    @ApiOperation(value = BtcWalletOutTxApi.Check.METHOD_TITLE_NAME, notes = BtcWalletOutTxApi.Check.METHOD_TITLE_NOTE)
    @PostMapping("/check")
    public ResultDTO check(@ApiParam(BtcWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                           @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        paramsDTO.setTxStatus(OutTxConstants.CHECK);
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = btcWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletOutTxApi.Recheck.METHOD_TITLE_NAME, notes =
            BtcWalletOutTxApi.Recheck.METHOD_TITLE_NOTE)
    @PostMapping("/recheck")
    public ResultDTO recheck(@ApiParam(BtcWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                             @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        paramsDTO.setTxStatus(OutTxConstants.RECHECK);
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = btcWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletOutTxApi.Pack.METHOD_TITLE_NAME, notes = BtcWalletOutTxApi.Pack.METHOD_TITLE_NOTE)
    @PostMapping("/pack")
    public ResultDTO pack(@ApiParam(BtcWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                          @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        paramsDTO.setTxStatus(OutTxConstants.PACK);
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = btcWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletOutTxApi.Success.METHOD_TITLE_NAME, notes =
            BtcWalletOutTxApi.Success.METHOD_TITLE_NOTE)
    @PostMapping("/success")
    public ResultDTO success(@ApiParam(BtcWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                             @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        paramsDTO.setTxStatus(OutTxConstants.SUCCESS);
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = btcWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletOutTxApi.Error.METHOD_TITLE_NAME, notes = BtcWalletOutTxApi.Error.METHOD_TITLE_NOTE)
    @PostMapping("/error")
    public ResultDTO error(@ApiParam(BtcWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                           @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        paramsDTO.setTxStatus(OutTxConstants.ERROR);
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = btcWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletOutTxApi.Reject.METHOD_TITLE_NAME, notes =
            BtcWalletOutTxApi.Reject.METHOD_TITLE_NOTE)
    @PostMapping("/reject")
    public ResultDTO reject(@ApiParam(BtcWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                            @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        paramsDTO.setTxStatus(OutTxConstants.REJECT);
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = btcWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletOutTxApi.All.METHOD_TITLE_NAME, notes = BtcWalletOutTxApi.All.METHOD_TITLE_NOTE)
    @PostMapping("/all")
    public ResultDTO all(@ApiParam(BtcWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                         @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @ApiParam(BtcWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = btcWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = BtcWalletOutTxApi.SelectById.METHOD_TITLE_NAME,
            notes = BtcWalletOutTxApi.SelectById.METHOD_TITLE_NOTE)
    @GetMapping("/selectById")
    public ResultDTO selectById(@ApiParam(BtcWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id) {
        WalletTxDTO inTransfer = btcWalletTransferService.findOutTransfer(id);
        return ResultDTO.requstSuccess(inTransfer);
    }

    @ApiOperation(value = BtcWalletOutTxApi.CheckPass.METHOD_TITLE_NAME,
            notes = BtcWalletOutTxApi.CheckPass.METHOD_TITLE_NOTE)
    @PostMapping("/checkPass")
    public ResultDTO checkPass(@ApiParam(BtcWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
        btcWalletTransferService.updateStatus(id,OutTxConstants.RECHECK);
        return  ResultDTO.requstSuccess();
    }

    @ApiOperation(value = BtcWalletOutTxApi.RecheckPass.METHOD_TITLE_NAME,
            notes = BtcWalletOutTxApi.RecheckPass.METHOD_TITLE_NOTE)
    @PostMapping("/recheckPass")
    public ResultDTO recheckPass(@ApiParam(BtcWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
        btcWalletTransferService.handleOut(id,OutTxConstants.PACK);
//        btcWalletTransferService.updateStatus(id,OutTxConstants.PACK);
        return  ResultDTO.requstSuccess();
    }

    @ApiOperation(value = BtcWalletOutTxApi.GoReject.METHOD_TITLE_NAME,
            notes = BtcWalletOutTxApi.GoReject.METHOD_TITLE_NOTE)
    @PostMapping("/goReject")
    public ResultDTO goReject(@ApiParam(BtcWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
        btcWalletTransferService.handReject(id);
        return  ResultDTO.requstSuccess();
    }


//    @ApiOperation(value = BtcWalletOutTxApi.PackError.METHOD_TITLE_NAME,
//            notes = BtcWalletOutTxApi.PackError.METHOD_TITLE_NOTE)
//    @PostMapping("/packError")
//    public ResultDTO packError(@ApiParam(BtcWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
//        btcWalletTransferService.handPackError(id);
//        return  ResultDTO.requstSuccess();
//    }
//
//    @ApiOperation(value = BtcWalletOutTxApi.PackSuccess.METHOD_TITLE_NAME,
//            notes = BtcWalletOutTxApi.PackSuccess.METHOD_TITLE_NOTE)
//    @PostMapping("/packSuccess")
//    public ResultDTO packSuccess(@ApiParam(BtcWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
//        btcWalletTransferService.handPackSuccess(id);
//        return  ResultDTO.requstSuccess();
//    }

}
