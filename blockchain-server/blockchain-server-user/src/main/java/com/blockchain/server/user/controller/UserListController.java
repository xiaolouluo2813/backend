package com.blockchain.server.user.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.base.security.RequiresPermissions;
import com.blockchain.server.user.common.constant.UserListConstant;
import com.blockchain.server.user.controller.api.UserListApi;
import com.blockchain.server.user.dto.BlackAndWhiteDto;
import com.blockchain.server.user.service.UserListService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/5 11:42
 * @user WIN10
 */
@RestController
@Api(UserListApi.USER_LIST_API)
@RequestMapping("/userList")
public class UserListController extends BaseController {

    @Autowired
    private UserListService userListService;

    /**
     * 根据黑名单类型查询黑名单列表
     * @return
     */
    // @RequiresPermissions("app:userList:listBlacklist")
    @ApiOperation(value = UserListApi.ListBlacklist.METHOD_API_NAME, notes = UserListApi.ListBlacklist.METHOD_API_NOTE)
    @GetMapping("/listBlacklist")
    public ResultDTO listBlacklist(@ApiParam(UserListApi.ListBlacklist.METHOD_API_TYPE) @RequestParam(value = "type", required = false) String type,
                                   @ApiParam(UserListApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                   @ApiParam(UserListApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BlackAndWhiteDto> list = userListService.listBlacklistWhitelist(UserListConstant.BLACKLIST_LIST_TYPE, type);
        return generatePage(list);
    }

    /**
     * 添加黑名单
     * @param userId
     * @param type
     * @return
     */
//    @RequiresPermissions("app:userList:addBlacklist")
    @ApiOperation(value = UserListApi.AddBlacklist.METHOD_API_NAME, notes = UserListApi.AddBlacklist.METHOD_API_NOTE)
    @PostMapping("/addBlacklist")
    public ResultDTO addBlacklist(@ApiParam(UserListApi.AddBlacklist.METHOD_API_USER_ID) @RequestParam("userId") String userId, @ApiParam(UserListApi.AddBlacklist.METHOD_API_TYPE) @RequestParam("type") String type) {
        userListService.addBlacklistWhitelist(userId, UserListConstant.BLACKLIST_LIST_TYPE, type);
        return ResultDTO.requstSuccess();
    }

    /**
     * 根据id删除黑名单
     * @param id
     * @return
     */
//    @RequiresPermissions("app:userList:delBlacklist")
    @ApiOperation(value = UserListApi.DelBlacklist.METHOD_API_NAME, notes = UserListApi.DelBlacklist.METHOD_API_NOTE)
    @PostMapping("/delBlacklist")
    public ResultDTO delBlacklist(@ApiParam(UserListApi.DelBlacklist.METHOD_API_ID) @RequestParam("id") String id) {
        userListService.deleteUserList(id);
        return ResultDTO.requstSuccess();
    }

    /**
     * 根据白名单类型查询白名单列表
     * @return
     */
    // @RequiresPermissions("app:userList:listWhitelist")
    @ApiOperation(value = UserListApi.ListWhitelist.METHOD_API_NAME, notes = UserListApi.ListWhitelist.METHOD_API_NOTE)
    @GetMapping("/listWhitelist")
    public ResultDTO listWhitelist(@ApiParam(UserListApi.ListWhitelist.METHOD_API_TYPE) @RequestParam(value = "type", required = false) String type,
                                   @ApiParam(UserListApi.METHOD_API_PAGE_NUM) @RequestParam(value = "pageNum", required = false, defaultValue = "0") Integer pageNum,
                                   @ApiParam(UserListApi.METHOD_API_PAGE_SIZE) @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BlackAndWhiteDto> list = userListService.listBlacklistWhitelist(UserListConstant.WHITELIST_LIST_TYPE, type);
        return generatePage(list);
    }

    /**
     * 添加白名单
     * @param userId
     * @param type
     * @return
     */
//    @RequiresPermissions("app:userList:addWhitelist")
    @ApiOperation(value = UserListApi.AddWhitelist.METHOD_API_NAME, notes = UserListApi.AddWhitelist.METHOD_API_NOTE)
    @PostMapping("/addWhitelist")
    public ResultDTO addWhitelist(@ApiParam(UserListApi.AddWhitelist.METHOD_API_USER_ID) @RequestParam("userId") String userId, @ApiParam(UserListApi.AddWhitelist.METHOD_API_TYPE) @RequestParam("type") String type) {
        userListService.addBlacklistWhitelist(userId, UserListConstant.WHITELIST_LIST_TYPE, type);
        return ResultDTO.requstSuccess();
    }

    /**
     * 根据id删除白名单
     * @param id
     * @return
     */
//    @RequiresPermissions("app:userList:delWhitelist")
    @ApiOperation(value = UserListApi.DelWhitelist.METHOD_API_NAME, notes = UserListApi.DelWhitelist.METHOD_API_NOTE)
    @PostMapping("/delWhitelist")
    public ResultDTO delWhitelist(@ApiParam(UserListApi.DelWhitelist.METHOD_API_ID) @RequestParam("id") String id) {
        userListService.deleteUserList(id);
        return ResultDTO.requstSuccess();
    }

    /**
     * 根据用户id查找用户黑白名单
     * @param userId
     * @return
     */
//    @RequiresPermissions("app:userList:listUserListByUserId")
    @ApiOperation(value = UserListApi.ListUserListByUserId.METHOD_API_NAME, notes = UserListApi.ListUserListByUserId.METHOD_API_NOTE)
    @PostMapping("/listUserListByUserId")
    public ResultDTO listUserListByUserId(@ApiParam(UserListApi.ListUserListByUserId.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        return ResultDTO.requstSuccess(userListService.listUserListByUserId(userId));
    }

}
