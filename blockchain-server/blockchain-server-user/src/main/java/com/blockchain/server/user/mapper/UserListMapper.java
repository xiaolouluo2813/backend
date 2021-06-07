package com.blockchain.server.user.mapper;

import com.blockchain.server.user.dto.BlackAndWhiteDto;
import com.blockchain.server.user.entity.UserList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/5 11:46
 * @user WIN10
 */
@Repository
public interface UserListMapper extends Mapper<UserList> {


    /**
     * 根据黑名单类型查询黑名单列表
     * @param listType
     * @param type
     * @return
     */
    List<BlackAndWhiteDto> listBlacklistWhitelist(@Param("listType") String listType, @Param("type") String type);

    /**
     * 统计用户存在名单中
     * @param userId
     * @param listType
     * @param type
     * @return
     */
    Integer countUserList(@Param("userId") String userId, @Param("listType") String listType, @Param("type") String type);

    /**
     * 根据用户id以及名单类型查询用户黑白名单信息
     * @param userId
     * @param listType
     * @return
     */
    List<UserList> listUserListByUserId(@Param("userId") String userId, @Param("listType") String listType);
}
