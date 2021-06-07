package com.blockchain.server.user.service;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.user.dto.BlackAndWhiteDto;
import com.blockchain.server.user.entity.UserList;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/5 11:45
 * @user WIN10
 */
public interface UserListService {

    /**
     * 根据黑名单类型查询黑名单列表
     * @param listType
     * @param type
     * @return
     */
    List<BlackAndWhiteDto> listBlacklistWhitelist(String listType, String type);

    /**
     * 添加黑白名单
     * @param userId
     * @param listType
     * @param type
     * @return
     */
    Integer addBlacklistWhitelist(String userId, String listType, String type);

    /**
     * 根据id删除黑白名单
     * @param id
     * @return
     */
    Integer deleteUserList(String id);

    /**
     * 根据用户id查询所有黑白名单
     * @param userId
     * @return
     */
    List<UserList> listUserListByUserId(String userId);

    /**
     * 根据用户id以及名单类型查询用户黑白名单信息
     * @param userId
     * @param listType
     * @return
     */
    List<UserList> listUserListByUserId(String userId, String listType);

    /**
     * 根据用户id和类型判断用户是否存在该黑白名单
     * @param userId
     * @param listType
     * @param type
     * @return
     */
    Integer existUserList(String userId, String listType, String type);
}
