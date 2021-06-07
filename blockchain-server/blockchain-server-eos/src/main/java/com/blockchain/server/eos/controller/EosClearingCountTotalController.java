package com.blockchain.server.eos.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.eos.controller.api.EosCountTotalApi;
import com.blockchain.server.eos.controller.api.EosTotalApi;
import com.blockchain.server.eos.dto.EosCountTotalInfoDTO;
import com.blockchain.server.eos.dto.EosTotalInfoDTO;
import com.blockchain.server.eos.service.IEosClearingCountTotalService;
import com.blockchain.server.eos.service.IEosClearingTotalService;
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
public class EosClearingCountTotalController {
    @Autowired
    IEosClearingCountTotalService eosClearingCountTotalService;

    @ApiOperation(value = EosCountTotalApi.All.METHOD_TITLE_NAME, notes = EosCountTotalApi.All.METHOD_TITLE_NOTE)
    @GetMapping("/all")
    public ResultDTO all() {
        List<EosCountTotalInfoDTO> totalInfos = eosClearingCountTotalService.selectInfoAll();
        return ResultDTO.requstSuccess(totalInfos);
    }

    @ApiOperation(value = EosCountTotalApi.Total.METHOD_TITLE_NAME, notes = EosCountTotalApi.Total.METHOD_TITLE_NOTE)
    @PostMapping("/total")
    public ResultDTO total() {
        List<EosCountTotalInfoDTO> totalInfos = eosClearingCountTotalService.insertTotals();
        return ResultDTO.requstSuccess(totalInfos);
    }

}
