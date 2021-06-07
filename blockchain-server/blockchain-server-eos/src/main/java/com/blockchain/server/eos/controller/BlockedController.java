package com.blockchain.server.eos.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.security.RequiresPermissions;
import com.blockchain.server.eos.controller.api.EosBlockApi;
import com.blockchain.server.eos.entity.EosWalletBlockedDetail;
import com.blockchain.server.eos.service.IEosBlockedService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 冻结解冻接口
 *
 * @author huangxl
 * @create 2019-06-25 11:09
 */
@RestController
@RequestMapping("/block")
public class BlockedController {
    @Autowired
    private IEosBlockedService ethBlockedService;

    @ApiOperation(value = EosBlockApi.Block.METHOD_TITLE_NAME, notes = EosBlockApi.Block.METHOD_TITLE_NOTE)
    @PostMapping("/blockBalance")
    @RequiresPermissions("OperationShowFreeze")
    public ResultDTO blockBalance(@ApiParam(EosBlockApi.Block.METHOD_API_WALLET_UID) @RequestParam("walletUid") String walletUid,
                                  @ApiParam(EosBlockApi.Block.METHOD_API_TOKEN_SYMBOL) @RequestParam("tokenSymbol") String tokenSymbol,
                                  @ApiParam(EosBlockApi.Block.METHOD_API_OPT_NUMBER) @RequestParam("optNumber") BigDecimal optNumber,
                                  @ApiParam(EosBlockApi.Block.METHOD_API_REMARK) @RequestParam(value = "remark", required = false) String remark) {
        ethBlockedService.blockBalance(walletUid, tokenSymbol, optNumber, remark);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EosBlockApi.UnBlock.METHOD_TITLE_NAME, notes = EosBlockApi.UnBlock.METHOD_TITLE_NOTE)
    @PostMapping("/unblockBalance")
    @RequiresPermissions("OperationShowUnfreeze")
    public ResultDTO unblockBalance(@ApiParam(EosBlockApi.UnBlock.METHOD_API_WALLET_UID) @RequestParam("walletUid") String walletUid,
                                    @ApiParam(EosBlockApi.UnBlock.METHOD_API_TOKEN_SYMBOL) @RequestParam("tokenSymbol") String tokenSymbol,
                                    @ApiParam(EosBlockApi.UnBlock.METHOD_API_OPT_NUMBER) @RequestParam("optNumber") BigDecimal optNumber,
                                    @ApiParam(EosBlockApi.UnBlock.METHOD_API_REMARK) @RequestParam(value = "remark", required = false) String remark) {
        ethBlockedService.unblockBalance(walletUid, tokenSymbol, optNumber, remark);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EosBlockApi.Details.METHOD_TITLE_NAME, notes = EosBlockApi.Details.METHOD_TITLE_NOTE)
    @GetMapping("/details")
    public ResultDTO listDetail(@ApiParam(EosBlockApi.Details.METHOD_API_USERNAME) @RequestParam(value = "username", required = false) String username,
                                @ApiParam(EosBlockApi.Details.METHOD_API_TYPE) @RequestParam(value = "type", required = false) String type) {
        List<EosWalletBlockedDetail> details = ethBlockedService.listDetails(username, type);
        return ResultDTO.requstSuccess(details);
    }

    @ApiOperation(value = EosBlockApi.SelectTotal.METHOD_TITLE_NAME, notes = EosBlockApi.SelectTotal.METHOD_TITLE_NOTE)
    @GetMapping("/selectTotal")
    public ResultDTO selectDetail(@ApiParam(EosBlockApi.SelectTotal.METHOD_API_WALLET_UID) @RequestParam("walletUid") String walletUid,
                                  @ApiParam(EosBlockApi.SelectTotal.METHOD_API_TOKEN_SYMBOL) @RequestParam("tokenSymbol") String tokenSymbol) {
        return ResultDTO.requstSuccess(ethBlockedService.selectTotal(walletUid, tokenSymbol));
    }

}
