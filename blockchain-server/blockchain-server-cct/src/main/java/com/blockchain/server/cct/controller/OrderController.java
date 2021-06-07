package com.blockchain.server.cct.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.cct.common.constant.CCTConstant;
import com.blockchain.server.cct.controller.api.OrderApi;
import com.blockchain.server.cct.dto.publishOrder.ListOrderParamDTO;
import com.blockchain.server.cct.dto.publishOrder.ListOrderResultDTO;
import com.blockchain.server.cct.service.PublishOrderService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(OrderApi.ORDER_API)
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    @Autowired
    private PublishOrderService orderService;

    @ApiOperation(value = OrderApi.listMatch.METHOD_TITLE_NAME,
            notes = OrderApi.listMatch.METHOD_TITLE_NOTE)
    @GetMapping("/listMatch")
    public ResultDTO listMatch(@ApiParam(OrderApi.listMatch.METHOD_API_PARAM) ListOrderParamDTO param,
                               @ApiParam(OrderApi.listFinish.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                               @ApiParam(OrderApi.listFinish.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        //订单状态
        String[] status = {CCTConstant.STATUS_NEW, CCTConstant.STATUS_MATCH, CCTConstant.STATUS_CANCELING};
        //写死状态为已撮合
        param.setOrderStatus(status);
        //分页
        PageHelper.startPage(pageNum, pageSize);
        List<ListOrderResultDTO> orders = orderService.listOrder(param);
        return generatePage(orders);
    }

    @ApiOperation(value = OrderApi.listFinish.METHOD_TITLE_NAME,
            notes = OrderApi.listFinish.METHOD_TITLE_NOTE)
    @GetMapping("/listFinish")
    public ResultDTO listFinish(@ApiParam(OrderApi.listFinish.METHOD_API_PARAM) ListOrderParamDTO param,
                                @ApiParam(OrderApi.listFinish.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                @ApiParam(OrderApi.listFinish.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        //订单状态
        String[] status = {CCTConstant.STATUS_FINISH, CCTConstant.STATUS_CANCEL};
        //写死状态为已完成、已取消
        param.setOrderStatus(status);
        //分页
        PageHelper.startPage(pageNum, pageSize);
        List<ListOrderResultDTO> orders = orderService.listOrder(param);
        return generatePage(orders);
    }

    @ApiOperation(value = OrderApi.listFinish.METHOD_TITLE_NAME,
            notes = OrderApi.listFinish.METHOD_TITLE_NOTE)
    @GetMapping("/orderInfo")
    public ResultDTO orderInfo(@ApiParam(OrderApi.listFinish.METHOD_API_PARAM) ListOrderParamDTO param) {
        return ResultDTO.requstSuccess(orderService.listOrder(param));
    }

    @ApiOperation(value = OrderApi.cancel.METHOD_TITLE_NAME, notes = OrderApi.cancel.METHOD_TITLE_NOTE)
    @PostMapping("/cancel")
    public ResultDTO cancel(@ApiParam(OrderApi.cancel.METHOD_API_ORDERID) @RequestParam("orderId") String orderId) {
        orderService.cancel(SecurityUtils.getUserId(), HttpRequestUtil.getIpAddr(), orderId);
        return ResultDTO.requstSuccess();
    }
}
