package com.blockchain.server.eth.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.eth.inner.api.EthWalletTransferApi;
import com.blockchain.server.eth.service.IEthWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author YH
 * @date 2018年11月6日15:51:56
 */
@Api(EthWalletTransferApi.MARKET_CONTROLLER_API)
@RestController
@RequestMapping("/inner/walletTx")
public class EthWalletTransferInner {
    private static final Logger LOG = LoggerFactory.getLogger(EthWalletTransferInner.class);
    @Autowired
    IEthWalletService ethWalletService;
    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation(value = EthWalletTransferApi.Order.METHOD_API_NAME, notes =
            EthWalletTransferApi.Order.METHOD_API_NOTE)
    @PostMapping("/order")
    public ResultDTO order(@ApiParam(EthWalletTransferApi.Order.METHOD_API_ORDERDTO) @RequestBody WalletOrderDTO orderDTO) {
        LOG.info("冻结、解冻余额，orderDTO：" + orderDTO.toString());
        ethWalletService.updateBlanceTransform(orderDTO);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EthWalletTransferApi.Change.METHOD_API_NAME, notes =
            EthWalletTransferApi.Change.METHOD_API_NOTE)
    @PostMapping("/change")
    public ResultDTO change(@ApiParam(EthWalletTransferApi.Change.METHOD_API_CHANGEDTO) @RequestBody WalletChangeDTO changeDTO) {
        LOG.info("扣除、增加，changeDTO：" + changeDTO.toString());
        ethWalletService.updateBlance(changeDTO);
        return ResultDTO.requstSuccess();
    }


}
