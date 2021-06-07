package com.blockchain.server.quantized.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.quantized.common.constant.RedisKeyConstant;
import com.blockchain.server.quantized.common.constant.TradingOnConstant;
import com.blockchain.server.quantized.common.enums.QuantizedResultEnums;
import com.blockchain.server.quantized.common.exception.QuantizedException;
import com.blockchain.server.quantized.controller.api.TradingOnApi;
import com.blockchain.server.quantized.entity.TradingOn;
import com.blockchain.server.quantized.service.TradingOnService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author: Liusd
 * @create: 2019-04-18 15:54
 **/
@RestController
@RequestMapping("/tradingOn")
@Api(TradingOnApi.CONTROLLER_API)
public class TradingOnController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(TradingOnController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TradingOnService tradingOnService;


    /**
    * @Description: 列表
    * @Param: [pageNum, pageSize]
    * @return: com.blockchain.common.base.dto.ResultDTO
    * @Author: Liu.sd
    * @Date: 2019/4/18
    */
    @GetMapping("/list")
    @ApiOperation(value = TradingOnApi.List.METHOD_TITLE_NAME, notes = TradingOnApi.List.METHOD_TITLE_NOTE)
    public ResultDTO list(@ApiParam(TradingOnApi.List.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                                      @ApiParam(TradingOnApi.List.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(tradingOnService.list(null));
    }

    /**
    * @Description: 删除
    * @Param: [coinName, unitName, request]
    * @return: com.blockchain.common.base.dto.ResultDTO
    * @Author: Liu.sd
    * @Date: 2019/4/18
    */
    @PostMapping("/delete")
    @ApiOperation(value = TradingOnApi.Delete.METHOD_TITLE_NAME, notes = TradingOnApi.Delete.METHOD_TITLE_NOTE)
    public ResultDTO delete(@ApiParam(TradingOnApi.Delete.METHOD_API_COINNAME) @RequestParam("coinName") String coinName,@ApiParam(TradingOnApi.Delete.METHOD_API_UNITNAME) @RequestParam("unitName") String unitName,
                                  HttpServletRequest request) {
        tradingOnService.deleteByCoinNameAndUnitName(coinName,unitName);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 添加 
    * @Param: [coinName, unitName, state, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/4/24 
    */ 
    @PostMapping("/add")
    @ApiOperation(value = TradingOnApi.Add.METHOD_TITLE_NAME, notes = TradingOnApi.Add.METHOD_TITLE_NOTE)
    public ResultDTO add(@ApiParam(TradingOnApi.Add.METHOD_API_COINNAME) @RequestParam("coinName") String coinName,
                         @ApiParam(TradingOnApi.Add.METHOD_API_UNITNAME) @RequestParam("unitName") String unitName) {
        tradingOnService.add(coinName,unitName,TradingOnConstant.STATE_CANCEL);
        return ResultDTO.requstSuccess();
    }
    
    /** 
    * @Description: 修改状态
    * @Param: [id, state, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/4/24 
    */ 
    @PostMapping("/updateState")
    @ApiOperation(value = TradingOnApi.UpdateState.METHOD_TITLE_NAME, notes = TradingOnApi.UpdateState.METHOD_TITLE_NOTE)
    public ResultDTO updateState(@ApiParam(TradingOnApi.UpdateState.METHOD_API_ID) @RequestParam("id") String id,@ApiParam(TradingOnApi.UpdateState.METHOD_API_STATE) @RequestParam("state") String state,HttpServletRequest request) {
        TradingOn trading = tradingOnService.selectByKey(id);
        if (trading == null){
            throw new QuantizedException(QuantizedResultEnums.TRANSACTION_PAIR_NOT_EXIST);
        }
        if (redisTemplate.hasKey(RedisKeyConstant.getTradingOnCancel(trading.getCoinName(),trading.getUnitName()))){
            throw new QuantizedException(QuantizedResultEnums.OPERATING_FREQUENCY);
        }else {
            redisTemplate.opsForValue().set(RedisKeyConstant.getTradingOnCancel(trading.getCoinName(),trading.getUnitName()),RedisKeyConstant.STATUS,RedisKeyConstant.TIME_OUT, TimeUnit.MINUTES);
        }
        //修改订阅交易对为订阅时订阅火币
        if (state.equals(TradingOnConstant.STATE_SUBSCRIBE)){
            tradingOnService.addSubscribe(id);
        }
        //修改交易对状态
        tradingOnService.updateState(trading,state);
        return ResultDTO.requstSuccess();
    }


}
