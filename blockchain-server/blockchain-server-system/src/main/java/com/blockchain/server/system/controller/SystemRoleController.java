package com.blockchain.server.system.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.PageDTO;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.base.security.RequiresPermissions;
import com.blockchain.server.system.controller.api.SystemMenuApi;
import com.blockchain.server.system.controller.api.SystemRoleApi;
import com.blockchain.server.system.controller.api.SystemRoleApi;
import com.blockchain.server.system.service.SystemRoleService;
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
 * @create: 2019-03-05 15:34
 **/
@RestController
@RequestMapping("/systemRole")
@Api(SystemRoleApi.CONTROLLER_API)
public class SystemRoleController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(SystemRoleController.class);

    @Autowired
    private SystemRoleService systemRoleService;

    /** 
    * @Description:  角色列表
    * @Param: [status, name, pageNum, pageSize] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @GetMapping("/systemRoleList")
    @ApiOperation(value = SystemRoleApi.SystemRoleList.METHOD_TITLE_NAME, notes = SystemRoleApi.SystemRoleList.METHOD_TITLE_NOTE)
    public ResultDTO systemRoleList(@ApiParam(SystemRoleApi.SystemRoleList.METHOD_API_STATUS) @RequestParam(required = false, value = "status") String status,
                                    @ApiParam(SystemRoleApi.SystemRoleList.METHOD_API_NAME) @RequestParam(required = false, value = "name") String name,
                                    @ApiParam(SystemRoleApi.SystemRoleList.METHOD_API_PAGENUM) @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
                                    @ApiParam(SystemRoleApi.SystemRoleList.METHOD_API_PAGESIZE) @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return generatePage(systemRoleService.systemRoleList(status,name));
    }

    /** 
    * @Description: 用户角色列表 已弃用 
    * @Param: [status, userId] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @GetMapping("/userRoleList")
    @ApiOperation(value = SystemRoleApi.UserRoleList.METHOD_TITLE_NAME, notes = SystemRoleApi.UserRoleList.METHOD_TITLE_NOTE)
    public ResultDTO userRoleList(@ApiParam(SystemRoleApi.UserRoleList.METHOD_API_STATUS) @RequestParam(required = false, value = "status",defaultValue = "Y") String status,@ApiParam(SystemRoleApi.UserRoleList.METHOD_API_USERID) @RequestParam("userId") String userId) {
        return ResultDTO.requstSuccess(systemRoleService.userRoleList(status,userId));
    }

    /** 
    * @Description: 新增角色 
    * @Param: [code, name, ranking, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @PostMapping("/insertSystemRole")
    @RequiresPermissions("AddRole")
    @ApiOperation(value = SystemRoleApi.InsertSystemRole.METHOD_TITLE_NAME, notes = SystemRoleApi.InsertSystemRole.METHOD_TITLE_NOTE)
    public ResultDTO insertSystemRole(@ApiParam(SystemRoleApi.InsertSystemRole.METHOD_API_CODE) @RequestParam("code") String code,
                                      @ApiParam(SystemRoleApi.InsertSystemRole.METHOD_API_NAME) @RequestParam("name") String name,
                                      @ApiParam(SystemRoleApi.InsertSystemRole.METHOD_API_RANKING) @RequestParam("ranking") Integer ranking,
                                      HttpServletRequest request) {
        systemRoleService.insert(code,name,ranking);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 角色标识唯一校验 
    * @Param: [code] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @GetMapping("/codeCheck")
    @ApiOperation(value = SystemRoleApi.CodeCheck.METHOD_TITLE_NAME, notes = SystemRoleApi.CodeCheck.METHOD_TITLE_NOTE)
    public ResultDTO codeCheck(@ApiParam(SystemRoleApi.CodeCheck.METHOD_API_CODE) @RequestParam("code") String code) {
        systemRoleService.codeCheck(code);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 修改角色基础信息 已弃用 
    * @Param: [code, name, ranking, id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15
    */ 
    @PostMapping("/updateSystemRole")
    @ApiOperation(value = SystemRoleApi.UpdateSystemRole.METHOD_TITLE_NAME, notes = SystemRoleApi.UpdateSystemRole.METHOD_TITLE_NOTE)
    public ResultDTO updateSystemRole(@ApiParam(SystemRoleApi.UpdateSystemRole.METHOD_API_CODE) @RequestParam(required = false, value = "code") String code,
                                      @ApiParam(SystemRoleApi.UpdateSystemRole.METHOD_API_NAME) @RequestParam(required = false, value = "name") String name,
                                      @ApiParam(SystemRoleApi.UpdateSystemRole.METHOD_API_RANKING) @RequestParam(required = false, value = "ranking") Integer ranking,
                                      @ApiParam(SystemRoleApi.UpdateSystemRole.METHOD_API_ID) @RequestParam("id") String id,
                                      HttpServletRequest request) {
        systemRoleService.update(code,name,ranking, id);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description:  修改角色状态 
    * @Param: [status, id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @PostMapping("/updateSystemRoleStatus")
    @RequiresPermissions("UpdRoleStatus")
    @ApiOperation(value = SystemRoleApi.UpdateSystemRoleStatus.METHOD_TITLE_NAME, notes = SystemRoleApi.UpdateSystemRoleStatus.METHOD_TITLE_NOTE)
    public ResultDTO updateSystemRoleStatus(@ApiParam(SystemRoleApi.UpdateSystemRoleStatus.METHOD_API_STATUS) @RequestParam("status") String status,
                                            @ApiParam(SystemRoleApi.UpdateSystemRoleStatus.METHOD_API_ID) @RequestParam("id") String id,
                                            HttpServletRequest request) {
        systemRoleService.updateStatus(status, id);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 角色绑定菜单 
    * @Param: [menuIds, id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @PostMapping("/setRoleMenus")
    @RequiresPermissions("Authority")
    @ApiOperation(value = SystemRoleApi.SetRoleMenus.METHOD_TITLE_NAME, notes = SystemRoleApi.SetRoleMenus.METHOD_TITLE_NOTE)
    public ResultDTO setRoleMenus(@ApiParam(SystemRoleApi.SetRoleMenus.METHOD_API_MENUIDS) @RequestParam("menuIds") String menuIds,
                                  @ApiParam(SystemRoleApi.SetRoleMenus.METHOD_API_ID) @RequestParam("id") String id,
                                  HttpServletRequest request) {
        String account = SecurityUtils.getUsername();
        systemRoleService.setRoleMenus(menuIds, id,account);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 删除角色 
    * @Param: [id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @PostMapping("/deleteRole")
    @RequiresPermissions("DelRole")
    @ApiOperation(value = SystemRoleApi.DeleteRole.METHOD_TITLE_NAME, notes = SystemRoleApi.DeleteRole.METHOD_TITLE_NOTE)
    public ResultDTO deleteRole(@ApiParam(SystemRoleApi.DeleteRole.METHOD_API_ID) @RequestParam("id") String id,
                                  HttpServletRequest request) {
        systemRoleService.deleteRole(id);
        return ResultDTO.requstSuccess();
    }
    
    /** 
    * @Description: 角色菜单列表 
    * @Param: [id, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @GetMapping("/roleMenuList")
    @ApiOperation(value = SystemRoleApi.RoleMenuList.METHOD_TITLE_NAME, notes = SystemRoleApi.RoleMenuList.METHOD_TITLE_NOTE)
    public ResultDTO roleMenuList(@ApiParam(SystemRoleApi.RoleMenuList.METHOD_API_ID) @RequestParam("id") String id,
                                  HttpServletRequest request) {
        return ResultDTO.requstSuccess(systemRoleService.roleMenuList(id));
    }
}
