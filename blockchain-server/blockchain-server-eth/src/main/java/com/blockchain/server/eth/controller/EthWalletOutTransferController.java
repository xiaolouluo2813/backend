package com.blockchain.server.eth.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.eth.common.constants.tx.OutTxConstants;
import com.blockchain.server.eth.common.util.ParamsUtil;
import com.blockchain.server.eth.controller.api.EthWalletOutTxApi;
import com.blockchain.server.eth.controller.api.EthWalletOutTxApi;
import com.blockchain.server.eth.service.IEthWalletTransferService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 提现记录查询接口
 *
 * @author YH
 * @date 2019年3月9日09:50:24
 */
@Api(EthWalletOutTxApi.API_NAME)
@RestController
@RequestMapping("/outTx")
public class EthWalletOutTransferController extends BaseController {
    @Autowired
    IEthWalletTransferService ethWalletTransferService;

    @ApiOperation(value = EthWalletOutTxApi.Check.METHOD_TITLE_NAME, notes = EthWalletOutTxApi.Check.METHOD_TITLE_NOTE)
    @PostMapping("/check")
    public ResultDTO check(@ApiParam(EthWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                           @ApiParam(EthWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @ApiParam(EthWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(OutTxConstants.CHECK);
        List<WalletTxDTO> list = ethWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EthWalletOutTxApi.Recheck.METHOD_TITLE_NAME, notes =
            EthWalletOutTxApi.Recheck.METHOD_TITLE_NOTE)
    @PostMapping("/recheck")
    public ResultDTO recheck(@ApiParam(EthWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                             @ApiParam(EthWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @ApiParam(EthWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(OutTxConstants.RECHECK);
        List<WalletTxDTO> list = ethWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EthWalletOutTxApi.Pack.METHOD_TITLE_NAME, notes = EthWalletOutTxApi.Pack.METHOD_TITLE_NOTE)
    @PostMapping("/pack")
    public ResultDTO pack(@ApiParam(EthWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                          @ApiParam(EthWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                          @ApiParam(EthWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(OutTxConstants.PACK);
        List<WalletTxDTO> list = ethWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EthWalletOutTxApi.Success.METHOD_TITLE_NAME, notes =
            EthWalletOutTxApi.Success.METHOD_TITLE_NOTE)
    @PostMapping("/success")
    public ResultDTO success(@ApiParam(EthWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                             @ApiParam(EthWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                             @ApiParam(EthWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(OutTxConstants.SUCCESS);
        List<WalletTxDTO> list = ethWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EthWalletOutTxApi.Error.METHOD_TITLE_NAME, notes = EthWalletOutTxApi.Error.METHOD_TITLE_NOTE)
    @PostMapping("/error")
    public ResultDTO error(@ApiParam(EthWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                           @ApiParam(EthWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                           @ApiParam(EthWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(OutTxConstants.ERROR);
        List<WalletTxDTO> list = ethWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EthWalletOutTxApi.Reject.METHOD_TITLE_NAME, notes =
            EthWalletOutTxApi.Reject.METHOD_TITLE_NOTE)
    @PostMapping("/reject")
    public ResultDTO reject(@ApiParam(EthWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                            @ApiParam(EthWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @ApiParam(EthWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        paramsDTO.setTxStatus(OutTxConstants.REJECT);
        List<WalletTxDTO> list = ethWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EthWalletOutTxApi.All.METHOD_TITLE_NAME, notes = EthWalletOutTxApi.All.METHOD_TITLE_NOTE)
    @PostMapping("/all")
    public ResultDTO all(@ApiParam(EthWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                         @ApiParam(EthWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @ApiParam(EthWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = ethWalletTransferService.selectOutTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EthWalletOutTxApi.Mirror.METHOD_TITLE_NAME, notes = EthWalletOutTxApi.Mirror.METHOD_TITLE_NOTE)
    @PostMapping("/mirror")
    public ResultDTO mirror(@ApiParam(EthWalletOutTxApi.METHOD_API_PARAM) WalletTxParamsDTO paramsDTO,
                         @ApiParam(EthWalletOutTxApi.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                         @ApiParam(EthWalletOutTxApi.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WalletTxDTO> list = ethWalletTransferService.selectShopFastTransfer(paramsDTO);
        return generatePage(list);
    }

    @ApiOperation(value = EthWalletOutTxApi.SelectById.METHOD_TITLE_NAME,
            notes = EthWalletOutTxApi.SelectById.METHOD_TITLE_NOTE)
    @GetMapping("/selectById")
    public ResultDTO selectById(@ApiParam(EthWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id) {
        WalletTxDTO inTransfer = ethWalletTransferService.findOutTransfer(id);
        return ResultDTO.requstSuccess(inTransfer);
    }

    @ApiOperation(value = EthWalletOutTxApi.CheckPass.METHOD_TITLE_NAME,
            notes = EthWalletOutTxApi.CheckPass.METHOD_TITLE_NOTE)
    @PostMapping("/checkPass")
    public ResultDTO checkPass(@ApiParam(EthWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
        ethWalletTransferService.updateStatus(id,OutTxConstants.RECHECK);
        return  ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EthWalletOutTxApi.RecheckPass.METHOD_TITLE_NAME,
            notes = EthWalletOutTxApi.RecheckPass.METHOD_TITLE_NOTE)
    @PostMapping("/recheckPass")
    public ResultDTO recheckPass(@ApiParam(EthWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
        ethWalletTransferService.handleOut(id,OutTxConstants.PACK);
//        ethWalletTransferService.updateStatus(id,OutTxConstants.PACK);
        return  ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EthWalletOutTxApi.GoReject.METHOD_TITLE_NAME,
            notes = EthWalletOutTxApi.GoReject.METHOD_TITLE_NOTE)
    @PostMapping("/goReject")
    public ResultDTO goReject(@ApiParam(EthWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
        ethWalletTransferService.handReject(id);
        return  ResultDTO.requstSuccess();
    }

//    @ApiOperation(value = EthWalletOutTxApi.PackError.METHOD_TITLE_NAME,
//            notes = EthWalletOutTxApi.PackError.METHOD_TITLE_NOTE)
//    @PostMapping("/packError")
//    public ResultDTO packError(@ApiParam(EthWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
//        ethWalletTransferService.handPackError(id);
//        return  ResultDTO.requstSuccess();
//    }
//
//    @ApiOperation(value = EthWalletOutTxApi.PackSuccess.METHOD_TITLE_NAME,
//            notes = EthWalletOutTxApi.PackSuccess.METHOD_TITLE_NOTE)
//    @PostMapping("/packSuccess")
//    public ResultDTO packSuccess(@ApiParam(EthWalletOutTxApi.METHOD_API_ID) @RequestParam("id") String id){
//        ethWalletTransferService.handPackSuccess(id);
//        return  ResultDTO.requstSuccess();
//    }

}
