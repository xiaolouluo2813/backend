package com.blockchain.server.eos.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.eos.controller.api.EosTotalApi;
import com.blockchain.server.eos.dto.EosTotalInfoDTO;
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
@RequestMapping("/total")
public class EosClearingTotalController {
    @Autowired
    IEosClearingTotalService ethClearingTotalService;

    @ApiOperation(value = EosTotalApi.All.METHOD_TITLE_NAME, notes = EosTotalApi.All.METHOD_TITLE_NOTE)
    @GetMapping("/all")
    public ResultDTO all(@ApiParam(EosTotalApi.All.METHOD_API_USERID) @RequestParam(value = "userId") String userId) {
        List<EosTotalInfoDTO> totalInfos = ethClearingTotalService.selectInfoAll(userId);
        return ResultDTO.requstSuccess(totalInfos);
    }

    @ApiOperation(value = EosTotalApi.Save.METHOD_TITLE_NAME, notes = EosTotalApi.Save.METHOD_TITLE_NOTE)
    @PostMapping("/save")
    public ResultDTO save(@ApiParam(EosTotalApi.Save.METHOD_API_TOTALID) @RequestParam(value = "totalId") String totalId,
                          @ApiParam(EosTotalApi.Save.METHOD_API_AMOUNT) @RequestParam(value = "amount") BigDecimal amount) {
        ethClearingTotalService.updateCorr(totalId, amount);
        return ResultDTO.requstSuccess();
    }

    @ApiOperation(value = EosTotalApi.Total.METHOD_TITLE_NAME, notes = EosTotalApi.Total.METHOD_TITLE_NOTE)
    @PostMapping("/total")
    public ResultDTO total(@ApiParam(EosTotalApi.Total.METHOD_API_USERID) @RequestParam(value = "userId") String userId) {
        List<EosTotalInfoDTO> totalInfos = ethClearingTotalService.insertTotals(userId);
        return ResultDTO.requstSuccess(totalInfos);
    }

}
