package com.blockchain.server.eth.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.eth.controller.api.EthTotalApi;
import com.blockchain.server.eth.controller.api.EthWalletOutTxApi;
import com.blockchain.server.eth.dto.wallet.EthTotalInfoDTO;
import com.blockchain.server.eth.service.IEthClearingTotalService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * 提现记录查询接口
 *
 * @author YH
 * @date 2019年3月9日09:50:24
 */
@RestController
@RequestMapping("/total")
public class EthClearingTotalController {
    @Autowired
    IEthClearingTotalService ethClearingTotalService;

    @ApiOperation(value = EthTotalApi.All.METHOD_TITLE_NAME, notes = EthTotalApi.All.METHOD_TITLE_NOTE)
    @GetMapping("/all")
    public ResultDTO all(@ApiParam(EthTotalApi.All.METHOD_API_USERID) @RequestParam(value = "userId") String userId) {
        List<EthTotalInfoDTO> totalInfos = ethClearingTotalService.selectInfoAll(userId);
        return ResultDTO.requstSuccess(totalInfos);
    }

    @ApiOperation(value = EthTotalApi.Save.METHOD_TITLE_NAME, notes = EthTotalApi.Save.METHOD_TITLE_NOTE)
    @PostMapping("/save")
    public ResultDTO save(@ApiParam(EthTotalApi.Save.METHOD_API_TOTALID) @RequestParam(value = "totalId") String totalId,
                          @ApiParam(EthTotalApi.Save.METHOD_API_AMOUNT) @RequestParam(value = "amount") BigDecimal amount) {
        ethClearingTotalService.updateCorr(totalId, amount);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EthTotalApi.Total.METHOD_TITLE_NAME, notes = EthTotalApi.Total.METHOD_TITLE_NOTE)
    @PostMapping("/total")
    public ResultDTO total(@ApiParam(EthTotalApi.Total.METHOD_API_USERID) @RequestParam(value = "userId") String userId) {
        List<EthTotalInfoDTO> totalInfos = ethClearingTotalService.insertTotals(userId);
        return ResultDTO.requstSuccess(totalInfos);
    }

}
