package com.blockchain.server.cct.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.cct.controller.api.RecordApi;
import com.blockchain.server.cct.dto.tradingRecord.ListRecordResultDTO;
import com.blockchain.server.cct.service.TradingRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(RecordApi.RECORD_API)
@RestController
@RequestMapping("/record")
public class RecordController extends BaseController {

    @Autowired
    private TradingRecordService recordService;

    @ApiOperation(value = RecordApi.listRecord.METHOD_TITLE_NAME,
            notes = RecordApi.listRecord.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listRecord(@ApiParam(RecordApi.listRecord.METHOD_API_ORDERID) @RequestParam("orderId") String orderId) {
        List<ListRecordResultDTO> records = recordService.listRecord(orderId);
        return generatePage(records);
    }

    @ApiOperation(value = RecordApi.GetById.METHOD_TITLE_NAME,
            notes = RecordApi.GetById.METHOD_TITLE_NOTE)
    @GetMapping("/getById")
    public ResultDTO getById(@ApiParam(RecordApi.GetById.METHOD_API_ID) @RequestParam("id") String id) {
        return ResultDTO.requstSuccess(recordService.getById(id));
    }

}
