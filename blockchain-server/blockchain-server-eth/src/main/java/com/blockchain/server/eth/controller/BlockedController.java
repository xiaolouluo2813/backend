package com.blockchain.server.eth.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.security.RequiresPermissions;
import com.blockchain.server.eth.controller.api.EthBlockApi;
import com.blockchain.server.eth.entity.EthWalletBlockedDetail;
import com.blockchain.server.eth.service.IEthBlockedService;
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
    private IEthBlockedService ethBlockedService;

    @ApiOperation(value = EthBlockApi.Block.METHOD_TITLE_NAME, notes = EthBlockApi.Block.METHOD_TITLE_NOTE)
    @PostMapping("/blockBalance")
    @RequiresPermissions("OperationShowFreeze")
    public ResultDTO blockBalance(@ApiParam(EthBlockApi.Block.METHOD_API_WALLET_UID) @RequestParam("walletUid") String walletUid,
                                  @ApiParam(EthBlockApi.Block.METHOD_API_TOKEN_SYMBOL) @RequestParam("tokenSymbol") String tokenSymbol,
                                  @ApiParam(EthBlockApi.Block.METHOD_API_OPT_NUMBER) @RequestParam("optNumber") BigDecimal optNumber,
                                  @ApiParam(EthBlockApi.Block.METHOD_API_REMARK) @RequestParam(value = "remark", required = false) String remark) {
        ethBlockedService.blockBalance(walletUid, tokenSymbol, optNumber, remark);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EthBlockApi.UnBlock.METHOD_TITLE_NAME, notes = EthBlockApi.UnBlock.METHOD_TITLE_NOTE)
    @PostMapping("/unblockBalance")
    @RequiresPermissions("OperationShowUnfreeze")
    public ResultDTO unblockBalance(@ApiParam(EthBlockApi.UnBlock.METHOD_API_WALLET_UID) @RequestParam("walletUid") String walletUid,
                                    @ApiParam(EthBlockApi.UnBlock.METHOD_API_TOKEN_SYMBOL) @RequestParam("tokenSymbol") String tokenSymbol,
                                    @ApiParam(EthBlockApi.UnBlock.METHOD_API_OPT_NUMBER) @RequestParam("optNumber") BigDecimal optNumber,
                                    @ApiParam(EthBlockApi.UnBlock.METHOD_API_REMARK) @RequestParam(value = "remark", required = false) String remark) {
        ethBlockedService.unblockBalance(walletUid, tokenSymbol, optNumber, remark);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EthBlockApi.Details.METHOD_TITLE_NAME, notes = EthBlockApi.Details.METHOD_TITLE_NOTE)
    @GetMapping("/details")
    public ResultDTO listDetail(@ApiParam(EthBlockApi.Details.METHOD_API_USERNAME) @RequestParam(value = "username", required = false) String username,
                                @ApiParam(EthBlockApi.Details.METHOD_API_TYPE) @RequestParam(value = "type", required = false) String type) {
        List<EthWalletBlockedDetail> details = ethBlockedService.listDetails(username, type);
        return ResultDTO.requstSuccess(details);
    }

    @ApiOperation(value = EthBlockApi.SelectTotal.METHOD_TITLE_NAME, notes = EthBlockApi.SelectTotal.METHOD_TITLE_NOTE)
    @GetMapping("/selectTotal")
    public ResultDTO selectDetail(@ApiParam(EthBlockApi.SelectTotal.METHOD_API_WALLET_UID) @RequestParam("walletUid") String walletUid,
                                  @ApiParam(EthBlockApi.SelectTotal.METHOD_API_TOKEN_SYMBOL) @RequestParam("tokenSymbol") String tokenSymbol) {
        return ResultDTO.requstSuccess(ethBlockedService.selectTotal(walletUid, tokenSymbol));
    }

}
