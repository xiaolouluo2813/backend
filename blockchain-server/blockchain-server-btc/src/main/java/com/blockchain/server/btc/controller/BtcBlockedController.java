package com.blockchain.server.btc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.security.RequiresPermissions;
import com.blockchain.server.btc.controller.api.BtcBlockApi;
import com.blockchain.server.btc.entity.BtcWalletBlockedDetail;
import com.blockchain.server.btc.service.IBtcBlockedService;
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
public class BtcBlockedController {
    @Autowired
    private IBtcBlockedService btcBlockedService;

    @ApiOperation(value = BtcBlockApi.Block.METHOD_TITLE_NAME, notes = BtcBlockApi.Block.METHOD_TITLE_NOTE)
    @PostMapping("/blockBalance")
    @RequiresPermissions("OperationShowFreeze")
    public ResultDTO blockBalance(@ApiParam(BtcBlockApi.Block.METHOD_API_WALLET_UID) @RequestParam("walletUid") String walletUid,
                                  @ApiParam(BtcBlockApi.Block.METHOD_API_TOKEN_SYMBOL) @RequestParam("tokenSymbol") String tokenSymbol,
                                  @ApiParam(BtcBlockApi.Block.METHOD_API_OPT_NUMBER) @RequestParam("optNumber") BigDecimal optNumber,
                                  @ApiParam(BtcBlockApi.Block.METHOD_API_REMARK) @RequestParam(value = "remark", required = false) String remark) {
        btcBlockedService.blockBalance(walletUid, tokenSymbol, optNumber, remark);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = BtcBlockApi.UnBlock.METHOD_TITLE_NAME, notes = BtcBlockApi.UnBlock.METHOD_TITLE_NOTE)
    @PostMapping("/unblockBalance")
    @RequiresPermissions("OperationShowUnfreeze")
    public ResultDTO unblockBalance(@ApiParam(BtcBlockApi.UnBlock.METHOD_API_WALLET_UID) @RequestParam("walletUid") String walletUid,
                                    @ApiParam(BtcBlockApi.UnBlock.METHOD_API_TOKEN_SYMBOL) @RequestParam("tokenSymbol") String tokenSymbol,
                                    @ApiParam(BtcBlockApi.UnBlock.METHOD_API_OPT_NUMBER) @RequestParam("optNumber") BigDecimal optNumber,
                                    @ApiParam(BtcBlockApi.UnBlock.METHOD_API_REMARK) @RequestParam(value = "remark", required = false) String remark) {
        btcBlockedService.unblockBalance(walletUid, tokenSymbol, optNumber, remark);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = BtcBlockApi.Details.METHOD_TITLE_NAME, notes = BtcBlockApi.Details.METHOD_TITLE_NOTE)
    @GetMapping("/details")
    public ResultDTO listDetail(@ApiParam(BtcBlockApi.Details.METHOD_API_USERNAME) @RequestParam(value = "username", required = false) String username,
                                @ApiParam(BtcBlockApi.Details.METHOD_API_TYPE) @RequestParam(value = "type", required = false) String type) {
        List<BtcWalletBlockedDetail> details = btcBlockedService.listDetails(username, type);
        return ResultDTO.requstSuccess(details);
    }

    @ApiOperation(value = BtcBlockApi.SelectTotal.METHOD_TITLE_NAME, notes = BtcBlockApi.SelectTotal.METHOD_TITLE_NOTE)
    @GetMapping("/selectTotal")
    public ResultDTO selectDetail(@ApiParam(BtcBlockApi.SelectTotal.METHOD_API_WALLET_UID) @RequestParam("walletUid") String walletUid,
                                  @ApiParam(BtcBlockApi.SelectTotal.METHOD_API_TOKEN_SYMBOL) @RequestParam("tokenSymbol") String tokenSymbol) {
        return ResultDTO.requstSuccess(btcBlockedService.selectTotal(walletUid, tokenSymbol));
    }

}
