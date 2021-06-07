package com.blockchain.server.btc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.btc.controller.api.BtcTotalApi;
import com.blockchain.server.btc.dto.BtcTotalInfoDTO;
import com.blockchain.server.btc.service.IBtcClearingTotalService;
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
public class BtcClearingTotalController {
    @Autowired
    IBtcClearingTotalService btcClearingTotalService;

    @ApiOperation(value = BtcTotalApi.All.METHOD_TITLE_NAME, notes = BtcTotalApi.All.METHOD_TITLE_NOTE)
    @GetMapping("/all")
    public ResultDTO all(@ApiParam(BtcTotalApi.All.METHOD_API_USERID) @RequestParam(value = "userId") String userId) {
        List<BtcTotalInfoDTO> totalInfos = btcClearingTotalService.selectInfoAll(userId);
        return ResultDTO.requstSuccess(totalInfos);
    }

    @ApiOperation(value = BtcTotalApi.Save.METHOD_TITLE_NAME, notes = BtcTotalApi.Save.METHOD_TITLE_NOTE)
    @PostMapping("/save")
    public ResultDTO save(@ApiParam(BtcTotalApi.Save.METHOD_API_TOTALID) @RequestParam(value = "totalId") String totalId,
                          @ApiParam(BtcTotalApi.Save.METHOD_API_AMOUNT) @RequestParam(value = "amount") BigDecimal amount) {
        btcClearingTotalService.updateCorr(totalId, amount);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = BtcTotalApi.Total.METHOD_TITLE_NAME, notes = BtcTotalApi.Total.METHOD_TITLE_NOTE)
    @PostMapping("/total")
    public ResultDTO total(@ApiParam(BtcTotalApi.Total.METHOD_API_USERID) @RequestParam(value = "userId") String userId) {
        List<BtcTotalInfoDTO> totalInfos = btcClearingTotalService.insertTotals(userId);
        return ResultDTO.requstSuccess(totalInfos);
    }

}
