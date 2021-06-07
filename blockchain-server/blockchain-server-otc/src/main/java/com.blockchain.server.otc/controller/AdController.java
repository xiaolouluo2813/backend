package com.blockchain.server.otc.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.otc.controller.api.AdApi;
import com.blockchain.server.otc.dto.ad.ListAdResultDTO;
import com.blockchain.server.otc.service.AdService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(AdApi.AD_API)
@RestController
@RequestMapping("/ad")
public class AdController extends BaseController {

    @Autowired
    private AdService adService;

    @ApiOperation(value = AdApi.listAd.METHOD_TITLE_NAME,
            notes = AdApi.listAd.METHOD_TITLE_NOTE)
    @GetMapping("/list")
    public ResultDTO listAd(@ApiParam(AdApi.listAd.METHOD_API_AD_NUMBER) @RequestParam(value = "adNumber", required = false) String adNumber,
                            @ApiParam(AdApi.listAd.METHOD_API_USER_NAME) @RequestParam(value = "userName", required = false) String userName,
                            @ApiParam(AdApi.listAd.METHOD_API_COIN_NAME) @RequestParam(value = "coinName", required = false) String coinName,
                            @ApiParam(AdApi.listAd.METHOD_API_UNIT_NAME) @RequestParam(value = "unitName", required = false) String unitName,
                            @ApiParam(AdApi.listAd.METHOD_API_AD_TYPE) @RequestParam(value = "adType", required = false) String adType,
                            @ApiParam(AdApi.listAd.METHOD_API_AD_STATUS) @RequestParam(value = "adStatus", required = false) String adStatus,
                            @ApiParam(AdApi.listAd.METHOD_API_BEGIN_TIME) @RequestParam(value = "beginTime", required = false) String beginTime,
                            @ApiParam(AdApi.listAd.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                            @ApiParam(AdApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                            @ApiParam(AdApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ListAdResultDTO> resultDTOS = adService.listAd(adNumber, userName, coinName, unitName, adType, adStatus, beginTime, endTime);
        return generatePage(resultDTOS);
    }

    @ApiOperation(value = AdApi.cancelAd.METHOD_TITLE_NAME,
            notes = AdApi.cancelAd.METHOD_TITLE_NOTE)
    @PostMapping("/cancelAd")
    public ResultDTO cancelAd(@ApiParam(AdApi.cancelAd.METHOD_API_AD_ID) @RequestParam(value = "adId") String adId) {
        adService.cancelAd(SecurityUtils.getUserId(), HttpRequestUtil.getIpAddr(), adId);
        return ResultDTO.requstSuccess();
    }
}
