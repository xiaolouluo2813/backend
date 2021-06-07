package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.OrderApi;
import com.blockchain.server.otc.dto.order.ListOrderParamDTO;
import com.blockchain.server.otc.dto.order.ListOrderResultDTO;
import com.blockchain.server.otc.entity.Order;
import com.blockchain.server.otc.service.OrderService;
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

@Api(OrderApi.ORDER_API)
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = OrderApi.listOrder.METHOD_TITLE_NAME,
            notes = OrderApi.listOrder.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listOrder(@ApiParam(OrderApi.listOrder.METHOD_API_PARAM_DTO) ListOrderParamDTO paramDTO,
                               @ApiParam(OrderApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @ApiParam(OrderApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListOrderResultDTO> resultDTOS = orderService.listOrder(paramDTO);
        return generatePage(resultDTOS);
    }

    @ApiOperation(value = OrderApi.selectByOrderNumber.METHOD_TITLE_NAME,
            notes = OrderApi.selectByOrderNumber.METHOD_TITLE_NOTE)
    @GetMapping("/selectByOrderNumber")
    public ResultDTO selectByOrderNumber(@ApiParam(OrderApi.selectByOrderNumber.METHOD_API_ORDER_NUMBER) @RequestParam("orderNumber") String orderNumber) {
        ListOrderResultDTO resultDTO = orderService.selectDTOByOrderNumber(orderNumber);
        return ResultDTO.requstSuccess(resultDTO);
    }

    @ApiOperation(value = OrderApi.selectByAdId.METHOD_TITLE_NAME,
            notes = OrderApi.selectByAdId.METHOD_TITLE_NOTE)
    @GetMapping("/selectByAdId")
    public ResultDTO selectByAdId(@ApiParam(OrderApi.selectByAdId.METHOD_API_AD_ID) @RequestParam("adId") String adId,
                                  @ApiParam(OrderApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @ApiParam(OrderApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListOrderResultDTO> resultDTOS = orderService.selectDTOByAdId(adId);
        return generatePage(resultDTOS);
    }

    @ApiOperation(value = OrderApi.selectById.METHOD_TITLE_NAME,
            notes = OrderApi.selectById.METHOD_TITLE_NOTE)
    @GetMapping("/selectById")
    public ResultDTO selectById(@ApiParam(OrderApi.selectById.METHOD_API_ID) @RequestParam("id") String id) {
        ListOrderResultDTO resultDTO = orderService.selectDTOById(id);
        return ResultDTO.requstSuccess(resultDTO);
    }
}
