package com.blockchain.server.system.controller;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.dto.TokenDTO;
import com.blockchain.common.base.util.MD5Utils;
import com.blockchain.common.base.util.RSACoderUtils;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.system.common.constant.LoginConstant;
import com.blockchain.server.system.common.constant.SmsCountConstant;
import com.blockchain.server.system.common.enums.SmsCountEnum;
import com.blockchain.server.system.common.enums.SystemResultEnums;
import com.blockchain.server.system.common.exception.SystemException;
import com.blockchain.server.system.common.util.RedisPrivateUtil;
import com.blockchain.server.system.common.util.SendSmsgCode;
import com.blockchain.server.system.common.util.SmsCodeUtils;
import com.blockchain.server.system.controller.api.LoginApi;
import com.blockchain.server.system.dto.LoginDto;
import com.blockchain.server.system.dto.SystemMenuResultDto;
import com.blockchain.server.system.dto.UserLoginBaseDTO;
import com.blockchain.server.system.dto.UserMenuDto;
import com.blockchain.server.system.entity.SystemUser;
import com.blockchain.server.system.service.SmsCountService;
import com.blockchain.server.system.service.SystemMenuService;
import com.blockchain.server.system.service.SystemUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangxl
 * @create 2019-02-21 21:06
 */
@RestController
@RequestMapping("/login")
@Api(LoginApi.CONTROLLER_API)
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private SmsCountService smsCountService;
    @Autowired
    private SmsCodeUtils smsCodeUtils;
    @Autowired
    private SendSmsgCode sendSmsgCode;

    /**
    * @Description: 发送短信 
    * @Param: [username, password, phone] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/6/20 
    */ 
    @GetMapping("/sendLoginCode")
    @ApiOperation(value = LoginApi.SendLoginSmsCode.METHOD_NAME,
            notes = LoginApi.SendLoginSmsCode.METHOD_NOTE)
    public ResultDTO sendLoginSmsCode(@ApiParam(LoginApi.SendLoginSmsCode.METHOD_API_USERNAME) @RequestParam("username") String username,
            @ApiParam(LoginApi.SendLoginSmsCode.METHOD_API_PHONE) @RequestParam(name = "phone") String phone) {
        //短信验证码关闭时，不验证手机号。方便测试。
        if(!sendSmsgCode.isClosed()){
            SystemUser systemUser = systemUserService.selectByUsernameAndPhone(username, phone);
            if (systemUser == null) {
                throw new SystemException(SystemResultEnums.PHONE_ERROR);
            }
        }
        smsCountService.handleInsertSmsCode(phone, SmsCountConstant.DEFAULT_CODE, SmsCountEnum.SMS_COUNT_LOGIN);
        return ResultDTO.requstSuccess();
    }
    /**
     * @Description: 获取公钥
     * @Param: [username]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/6/3
     */
    @ApiOperation(value = LoginApi.GetPublicKey.METHOD_API_NAME, notes = LoginApi.GetPublicKey.METHOD_API_NOTE)
    @GetMapping("/getPublicKey")
    public ResultDTO getPublicKey(@ApiParam(LoginApi.GetPublicKey.METHOD_API_USERNAME) @RequestParam("username") String username) {
        return ResultDTO.requstSuccess(systemUserService.getPublicKey(username));
    }

    /**
     * @Description: 登陆
     * @Param: [username, password]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/3/15
     */
    @GetMapping("/login")
    @ApiOperation(value = LoginApi.Login.METHOD_TITLE_NAME, notes = LoginApi.Login.METHOD_TITLE_NOTE)
    public ResultDTO login(@ApiParam(LoginApi.Login.METHOD_API_USERNAME) @RequestParam("username") String username,
                           @ApiParam(LoginApi.Login.METHOD_API_PASSWORD) @RequestParam("password") String password,
                           @ApiParam(LoginApi.Login.METHOD_API_PHONE) @RequestParam(name = "phone") String phone,
                           @ApiParam(LoginApi.Login.METHOD_API_CODE) @RequestParam(name = "code") String code) {
        smsCodeUtils.validateVerifyCode(code, phone, SmsCountEnum.SMS_COUNT_LOGIN);
        String newPassword = getPassword(username, password);
        smsCodeUtils.removeKey(phone, SmsCountEnum.SMS_COUNT_LOGIN);
        redisTemplate.delete(RedisPrivateUtil.PRIVATE_KEY + LoginConstant.getLoginKey(username));
        return ResultDTO.requstSuccess(afterLogin(systemUserService.login(username, MD5Utils.MD5(newPassword))));
    }

    /** 
    * @Description: 用户退出接口 
    * @Param: [] 
    * @return: com.blockchain.common.base.dto.ResultDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    @GetMapping("/exit")
    @ApiOperation(value = LoginApi.Exit.METHOD_TITLE_NAME, notes = LoginApi.Exit.METHOD_TITLE_NOTE)
    public ResultDTO exit() {
        SecurityUtils.removeUser(redisTemplate);
        return ResultDTO.requstSuccess();
    }

    /** 
    * @Description: 登陆封装返回对象接口 
    * @Param: [loginDto] 
    * @return: com.blockchain.server.system.dto.UserLoginBaseDTO 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    private UserLoginBaseDTO afterLogin(LoginDto loginDto){
        long time= System.currentTimeMillis();
        //封装菜单列表
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (UserMenuDto userMenuDto : loginDto.getMenus()){
            list.add(new SimpleGrantedAuthority(userMenuDto.getCode()));
        }
        //封装用户对象
        SessionUserDTO user = new SessionUserDTO();
        user.setId(loginDto.getUser().getId());
        user.setUsername(loginDto.getUser().getAccount());
        user.setTimestamp(time);
        user.setAuthorities(list);
        SecurityUtils.setUser(user,redisTemplate);
        //封装token
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccount(loginDto.getUser().getAccount());
        tokenDTO.setTimestamp(time);
        //封装登陆放回DTO
        UserLoginBaseDTO userLoginBaseDTO = new UserLoginBaseDTO();
        userLoginBaseDTO.setAccount(loginDto.getUser().getAccount());
        userLoginBaseDTO.setToken(RSACoderUtils.encryptToken(tokenDTO));
        userLoginBaseDTO.setUserId(loginDto.getUser().getId());
        userLoginBaseDTO.setUsername(loginDto.getUser().getUsername());
        userLoginBaseDTO.setMenuList(loginDto.getMenus());
        return userLoginBaseDTO;
    }


    /**
     * 获取解密后的密码
     *
     * @param username 用户名
     * @param password   加密后的密码
     * @return
     */
    private String getPassword(String username, String password) {
        String key = RedisPrivateUtil.getPrivateKey(LoginConstant.getLoginKey(username), redisTemplate);    // 获取私钥
        String _pass = RSACoderUtils.decryptPassword(password, key);    // 获取解密后的密码
        return _pass;
    }
}
