package com.blockchain.server.databot.feign;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

@FeignClient("dapp-user-server")
public interface UserFeign {
    //请求路径
    String CONTENT_PATH = "/inner/user";

    /***
     * 查询单个用户信息
     * @param userId
     * @return
     */
    @PostMapping(CONTENT_PATH + "/selectUserInfo")
    ResultDTO<UserBaseInfoDTO> selectUserInfo(@RequestParam("userId") String userId);

    /***
     * 查询多个用户信息
     * 根据id
     *
     * @param userIds
     * @return
     */
    @PostMapping(CONTENT_PATH + "/listUserInfo")
    ResultDTO<Map<String, UserBaseInfoDTO>> listUserInfo(@RequestBody Set<String> userIds);

    /***
     * 根据账户查询用户信息
     * @param userName
     * @return
     */
    @PostMapping(CONTENT_PATH + "/selectUserInfoByUserName")
    ResultDTO<UserBaseInfoDTO> selectUserInfoByUserName(@RequestParam("userName") String userName);
}
