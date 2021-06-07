package com.blockchain.server.btc.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;

import com.blockchain.server.btc.inner.api.BtcWalletTransferInnerApi;
import com.blockchain.server.btc.service.IBtcWalletTransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(BtcWalletTransferInnerApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/inner/walletTx")
public class BtcWalletTransferInner {
    private static final Logger LOG = LoggerFactory.getLogger(BtcWalletTransferInner.class);
    @Autowired
    private IBtcWalletTransferService btcWalletTransferService;

    @ApiOperation(value = BtcWalletTransferInnerApi.Order.METHOD_API_NAME, notes = BtcWalletTransferInnerApi.Order.METHOD_API_NOTE)
    @PostMapping("/order")
    public ResultDTO order(@ApiParam(BtcWalletTransferInnerApi.Order.WALLETORDERDTO) @RequestBody WalletOrderDTO walletOrderDTO) {
        LOG.info("冻结、解冻余额，walletOrderDTO：" + walletOrderDTO.toString());
        return ResultDTO.requstSuccess(btcWalletTransferService.handleOrder(walletOrderDTO));
    }

    @ApiOperation(value = BtcWalletTransferInnerApi.Change.METHOD_API_NAME, notes = BtcWalletTransferInnerApi.Change.METHOD_API_NOTE)
    @PostMapping("/change")
    public ResultDTO change(@ApiParam(BtcWalletTransferInnerApi.Change.WALLETCHANGEDTO) @RequestBody WalletChangeDTO walletChangeDTO) {
        LOG.info("扣除、增加，walletChangeDTO：" + walletChangeDTO.toString());
        return ResultDTO.requstSuccess(btcWalletTransferService.handleChange(walletChangeDTO));
    }

}
