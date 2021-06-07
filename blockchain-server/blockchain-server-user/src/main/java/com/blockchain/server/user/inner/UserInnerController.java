package com.blockchain.server.user.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.user.inner.api.UserApi;
import com.blockchain.server.user.service.UserMainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * @author Harvey
 * @date 2019/3/9 11:57
 * @user WIN10
 */
@RestController
@Api(UserApi.USER_API)
@RequestMapping("/inner/user")
public class UserInnerController {

    @Autowired
    private UserMainService userMainService;

    /**
     * 查询单个用户信息
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = UserApi.SelectUserInfo.METHOD_API_NAME, notes = UserApi.SelectUserInfo.METHOD_API_NOTE)
    @PostMapping("/selectUserInfo")
    public ResultDTO selectUserInfo(@ApiParam(UserApi.SelectUserInfo.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        UserBaseInfoDTO userInfoDto = userMainService.selectUserInfo(userId);
        return ResultDTO.requstSuccess(userInfoDto);
    }

    /**
     * 查询多个用户信息
     * <p>
     * 根据id
     *
     * @param userIds
     * @return
     */
    @ApiOperation(value = UserApi.ListUserInfo.METHOD_API_NAME, notes = UserApi.ListUserInfo.METHOD_API_NOTE)
    @PostMapping("/listUserInfo")
    public ResultDTO listUserInfo(@ApiParam(UserApi.ListUserInfo.METHOD_API_USER_IDS) @RequestBody Set<String> userIds) {
        // TODO 更换公共dto
        Map<String, UserBaseInfoDTO> map = userMainService.listUserInfo(userIds);
        return ResultDTO.requstSuccess(map);
    }

    /**
     * 查询多个用户信息
     * <p>
     * 根据账户
     *
     * @param userName
     * @return
     */
    @ApiOperation(value = UserApi.SelectUserInfoByUserName.METHOD_API_NAME, notes = UserApi.SelectUserInfoByUserName.METHOD_API_NOTE)
    @PostMapping("/selectUserInfoByUserName")
    public ResultDTO selectUserInfoByUserName(@ApiParam(UserApi.SelectUserInfoByUserName.METHOD_API_USERNAME) @RequestParam("userName") String userName) {
        // TODO 更换公共dto
        UserBaseInfoDTO userInfo = userMainService.selectUserInfoByUserName(userName);
        return ResultDTO.requstSuccess(userInfo);
    }

}
