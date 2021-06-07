package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.user.common.constant.UserListConstant;
import com.blockchain.server.user.dto.BlackAndWhiteDto;
import com.blockchain.server.user.entity.UserList;
import com.blockchain.server.user.mapper.UserListMapper;
import com.blockchain.server.user.service.UserListService;
import com.blockchain.server.user.service.UserMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Harvey
 * @date 2019/3/5 11:45
 * @user WIN10
 */
@Service
public class UserListServiceImpl implements UserListService {

    @Autowired
    private UserListMapper userListMapper;
    @Autowired
    private UserMainService userMainService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据黑名单类型查询黑名单列表
     *
     * @param listType
     * @param type
     * @return
     */
    @Override
    public List<BlackAndWhiteDto> listBlacklistWhitelist(String listType, String type) {
        return userListMapper.listBlacklistWhitelist(listType, type);
    }

    /**
     * 添加黑白名单
     *
     * @param userId
     * @param listType
     * @param type
     * @return
     */
    @Override
    public Integer addBlacklistWhitelist(String userId, String listType, String type) {
        Integer row = userListMapper.countUserList(userId, listType, type);
        if (row != 0) return 0;

        if (listType.equals(UserListConstant.BLACKLIST_LIST_TYPE) && type.equals(UserListConstant.BLACKLIST_BAN_LOGIN)) {
            UserBaseInfoDTO user = userMainService.selectUserInfo(userId);
            String phone = user.getMobilePhone();
            String REDIS_TOKEN_KEY = "user:token:" + phone;
            String REDIS_TOKEN_PC_KEY = "user:token:pc" + phone;
            redisTemplate.delete(REDIS_TOKEN_KEY);
            redisTemplate.delete(REDIS_TOKEN_PC_KEY);
        }

        UserList userList = new UserList();
        userList.setId(UUID.randomUUID().toString());
        userList.setUserId(userId);
        userList.setListType(listType);
        userList.setType(type);
        userList.setCreateTime(new Date());
        return userListMapper.insertSelective(userList);
    }

    /**
     * 根据id删除黑白名单
     *
     * @param id
     * @return
     */
    @Override
    public Integer deleteUserList(String id) {
        return userListMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据用户id查询所有黑白名单
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserList> listUserListByUserId(String userId) {
        UserList userList = new UserList();
        userList.setUserId(userId);
        return userListMapper.select(userList);
    }

    /**
     * 根据用户id以及名单类型查询用户黑白名单信息
     *
     * @param userId
     * @param listType
     * @return
     */
    @Override
    public List<UserList> listUserListByUserId(String userId, String listType) {
        return userListMapper.listUserListByUserId(userId, listType);
    }

    /**
     * 根据用户id和类型判断用户是否存在该黑白名单
     *
     * @param userId
     * @param listType
     * @param type
     * @return
     */
    @Override
    public Integer existUserList(String userId, String listType, String type) {
        return userListMapper.countUserList(userId, listType, type);
    }
}
