package com.blockchain.server.user.controller;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.base.controller.BaseController;
import com.blockchain.server.user.common.constant.UserAuthenticationConstant;
import com.blockchain.server.user.controller.api.UserAuthenticationApi;
import com.blockchain.server.user.dto.AuthenticationDto;
import com.blockchain.server.user.dto.UserListDto;
import com.blockchain.server.user.entity.AuthenticationApply;
import com.blockchain.server.user.entity.HighAuthenticationApply;
import com.blockchain.server.user.entity.UserAuthentication;
import com.blockchain.server.user.service.AuthenticationApplyService;
import com.blockchain.server.user.service.HighAuthenticationApplyService;
import com.blockchain.server.user.service.UserAuthenticationService;
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
 * @date 2019/3/7 10:25
 * @user WIN10
 */
@RestController
@Api(UserAuthenticationApi.USER_AUTHENTICATION_API)
@RequestMapping("/userAuthentication")
public class UserAuthenticationController extends BaseController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @Autowired
    private AuthenticationApplyService authenticationApplyService;
    @Autowired
    private HighAuthenticationApplyService highAuthenticationApplyService;

    /** 
    * @Description: 审核记录列表
    * @Param: [params] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/7/16 
    */ 
    @ApiOperation(value = UserAuthenticationApi.SelectUserAuthenticationInfo.METHOD_API_NAME, notes = UserAuthenticationApi.SelectUserAuthenticationInfo.METHOD_API_NOTE)
    @PostMapping("/selectAuthenticationList")
    public ResultDTO selectAuthenticationList( @RequestParam(value = "pageNum", defaultValue = BaseConstant.PAGE_DEFAULT_INDEX, required = false) Integer pageNum,
             @RequestParam(value = "pageSize", defaultValue = BaseConstant.PAGE_DEFAULT_SIZE, required = false) Integer pageSize,AuthenticationDto params) {
        PageHelper.startPage(pageNum, pageSize);
        List<AuthenticationDto> authenticationList ;
        if (params==null || params.getAuth().equals(UserAuthenticationConstant.LOW) ){
            authenticationList = authenticationApplyService.selectAuthenticationList(params);
        }else {
            authenticationList = highAuthenticationApplyService.selectAuthenticationList(params);
        }
        return generatePage(authenticationList);
    }
    /**
     * 查询用户高级未认证原因
     * @param userId
     * @return
     */
    // @RequiresPermissions("app:userAuthentication:selectLowAuth")
    @ApiOperation(value = UserAuthenticationApi.SelectLowAuth.METHOD_API_NAME, notes = UserAuthenticationApi.SelectLowAuth.METHOD_API_NOTE)
    @PostMapping("/selectHighRemark")
    public ResultDTO selectHighRemark(@ApiParam(UserAuthenticationApi.SelectHighAuth.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        return ResultDTO.requstSuccess(userAuthenticationService.selectHighRemarkByUserId(userId));
    }


    /**
     * 查询初级未认证原因
     * @param userId
     * @return
     */
    // @RequiresPermissions("app:userAuthentication:selectLowAuth")
    @ApiOperation(value = UserAuthenticationApi.SelectLowAuth.METHOD_API_NAME, notes = UserAuthenticationApi.SelectLowAuth.METHOD_API_NOTE)
    @PostMapping("/selectLowRemark")
    public ResultDTO selectLowRemark(@ApiParam(UserAuthenticationApi.SelectLowAuth.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        return ResultDTO.requstSuccess(userAuthenticationService.selectLowRemarkByUserId(userId));
    }

    /**
     * 查询用户初级审核申请
     * @param userId
     * @return
     */
    // @RequiresPermissions("app:userAuthentication:selectLowAuth")
    @ApiOperation(value = UserAuthenticationApi.SelectLowAuth.METHOD_API_NAME, notes = UserAuthenticationApi.SelectLowAuth.METHOD_API_NOTE)
    @PostMapping("/selectLowAuth")
    public ResultDTO selectLowAuth(@ApiParam(UserAuthenticationApi.SelectLowAuth.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        AuthenticationApply authenticationApply = userAuthenticationService.selectLowAuthByUserId(userId);
        return ResultDTO.requstSuccess(authenticationApply);
    }
    /**
     * 查询用户高级审核申请
     * @param userId
     * @return
     */
    // @RequiresPermissions("app:userAuthentication:selectHighAuth")
    @ApiOperation(value = UserAuthenticationApi.SelectHighAuth.METHOD_API_NAME, notes = UserAuthenticationApi.SelectHighAuth.METHOD_API_NOTE)
    @PostMapping("/selectHighAuth")
    public ResultDTO selectHighAuth(@ApiParam(UserAuthenticationApi.SelectHighAuth.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        HighAuthenticationApply highAuthenticationApply = userAuthenticationService.selectHighAuthByUserId(userId);
        return ResultDTO.requstSuccess(highAuthenticationApply);
    }

    /**
     * 通过用户初级审核申请
     * @param userId
     * @return
     */
    // @RequiresPermissions("app:userAuthentication:passLowAuth")
    @ApiOperation(value = UserAuthenticationApi.PassLowAuth.METHOD_API_NAME, notes = UserAuthenticationApi.PassLowAuth.METHOD_API_NOTE)
    @PostMapping("/passLowAuth")
    public ResultDTO passLowAuth(@ApiParam(UserAuthenticationApi.PassLowAuth.METHOD_API_USER_ID) @RequestParam("userId") String userId,
                                   @ApiParam(UserAuthenticationApi.PassLowAuth.METHOD_API_APPLY_ID) @RequestParam("applyId") String applyId) {
        userAuthenticationService.handleSubmitLowAuthentication(null,userId, applyId, UserAuthenticationConstant.SUCCESS);
        return ResultDTO.requstSuccess();
    }

    /**
     * 驳回用户初级审核申请
     * @param userId
     * @return
     */
//    @RequiresPermissions("app:userAuthentication:rejectLowAuth")
    @ApiOperation(value = UserAuthenticationApi.RejectLowAuth.METHOD_API_NAME, notes = UserAuthenticationApi.RejectLowAuth.METHOD_API_NOTE)
    @PostMapping("/rejectLowAuth")
    public ResultDTO rejectLowAuth(@ApiParam(UserAuthenticationApi.RejectLowAuth.METHOD_API_REMARK) @RequestParam("remark") String remark,
            @ApiParam(UserAuthenticationApi.RejectLowAuth.METHOD_API_USER_ID) @RequestParam("userId") String userId,
                                   @ApiParam(UserAuthenticationApi.RejectLowAuth.METHOD_API_APPLY_ID) @RequestParam("applyId") String applyId) {
        userAuthenticationService.handleSubmitLowAuthentication(remark,userId, applyId, UserAuthenticationConstant.FAILED);
        return ResultDTO.requstSuccess();
    }

    /**
     * 通过用户高级审核申请
     * @param userId
     * @return
     */
//    @RequiresPermissions("app:userAuthentication:passHighAuth")
    @ApiOperation(value = UserAuthenticationApi.PassHighAuth.METHOD_API_NAME, notes = UserAuthenticationApi.PassHighAuth.METHOD_API_NOTE)
    @PostMapping("/passHighAuth")
    public ResultDTO passHighAuth(@ApiParam(UserAuthenticationApi.PassHighAuth.METHOD_API_USER_ID) @RequestParam("userId") String userId,
                                   @ApiParam(UserAuthenticationApi.PassHighAuth.METHOD_API_APPLY_ID) @RequestParam("applyId") String applyId) {
        userAuthenticationService.handleSubmitHighAuthentication(null,userId, applyId, UserAuthenticationConstant.SUCCESS);
        return ResultDTO.requstSuccess();
    }

    /**
     * 驳回用户高级审核申请
     * @param userId
     * @return
     */
//    @RequiresPermissions("app:userAuthentication:rejectHighAuth")
    @ApiOperation(value = UserAuthenticationApi.RejectHighAuth.METHOD_API_NAME, notes = UserAuthenticationApi.RejectHighAuth.METHOD_API_NOTE)
    @PostMapping("/rejectHighAuth")
    public ResultDTO rejectHighAuth(@ApiParam(UserAuthenticationApi.RejectHighAuth.METHOD_API_REMARK) @RequestParam("remark") String remark,
            @ApiParam(UserAuthenticationApi.RejectHighAuth.METHOD_API_USER_ID) @RequestParam("userId") String userId,
                                   @ApiParam(UserAuthenticationApi.RejectHighAuth.METHOD_API_APPLY_ID) @RequestParam("applyId") String applyId) {
        userAuthenticationService.handleSubmitHighAuthentication(remark,userId, applyId, UserAuthenticationConstant.FAILED);
        return ResultDTO.requstSuccess();
    }

    /**
     * 查询用户认证信息
     * @param userId
     * @return
     */
    // @RequiresPermissions("app:userAuthentication:selectUserAuthentication")
    @ApiOperation(value = UserAuthenticationApi.SelectUserAuthentication.METHOD_API_NAME, notes = UserAuthenticationApi.SelectUserAuthentication.METHOD_API_NOTE)
    @PostMapping("/selectUserAuthentication")
    public ResultDTO selectUserAuthentication(@ApiParam(UserAuthenticationApi.SelectUserAuthentication.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        UserAuthentication userAuthentication = userAuthenticationService.selectUserAuthenticationByUserId(userId);
        return ResultDTO.requstSuccess(userAuthentication);
    }

    /**
     * 查询用户基本认证信息
     * @param userId
     * @return
     */
    @ApiOperation(value = UserAuthenticationApi.SelectUserAuthenticationInfo.METHOD_API_NAME, notes = UserAuthenticationApi.SelectUserAuthenticationInfo.METHOD_API_NOTE)
    @PostMapping("/selectUserAuthenticationInfo")
    public ResultDTO selectUserAuthenticationInfo(@ApiParam(UserAuthenticationApi.SelectUserAuthenticationInfo.METHOD_API_USER_ID) @RequestParam("userId") String userId) {
        UserListDto userListDto = userAuthenticationService.selectUserAuthenticationInfoByUserId(userId);
        return ResultDTO.requstSuccess(userListDto);
    }

}
