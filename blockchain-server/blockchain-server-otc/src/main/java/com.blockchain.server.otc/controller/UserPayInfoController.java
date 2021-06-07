package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.UserPayApi;
import com.blockchain.server.otc.dto.userpayinfo.ListUserPayInfoResultDTO;
import com.blockchain.server.otc.service.UserPayInfoService;
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

@Api(UserPayApi.USER_PAY_API)
@RestController
@RequestMapping("/userPayInfo")
public class UserPayInfoController extends BaseController {

    @Autowired
    private UserPayInfoService userPayInfoService;

    @ApiOperation(value = UserPayApi.listUserPayInfo.METHOD_TITLE_NAME,
            notes = UserPayApi.listUserPayInfo.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listUserPayInfo(@ApiParam(value = UserPayApi.listUserPayInfo.METHOD_API_USERNAME) @RequestParam(value = "userName", required = false) String userName,
                                     @ApiParam(value = UserPayApi.listUserPayInfo.METHOD_API_PAYTYPE) @RequestParam(value = "payType", required = false) String payType,
                                     @ApiParam(value = UserPayApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                     @ApiParam(value = UserPayApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListUserPayInfoResultDTO> resultDTOS = userPayInfoService.listUserPayInfo(userName, payType);
        return generatePage(resultDTOS);
    }
}
