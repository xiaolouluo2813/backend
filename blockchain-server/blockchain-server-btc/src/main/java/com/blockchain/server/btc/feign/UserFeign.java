package com.blockchain.server.btc.feign;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * 用户微服务
 *
 * @author huangxl
 * @create 2019-02-28 17:39
 */
@FeignClient("dapp-user-server")
public interface UserFeign {
    /***
     * 查询多个用户信息
     * 根据id
     *
     * @param list
     * @return
     */
    @PostMapping("/inner/user/listUserInfo")
    ResultDTO<Map<String, UserBaseInfoDTO>> userInfos(@RequestBody Set<String> list);

    /***
     * 根据账户查询用户信息
     * @param userName
     * @return
     */
    @PostMapping("/inner/user/selectUserInfoByUserName")
    ResultDTO<UserBaseInfoDTO> selectUserInfoByUserName(@RequestParam("userName") String userName);
}
