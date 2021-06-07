package com.blockchain.server.eos.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.eos.controller.api.EosTokenApi;
import com.blockchain.server.eos.entity.Token;
import com.blockchain.server.eos.service.IEosTokenService;
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
@Api(EosTokenApi.API_NAME)
@RestController
@RequestMapping("/coin")
public class EosTokenController {
    @Autowired
    IEosTokenService eosTokenService;

    @ApiOperation(value = EosTokenApi.All.METHOD_TITLE_NAME, notes = EosTokenApi.All.METHOD_TITLE_NOTE)
    @GetMapping("/all")
    public ResultDTO all() {
        List<Token> list = eosTokenService.selectAll();
        return ResultDTO.requstSuccess(list);
    }
}
