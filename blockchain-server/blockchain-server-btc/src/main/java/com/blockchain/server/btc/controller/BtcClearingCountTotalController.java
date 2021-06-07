package com.blockchain.server.btc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.btc.controller.api.BtcTotalApi;
import com.blockchain.server.btc.dto.BtcCountTotalInfoDTO;
import com.blockchain.server.btc.dto.BtcTotalInfoDTO;
import com.blockchain.server.btc.service.IBtcClearingCountTotalService;
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
@RequestMapping("/countTotal")
public class BtcClearingCountTotalController {
    @Autowired
    IBtcClearingCountTotalService btcClearingCountTotalService;

    @ApiOperation(value = BtcTotalApi.All.METHOD_TITLE_NAME, notes = BtcTotalApi.All.METHOD_TITLE_NOTE)
    @GetMapping("/all")
    public ResultDTO all() {
        List<BtcCountTotalInfoDTO> totalInfos = btcClearingCountTotalService.selectInfoAll();
        return ResultDTO.requstSuccess(totalInfos);
    }


    @ApiOperation(value = BtcTotalApi.Total.METHOD_TITLE_NAME, notes = BtcTotalApi.Total.METHOD_TITLE_NOTE)
    @PostMapping("/total")
    public ResultDTO total() {
        List<BtcCountTotalInfoDTO> totalInfos = btcClearingCountTotalService.insertTotals();
        return ResultDTO.requstSuccess(totalInfos);
    }

}
