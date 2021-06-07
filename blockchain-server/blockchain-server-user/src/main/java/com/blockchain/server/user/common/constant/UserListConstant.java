package com.blockchain.server.user.common.constant;

/**
 * @author Harvey
 * @date 2019/3/8 14:17
 * @user WIN10
 */
public class UserListConstant {
    public static final String BLACKLIST_LIST_TYPE = "BLACK";
    public static final String WHITELIST_LIST_TYPE = "WHITE";
    // 黑名单
    // 禁止登录
    public static final String BLACKLIST_BAN_LOGIN = "BAN_LOGIN";
    // 禁止提现
    public static final String BLACKLIST_BAN_TX = "BAN_TX";
    // 白名单
    // 提现免手续费
    public static final String WHITELIST_FREE_TX = "FREE_TX";
    // 交易免手续费
    public static final String WHITELIST_FREE_TRANSACTION = "FREE_TRANSACTION";
    // 发布广告
    public static final String WHITELIST_FREE_PUSH_AD = "FREE_PUSH_AD";

    public static final String SORT_ASC = "A";

    public static final String SORT_ASC_SQL = "asc";
}
