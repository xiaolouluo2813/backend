package com.blockchain.server.eth.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.eth.common.constants.tx.OutTxConstants;
import com.blockchain.server.eth.controller.api.EthTokenApi;
import com.blockchain.server.eth.controller.api.EthWalletOutTxApi;
import com.blockchain.server.eth.entity.EthToken;
import com.blockchain.server.eth.service.IEthTokenService;
import com.blockchain.server.eth.service.IEthWalletTransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 提现记录查询接口
 *
 * @author YH
 * @date 2019年3月9日09:50:24
 */
@Api(EthTokenApi.API_NAME)
@RestController
@RequestMapping("/coin")
public class EthTokenController {
    @Autowired
    IEthTokenService ethTokenService;

    @ApiOperation(value = EthTokenApi.All.METHOD_TITLE_NAME, notes = EthTokenApi.All.METHOD_TITLE_NOTE)
    @GetMapping("/all")
    public ResultDTO all() {
        List<EthToken> list = ethTokenService.selectAll();
        return ResultDTO.requstSuccess(list);
    }
}
