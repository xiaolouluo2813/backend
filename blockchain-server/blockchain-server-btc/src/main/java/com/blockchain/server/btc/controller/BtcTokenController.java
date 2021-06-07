package com.blockchain.server.btc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.btc.controller.api.BtcTokenApi;
import com.blockchain.server.btc.entity.BtcToken;
import com.blockchain.server.btc.service.IBtcTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 提现记录查询接口
 *
 * @author YH
 * @date 2019年3月9日09:50:24
 */
@Api(BtcTokenApi.API_NAME)
@RestController
@RequestMapping("/coin")
public class BtcTokenController {
    @Autowired
    IBtcTokenService btcTokenService;

    @ApiOperation(value = BtcTokenApi.All.METHOD_TITLE_NAME, notes = BtcTokenApi.All.METHOD_TITLE_NOTE)
    @GetMapping("/all")
    public ResultDTO all() {
        List<BtcToken> list = btcTokenService.selectAll();
        return ResultDTO.requstSuccess(list);
    }
}
