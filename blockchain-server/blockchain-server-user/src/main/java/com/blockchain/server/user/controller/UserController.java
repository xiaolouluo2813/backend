package com.blockchain.server.user.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.user.controller.api.UserApi;
import com.blockchain.server.user.dto.UserListDto;
import com.blockchain.server.user.service.UserInfoService;
import com.blockchain.server.user.service.UserMainService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/4 18:37
 * @user WIN10
 */
@Api(UserApi.USER_API)
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserMainService userMainService;

    /**
     * 根据需求查询用户列表
     *
     * @param mobilePhone
     * @param email
     * @param lowAuth
     * @param highAuth
     * @param startTime
     * @param endTime
     * @return
     */
    // @RequiresPermissions("app:user:listSearchUser")
    @ApiOperation(value = UserApi.ListSearchUser.METHOD_API_NAME, notes = UserApi.ListSearchUser.METHOD_API_NOTE)
    @PostMapping("/listSearchUser")
    public ResultDTO listSearchUser(@ApiParam(UserApi.ListSearchUser.METHOD_API_MOBILE_PHONE) @RequestParam(value = "mobilePhone", required = false) String mobilePhone,
                                    @ApiParam(UserApi.ListSearchUser.METHOD_API_EMAIL) @RequestParam(value = "email", required = false) String email,
                                    @ApiParam(UserApi.ListSearchUser.METHOD_API_LOW_AUTH) @RequestParam(value = "lowAuth", required = false) String lowAuth,
                                    @ApiParam(UserApi.ListSearchUser.METHOD_API_HIGH_AUTH) @RequestParam(value = "highAuth", required = false) String highAuth,
                                    @ApiParam(UserApi.ListSearchUser.METHOD_API_HIGH_AUTH) @RequestParam(value = "invitationCode", required = false) String invitationCode,
                                    @ApiParam(UserApi.ListSearchUser.METHOD_API_START_TIME) @RequestParam(value = "startTime", required = false) String startTime,
                                    @ApiParam(UserApi.ListSearchUser.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                                    @ApiParam(UserApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @ApiParam(UserApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserListDto> userListDtos = userInfoService.listSearchUser(mobilePhone, email, lowAuth, highAuth, invitationCode, startTime, endTime);
        return generatePage(userListDtos);
    }

    /**
     * 实名审核
     *
     * @param mobilePhone
     * @param realName
     * @param lowAuth
     * @param highAuth
     * @param startTime
     * @param endTime
     * @param pageNum
     * @param pageSize
     * @return
     */
    // @RequiresPermissions("app:user:realNameAudit")
    @ApiOperation(value = UserApi.RealNameAudit.METHOD_API_NAME, notes = UserApi.RealNameAudit.METHOD_API_NOTE)
    @PostMapping("/realNameAudit")
    public ResultDTO realNameAudit(@ApiParam(UserApi.RealNameAudit.METHOD_API_MOBILE_PHONE) @RequestParam(value = "mobilePhone", required = false) String mobilePhone,
                                   @ApiParam(UserApi.RealNameAudit.METHOD_API_REAL_NAME) @RequestParam(value = "realName", required = false) String realName,
                                   @ApiParam(UserApi.RealNameAudit.METHOD_API_LOW_AUTH) @RequestParam(value = "lowAuth", required = false) String lowAuth,
                                   @ApiParam(UserApi.RealNameAudit.METHOD_API_HIGH_AUTH) @RequestParam(value = "highAuth", required = false) String highAuth,
                                   @ApiParam(UserApi.RealNameAudit.METHOD_API_START_TIME) @RequestParam(value = "startTime", required = false) String startTime,
                                   @ApiParam(UserApi.RealNameAudit.METHOD_API_END_TIME) @RequestParam(value = "endTime", required = false) String endTime,
                                   @ApiParam(UserApi.RealNameAudit.METHOD_API_END_TIME) @RequestParam(value = "sort", required = false) String sort,
                                   @ApiParam(UserApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                   @ApiParam(UserApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserListDto> userListDtos = userInfoService.realNameAudit(mobilePhone, realName, lowAuth, highAuth, startTime, endTime, sort);
        return generatePage(userListDtos);
    }

    // @RequiresPermissions("app:user:userAssets")
    /*@ApiOperation(value = UserApi.UserAssets.METHOD_API_NAME, notes = UserApi.UserAssets.METHOD_API_NOTE)
    @PostMapping("/userAssets")
    public ResultDTO userAssets(@ApiParam(UserApi.UserAssets.METHOD_API_REAL_NAME) @RequestParam("realName") String realName,
                                @ApiParam(UserApi.UserAssets.METHOD_API_MOBILE_PHONE) @RequestParam("mobilePhone") String mobilePhone,
                                @ApiParam(UserApi.UserAssets.METHOD_API_EMAIL) @RequestParam("email") String email,
                                @ApiParam(UserApi.UserAssets.METHOD_API_DATE) @RequestParam("date") Date date) {
        List<UserListDto> userListDtos = null;
        return ResultDTO.requstSuccess(userListDtos);
    }*/

    /**
     * 查询用户基本信息
     *
     * @param userId
     * @return
     */
    // @RequiresPermissions("app:user:selectUserInfo")
    @ApiOperation(value = UserApi.SelectUserInfo.METHOD_API_NAME, notes = UserApi.SelectUserInfo.METHOD_API_NOTE)
    @PostMapping("/selectUserInfo")
    public ResultDTO selectUserInfo(@ApiParam(UserApi.SelectUserInfo.METHOD_API_USER_id) @RequestParam("userId") String userId) {
        UserListDto userListDto = userInfoService.selectUserInfoAndMainByUserId(userId);
        return ResultDTO.requstSuccess(userListDto);
    }

    /**
     * 查询用户资产信息
     *
     * @param realName
     * @param mobilePhone
     * @param email
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = UserApi.ListSearchUserAsset.METHOD_API_NAME, notes = UserApi.ListSearchUserAsset.METHOD_API_NOTE)
    @PostMapping("/listSearchUserAsset")
    public ResultDTO listSearchUserAsset(@ApiParam(UserApi.ListSearchUserAsset.METHOD_API_REAL_NAME) @RequestParam(value = "realName", required = false) String realName,
                                         @ApiParam(UserApi.ListSearchUserAsset.METHOD_API_MOBILE_PHONE) @RequestParam(value = "mobilePhone", required = false) String mobilePhone,
                                         @ApiParam(UserApi.ListSearchUserAsset.METHOD_API_EMAIL) @RequestParam(value = "email", required = false) String email,
                                         @ApiParam(UserApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                         @ApiParam(UserApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserListDto> userListDtos = userInfoService.listSearchUserAsset(realName, mobilePhone, email);
        return generatePage(userListDtos);
    }

    /**
     * 修改用户手机
     * @param userId
     * @return
     */
    @ApiOperation(value = UserApi.UpdateMobilePhone.METHOD_API_NAME, notes = UserApi.UpdateMobilePhone.METHOD_API_NOTE)
    @PostMapping("/updateMobilePhone")
    public ResultDTO updateMobilePhone(@ApiParam(UserApi.UpdateMobilePhone.METHOD_API_USER_ID) @RequestParam("userId") String userId, @ApiParam(UserApi.UpdateMobilePhone.METHOD_API_MUBILE_PHONE) @RequestParam("mobilePhone") String mobilePhone) {
        userMainService.updateMobilePhone(userId, mobilePhone);
        return ResultDTO.requstSuccess();
    }

    /**
     * 修改用户身份证号码
     * @param userId
     * @return
     */
    @ApiOperation(value = UserApi.UpdateIdentityCode.METHOD_API_NAME, notes = UserApi.UpdateIdentityCode.METHOD_API_NOTE)
    @PostMapping("/updateIdentityCode")
    public ResultDTO updateIdentityCode(@ApiParam(UserApi.UpdateIdentityCode.METHOD_API_USER_ID) @RequestParam("userId") String userId, @ApiParam(UserApi.UpdateIdentityCode.METHOD_API_IDENTITY_CODE) @RequestParam("identityCode") String identityCode) {
        userMainService.updateIdentityCode(userId, identityCode);
        return ResultDTO.requstSuccess();
    }
}
