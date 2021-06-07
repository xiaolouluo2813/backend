package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.marketuser.ListMarketUserResultDTO;
import com.blockchain.server.otc.entity.MarketUser;

import java.math.BigDecimal;
import java.util.List;

public interface MarketUserService {

    /***
     * 市商用户列表
     * @param userName
     * @param status
     * @return
     */
    List<ListMarketUserResultDTO> list(String userName, String status);

    /***
     * 根据用户id查询市商用户
     * @param userId
     * @return
     */
    MarketUser getByUserId(String userId);

    /***
     * 根据账户新建市商 - 外部接口调用
     * @param userName
     * @param amount
     * @param coin
     */
    void insertMarketUserByUserName(String userName, BigDecimal amount, String coin, String ipAddress, String sysUserId);

    /***
     * 根据账户新建市商 - 通过申请时调用
     * @param userId
     * @param amount
     * @param coin
     * @param ipAddress
     * @param sysUserId
     */
    void insertMarketUserByUserId(String userId, BigDecimal amount, String coin, String ipAddress, String sysUserId);

    /***
     * 根据市商记录id取消市商 - 外部接口调用
     * @param id
     * @param ipAddress
     * @param sysUserId
     */
    void cancelMarketUserById(String id, String ipAddress, String sysUserId);

    /***
     * 根据用户id取消市商 - 通过取消申请时调用
     * @param userId
     * @param ipAddress
     * @param sysUserId
     */
    void cancelMarketUserByUserId(String userId, String ipAddress, String sysUserId);

    /***
     * 让已取消市商的用户变成市商
     * @param id
     * @param ipAddress
     * @param sysUserId
     */
    void becomeMarketUser(String id, BigDecimal amount, String coin, String ipAddress, String sysUserId);

    /***
     * 更新市商用户
     * @param marketUser
     * @return
     */
    int updateByPrimaryKeySelective(MarketUser marketUser);
}
