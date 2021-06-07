package com.blockchain.server.eos.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.eos.controller.api.EosWalletInApi;
import com.blockchain.server.eos.entity.WalletIn;
import com.blockchain.server.eos.service.WalletInService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-26 16:18
 **/
@RestController
@RequestMapping("/walletIn")
@Api(EosWalletInApi.CONTROLLER_API)
public class EosWalletInController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(EosWalletInController.class);

    @Autowired
    private WalletInService walletInService;

    /**
    * @Description: 充币账户列表
    * @Param: [status, pageNum, pageSize]
    * @return: com.blockchain.common.base.dto.ResultDTO
    * @Author: Liu.sd
    * @Date: 2019/3/26
    */
    @GetMapping("/list")
    @ApiOperation(value = EosWalletInApi.List.METHOD_TITLE_NAME, notes = EosWalletInApi.List.METHOD_TITLE_NOTE)
    public ResultDTO walletInList(@ApiParam(EosWalletInApi.List.METHOD_API_STATUS) @RequestParam(required = false, value = "status") String status,
                                      @ApiParam(EosWalletInApi.List.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                                      @ApiParam(EosWalletInApi.List.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WalletIn> list = new ArrayList<>();
        if (walletInService.list(status)!=null){
            list.add(walletInService.list(status).get(0));
        }
        return generatePage(list);
//        return generatePage(walletInService.list(status));
    }

    /** 
    * @Description: 新增充币账户
    * @Param: [addr, tokenId, privateKey, remark, request]
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/27 
    */ 
    @PostMapping("/insert")
    @ApiOperation(value = EosWalletInApi.Insert.METHOD_TITLE_NAME, notes = EosWalletInApi.Insert.METHOD_TITLE_NOTE)
    public ResultDTO insert(@ApiParam(EosWalletInApi.Insert.METHOD_API_ACCOUNTNAME) @RequestParam("addr") String addr, @ApiParam(EosWalletInApi.Insert.METHOD_API_TOKENNAME) @RequestParam("tokenId") String tokenId, @ApiParam(EosWalletInApi.Insert.METHOD_API_REMARK) @RequestParam(required = false, value = "remark") String remark, HttpServletRequest request) {
        walletInService.insert(addr, tokenId, remark);
        return ResultDTO.requstSuccess();
    }

    /**
     * @Description:  修改充币账户
     * @Param: [details, jumpUrl, rank, id, request] 
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/3/25 
     */
    @PostMapping("/update")
    @ApiOperation(value = EosWalletInApi.Update.METHOD_TITLE_NAME, notes = EosWalletInApi.Update.METHOD_TITLE_NOTE)
    public ResultDTO update(@ApiParam(EosWalletInApi.Insert.METHOD_API_ACCOUNTNAME) @RequestParam("addr") String addr, @ApiParam(EosWalletInApi.Insert.METHOD_API_TOKENNAME) @RequestParam("tokenId") String tokenId, @ApiParam(EosWalletInApi.Insert.METHOD_API_REMARK) @RequestParam(required = false, value = "remark") String remark,@ApiParam(EosWalletInApi.Update.METHOD_API_ID) @RequestParam("id") String id,   HttpServletRequest request) {
        walletInService.update(addr, tokenId,remark, null, id);
        return ResultDTO.requstSuccess();
    }

    /**
     * @Description: 删除充币账户
     * @Param: [id, request] 
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/3/25 
     */
    @PostMapping("/delete")
    @ApiOperation(value = EosWalletInApi.Delete.METHOD_TITLE_NAME, notes = EosWalletInApi.Delete.METHOD_TITLE_NOTE)
    public ResultDTO delete(@ApiParam(EosWalletInApi.Delete.METHOD_API_ID) @RequestParam("id") String id,
                                  HttpServletRequest request) {
        walletInService.delete(id);
        return ResultDTO.requstSuccess();
    }
}
