package com.blockchain.server.eos.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.eos.inner.api.EosWalletApi;
import com.blockchain.server.eos.service.IEosWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Harvey
 * @date 2019/2/19 18:09
 * @user WIN10
 */
@Api(EosWalletApi.EOS_WALLET_API)
@RestController
@RequestMapping("/inner/walletTx")
public class EosWalletInnerController {
    private static final Logger LOG = LoggerFactory.getLogger(EosWalletInnerController.class);
    @Autowired
    private IEosWalletService eosWalletService;

    @ApiOperation(value = EosWalletApi.Order.MATHOD_API_NAME, notes = EosWalletApi.Order.MATHOD_API_NOTE)
    @PostMapping("/order")
    public ResultDTO order(@ApiParam(EosWalletApi.Order.MATHOD_API_WALLET_ORDER_DTO) @RequestBody WalletOrderDTO walletOrderDTO) {
        LOG.info("冻结、解冻余额，walletOrderDTO：" + walletOrderDTO.toString());
        eosWalletService.updateWalletOrder(walletOrderDTO);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EosWalletApi.Change.MATHOD_API_NAME, notes = EosWalletApi.Change.MATHOD_API_NOTE)
    @PostMapping("/change")
    public ResultDTO change(@ApiParam(EosWalletApi.Change.MATHOD_API_WALLET_CHANGE_DTO) @RequestBody WalletChangeDTO walletChangeDTO) {
        LOG.info("扣除、增加，walletChangeDTO：" + walletChangeDTO.toString());
        eosWalletService.handleWalletChange(walletChangeDTO);
        return ResultDTO.requstSuccess();
    }

}
