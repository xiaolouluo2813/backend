package com.blockchain.server.user.inner;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.user.common.constant.UserListConstant;
import com.blockchain.server.user.entity.UserList;
import com.blockchain.server.user.inner.api.UserApi;
import com.blockchain.server.user.inner.api.UserListApi;
import com.blockchain.server.user.service.UserListService;
import com.blockchain.server.user.service.UserMainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Harvey
 * @date 20/9 11:57
 * @user WIN10
 */
@RestController
@Api(UserListApi.USER_LIST_API)
@RequestMapping("/inner/userList")
public class UserListInnerController {

    @Autowired
    private UserListService userListService;

    /**
     * 查询用户黑名单信息
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = UserListApi.ListBlacklist.METHOD_API_NAME, notes = UserListApi.ListBlacklist.METHOD_API_NOTE)
    @PostMapping("/listBlacklist")
    public ResultDTO listBlacklist(@ApiParam(UserListApi.ListBlacklist.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        List<UserList> users = userListService.listUserListByUserId(userId, UserListConstant.BLACKLIST_LIST_TYPE);
        return ResultDTO.requstSuccess(users);
    }

    /**
     * 查询用户白名单信息
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = UserListApi.ListWhitelist.METHOD_API_NAME, notes = UserListApi.ListWhitelist.METHOD_API_NOTE)
    @PostMapping("/listWhitelist")
    public ResultDTO listWhitelist(@ApiParam(UserListApi.ListWhitelist.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        List<UserList> users = userListService.listUserListByUserId(userId, UserListConstant.WHITELIST_LIST_TYPE);
        return ResultDTO.requstSuccess(users);
    }

    /**
     * 查询用户是否存在黑名单中
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = UserListApi.ExistBlacklist.METHOD_API_NAME, notes = UserListApi.ExistBlacklist.METHOD_API_NOTE)
    @PostMapping("/existBlacklist")
    public ResultDTO ExistBlacklist(@ApiParam(UserListApi.ExistBlacklist.METHOD_API_USER_ID) @RequestParam("userId") String userId,
                                    @ApiParam(UserListApi.ExistBlacklist.METHOD_API_TYPE) @RequestParam("type") String type) {
        Integer row = userListService.existUserList(userId, UserListConstant.BLACKLIST_LIST_TYPE, type);
        return ResultDTO.requstSuccess(row);
    }

    /**
     * 查询用户是否存在白名单中
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = UserListApi.ExistWhitelist.METHOD_API_NAME, notes = UserListApi.ExistWhitelist.METHOD_API_NOTE)
    @PostMapping("/existWhitelist")
    public ResultDTO existWhitelist(@ApiParam(UserListApi.ExistWhitelist.METHOD_API_USER_ID) @RequestParam("userId") String userId,
                                    @ApiParam(UserListApi.ExistWhitelist.METHOD_API_TYPE) @RequestParam("type") String type) {
        Integer row = userListService.existUserList(userId, UserListConstant.BLACKLIST_LIST_TYPE, type);
        return ResultDTO.requstSuccess(row);
    }

}
