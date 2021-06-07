package com.blockchain.server.eos.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.eos.controller.api.EosWalletOutApi;
import com.blockchain.server.eos.service.WalletOutService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: Liusd
 * @create: 2019-03-26 17:32
 **/
@RestController
@RequestMapping("/walletOut")
@Api(EosWalletOutApi.CONTROLLER_API)
public class EosWalletOutController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(EosWalletOutController.class);

    @Autowired
    private WalletOutService walletOutService;

    /** 
    * @Description: 发币账户列表 
    * @Param: [status, pageNum, pageSize] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/27 
    */ 
    @GetMapping("/list")
    @ApiOperation(value = EosWalletOutApi.List.METHOD_TITLE_NAME, notes = EosWalletOutApi.List.METHOD_TITLE_NOTE)
    public ResultDTO walletInList(@ApiParam(EosWalletOutApi.List.METHOD_API_STATUS) @RequestParam(required = false, value = "status") String status,
                                  @ApiParam(EosWalletOutApi.List.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                                  @ApiParam(EosWalletOutApi.List.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(walletOutService.list(status));
    }

    /** 
    * @Description: 新增发币账户
    * @Param: [addr, tokenId, privateKey, remark, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/27 
    */ 
    @PostMapping("/insert")
    @ApiOperation(value = EosWalletOutApi.Insert.METHOD_TITLE_NAME, notes = EosWalletOutApi.Insert.METHOD_TITLE_NOTE)
    public ResultDTO insert(@ApiParam(EosWalletOutApi.Insert.METHOD_API_ACCOUNTNAME) @RequestParam("addr") String addr, @ApiParam(EosWalletOutApi.Insert.METHOD_API_TOKENNAME) @RequestParam("tokenId") String tokenId,@ApiParam(EosWalletOutApi.Insert.METHOD_API_PRIVATEKEY) @RequestParam("privateKey") String privateKey, @ApiParam(EosWalletOutApi.Insert.METHOD_API_REMARK) @RequestParam(required = false, value = "remark") String remark, HttpServletRequest request) {
        walletOutService.insert(addr, tokenId,privateKey,remark);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 修改发币账户 
    * @Param: [accountName, tokenName, remark, status, id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/27 
    */ 
    @PostMapping("/update")
    @ApiOperation(value = EosWalletOutApi.Update.METHOD_TITLE_NAME, notes = EosWalletOutApi.Update.METHOD_TITLE_NOTE)
    public ResultDTO update(@ApiParam(EosWalletOutApi.Update.METHOD_API_ACCOUNTNAME) @RequestParam("accountName") String accountName, @ApiParam(EosWalletOutApi.Update.METHOD_API_TOKENNAME) @RequestParam("tokenName") String tokenName, @ApiParam(EosWalletOutApi.Update.METHOD_API_REMARK) @RequestParam(required = false, value = "remark") String remark,@ApiParam(EosWalletOutApi.Update.METHOD_API_STATUS) @RequestParam("status") String status, @ApiParam(EosWalletOutApi.Update.METHOD_API_ID) @RequestParam("id") String id,   HttpServletRequest request) {
        walletOutService.update(accountName, tokenName,remark, status, id);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 删除发币账户
    * @Param: [id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/27 
    */ 
    @PostMapping("/delete")
    @ApiOperation(value = EosWalletOutApi.Delete.METHOD_TITLE_NAME, notes = EosWalletOutApi.Delete.METHOD_TITLE_NOTE)
    public ResultDTO delete(@ApiParam(EosWalletOutApi.Delete.METHOD_API_ID) @RequestParam("id") String id,
                            HttpServletRequest request) {
        walletOutService.delete(id);
        return ResultDTO.requstSuccess();
    }
}
