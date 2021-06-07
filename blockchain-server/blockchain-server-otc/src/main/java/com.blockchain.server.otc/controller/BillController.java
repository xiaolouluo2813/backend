package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.BillApi;
import com.blockchain.server.otc.dto.bill.ListBillResultDTO;
import com.blockchain.server.otc.service.BillService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(BillApi.BILL_API)
@RestController
@RequestMapping("/bill")
public class BillController extends BaseController {

    @Autowired
    private BillService billService;

    @ApiOperation(value = BillApi.listBill.METHOD_TITLE_NAME,
            notes = BillApi.listBill.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listBill(@ApiParam(BillApi.listBill.METHOD_API_USER_NAME) @RequestParam(value = "userName", required = false) String userName,
                              @ApiParam(BillApi.listBill.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                              @ApiParam(BillApi.listBill.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                              @ApiParam(BillApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                              @ApiParam(BillApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListBillResultDTO> resultDTOS = billService.listBill(userName, beginTime, endTime);
        return generatePage(resultDTOS);
    }
}
