package com.blockchain.server.eth.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.eth.controller.api.EthCountTotalApi;
import com.blockchain.server.eth.controller.api.EthTotalApi;
import com.blockchain.server.eth.dto.wallet.EthCountTotalInfoDTO;
import com.blockchain.server.eth.dto.wallet.EthTotalInfoDTO;
import com.blockchain.server.eth.service.IEthClearingCountTotalService;
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
@RequestMapping("/countTotal")
public class EthClearingCountTotalController {
    @Autowired
    IEthClearingCountTotalService ethClearingCountTotalService;

    @ApiOperation(value = EthCountTotalApi.All.METHOD_TITLE_NAME, notes = EthCountTotalApi.All.METHOD_TITLE_NOTE)
    @GetMapping("/all")
    public ResultDTO all() {
        List<EthCountTotalInfoDTO> totalInfos = ethClearingCountTotalService.selectInfoAll();
        return ResultDTO.requstSuccess(totalInfos);
    }

    @ApiOperation(value = EthCountTotalApi.Total.METHOD_TITLE_NAME, notes = EthCountTotalApi.Total.METHOD_TITLE_NOTE)
    @PostMapping("/total")
    public ResultDTO total() {
        List<EthCountTotalInfoDTO> totalInfos = ethClearingCountTotalService.insertTotals();
        return ResultDTO.requstSuccess(totalInfos);
    }

}
