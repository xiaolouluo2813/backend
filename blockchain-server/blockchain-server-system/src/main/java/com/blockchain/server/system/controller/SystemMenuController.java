package com.blockchain.server.system.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.util.SerializableUtil;
import com.blockchain.server.base.security.RequiresPermissions;
import com.blockchain.server.system.controller.api.SystemMenuApi;
import com.blockchain.server.system.dto.SystemMenuAddDto;
import com.blockchain.server.system.dto.SystemMenuResultDto;
import com.blockchain.server.system.service.SystemMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-05 17:00
 **/
@RestController
@RequestMapping("/systemMenu")
@Api(SystemMenuApi.CONTROLLER_API)
public class SystemMenuController {

    private static final Logger LOG = LoggerFactory.getLogger(SystemMenuController.class);

    @Autowired
    private SystemMenuService systemMenuService;

    /** 
    * @Description: 新增菜单接口 已弃用
    * @Param: [systemMenuAddDto, request] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @PostMapping("/insertSystemMenu")
    @ApiOperation(value = SystemMenuApi.InsertSystemMenu.METHOD_TITLE_NAME, notes = SystemMenuApi.InsertSystemMenu.METHOD_TITLE_NOTE)
    public ResultDTO insertSystemMenu(@ApiParam(SystemMenuApi.InsertSystemMenu.METHOD_API_SYSTEMMENUADDDTO) @RequestBody SystemMenuAddDto systemMenuAddDto, HttpServletRequest request) {
        systemMenuService.insert(systemMenuAddDto);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 校验菜单标识接口 已弃用 
    * @Param: [code] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @GetMapping("/codeCheck")
    @ApiOperation(value = SystemMenuApi.CodeCheck.METHOD_TITLE_NAME, notes = SystemMenuApi.CodeCheck.METHOD_TITLE_NOTE)
    public ResultDTO codeCheck(@ApiParam(SystemMenuApi.CodeCheck.METHOD_API_CODE) @RequestParam("code") String code) {
        systemMenuService.codeCheck(code);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 菜单树状结构
    * @Param: [] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @GetMapping("/systemMenuList")
    @ApiOperation(value = SystemMenuApi.SysteMenuList.METHOD_TITLE_NAME, notes = SystemMenuApi.SysteMenuList.METHOD_TITLE_NOTE)
    public ResultDTO systemMenuList() {
        List<SystemMenuResultDto> list = systemMenuService.systemMenuList("Y");
        return ResultDTO.requstSuccess(list);
    }

    /** 
    * @Description: 用户菜单树状结构 已弃用
    * @Param: [userId] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @GetMapping("/userMenuList")
    @ApiOperation(value = SystemMenuApi.UserMenuList.METHOD_TITLE_NAME, notes = SystemMenuApi.UserMenuList.METHOD_TITLE_NOTE)
    public ResultDTO userMenuList(@ApiParam(SystemMenuApi.UserMenuList.METHOD_API_USERID) @RequestParam("userId") String userId) {
        List<SystemMenuResultDto> list = systemMenuService.userMenuList("Y",userId);
        return ResultDTO.requstSuccess(list);
    }
}
