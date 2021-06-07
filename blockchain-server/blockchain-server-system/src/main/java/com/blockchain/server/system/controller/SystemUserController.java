package com.blockchain.server.system.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.MD5Utils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.base.security.RequiresPermissions;
import com.blockchain.server.system.common.enums.SystemResultEnums;
import com.blockchain.server.system.controller.api.SystemRoleApi;
import com.blockchain.server.system.controller.api.SystemUserApi;
import com.blockchain.server.system.dto.SystemUserAddDto;
import com.blockchain.server.system.entity.SystemUser;
import com.blockchain.server.system.service.SystemUserService;
import com.github.pagehelper.PageHelper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-05 08:49
 **/
@RestController
@RequestMapping("/systemUser")
@Api(SystemUserApi.CONTROLLER_API)
public class SystemUserController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(SystemUserController.class);

    @Autowired
    private SystemUserService systemUserService;

    /** 
    * @Description: 管理员列表 
    * @Param: [status, name, phone, pageNum, pageSize] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @GetMapping("/systemUserList")
    @ApiOperation(value = SystemUserApi.SystemUserList.METHOD_TITLE_NAME, notes = SystemUserApi.SystemUserList.METHOD_TITLE_NOTE)
    public ResultDTO systemUserList(@ApiParam(SystemUserApi.SystemUserList.METHOD_API_STATUS) @RequestParam(required = false, value = "status") String status,
                                    @ApiParam(SystemUserApi.SystemUserList.METHOD_API_NAME) @RequestParam(required = false, value = "name") String name,
                                    @ApiParam(SystemUserApi.SystemUserList.METHOD_API_PHONE) @RequestParam(required = false, value = "phone") String phone,
                                    @ApiParam(SystemUserApi.SystemUserList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                                    @ApiParam(SystemUserApi.SystemUserList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(systemUserService.systemUserList(status,name,phone));
    }

    /** 
    * @Description: 新增管理员 
    * @Param: [systemUserAddDto, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @PostMapping("/insertSystemUser")
    @RequiresPermissions("AddManager")
    @ApiOperation(value = SystemUserApi.InsertSystemUser.METHOD_TITLE_NAME, notes = SystemUserApi.InsertSystemUser.METHOD_TITLE_NOTE)
    public ResultDTO insertSystemUser(@ApiParam(SystemUserApi.InsertSystemUser.METHOD_API_SYSTEMUSERADDDTO) @RequestBody SystemUserAddDto systemUserAddDto, HttpServletRequest request) {
        systemUserService.insert(systemUserAddDto);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 真实姓名唯一校验 
    * @Param: [account] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @GetMapping("/accountCheck")
    @ApiOperation(value = SystemUserApi.AccountCheck.METHOD_TITLE_NAME, notes = SystemUserApi.AccountCheck.METHOD_TITLE_NOTE)
    public ResultDTO accountCheck(@ApiParam(SystemUserApi.AccountCheck.METHOD_API_ACCOUNT) @RequestParam("account") String account) {
        systemUserService.accountCheck(account);
        return ResultDTO.requstSuccess();
    }
    
    /** 
    * @Description: 根据id查询用户信息
    * @Param: [id] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/6/20 
    */ 
    @GetMapping("/showById")
    @ApiOperation(value = SystemUserApi.SystemUserList.METHOD_TITLE_NAME, notes = SystemUserApi.SystemUserList.METHOD_TITLE_NOTE)
    public ResultDTO showById(@ApiParam(SystemUserApi.SystemUserList.METHOD_API_STATUS) @RequestParam("id") String id) {
        return ResultDTO.requstSuccess(systemUserService.showById(id));
    }

    /** 
    * @Description: 修改管理员基础信息 
    * @Param: [systemUserAddDto, id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @PostMapping("/updateSystemUser")
    @ApiOperation(value = SystemUserApi.UpdateSystemUser.METHOD_TITLE_NAME, notes = SystemUserApi.UpdateSystemUser.METHOD_TITLE_NOTE)
    public ResultDTO updateSystemUser(@ApiParam(SystemUserApi.UpdateSystemUser.METHOD_API_SYSTEMUSERADDDTO) @RequestBody SystemUserAddDto systemUserAddDto) {
        systemUserService.update(systemUserAddDto, systemUserAddDto.getId());
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 修改管理员状态 
    * @Param: [status, id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @PostMapping("/updateSystemUserStatus")
    @RequiresPermissions("UpdManagerStatus")
    @ApiOperation(value = SystemUserApi.UpdateSystemUserStatus.METHOD_TITLE_NAME, notes = SystemUserApi.UpdateSystemUserStatus.METHOD_TITLE_NOTE)
    public ResultDTO updateSystemUserStatus(@ApiParam(SystemUserApi.UpdateSystemUserStatus.METHOD_API_STATUS) @RequestParam("status") String status,
                                            @ApiParam(SystemUserApi.UpdateSystemUserStatus.METHOD_API_ID) @RequestParam("id") String id,
                                            HttpServletRequest request) {
        systemUserService.updateStatus(status, id);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 设置用户角色 
    * @Param: [roleIds, id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @PostMapping("/setUserRoles")
    @RequiresPermissions("SetManagerRole")
    @ApiOperation(value = SystemUserApi.SetUserRoles.METHOD_TITLE_NAME, notes = SystemUserApi.SetUserRoles.METHOD_TITLE_NOTE)
    public ResultDTO setUserRoles(@ApiParam(SystemUserApi.SetUserRoles.METHOD_API_ROLEIDS) @RequestParam("roleIds") String roleIds,
                                  @ApiParam(SystemUserApi.SetUserRoles.METHOD_API_ID) @RequestParam("id") String id,
                                  HttpServletRequest request) {
        systemUserService.setUserRoles(roleIds, id);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 修改用户密码 
    * @Param: [id, newPassword, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @PostMapping("/updateUserPassword")
    @RequiresPermissions("UpdPassword")
    @ApiOperation(value = SystemUserApi.UpdateUserPassword.METHOD_TITLE_NAME, notes = SystemUserApi.UpdateUserPassword.METHOD_TITLE_NOTE)
    public ResultDTO updateUserPassword( @ApiParam(SystemUserApi.UpdateUserPassword.METHOD_API_ID) @RequestParam("id") String id,
                                         @ApiParam(SystemUserApi.UpdateUserPassword.METHOD_API_NEWPASSWORD) @RequestParam("newPassword") String newPassword,
                                         HttpServletRequest request) {
        systemUserService.updateUserPassword(MD5Utils.MD5(newPassword), id);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 删除用户
    * @Param: [id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @PostMapping("/deleteUser")
    @RequiresPermissions("DelManager")
    @ApiOperation(value = SystemUserApi.DeleteUser.METHOD_TITLE_NAME, notes = SystemUserApi.DeleteUser.METHOD_TITLE_NOTE)
    public ResultDTO deleteUser( @ApiParam(SystemUserApi.DeleteUser.METHOD_API_ID) @RequestParam("id") String id,
                                         HttpServletRequest request) {
        systemUserService.deleteUser(id);
        return ResultDTO.requstSuccess();
    }

}
