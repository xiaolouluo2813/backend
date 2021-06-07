package com.blockchain.server.eth.common.util;

import com.github.pagehelper.PageHelper;

/**
 * 页数控制工具类
 */
public class PageUtil {
    private static final int PAGE_NUM = 0;   // 默认查询第几页
    private static final int PAGE_SIZE = 10;  // 默认查询记录数

    /**
     * 查询分页处理
     *
     * @param pageNum  当前页数
     * @param pageSize 页数展示条数
     */
    public static void startPage(Integer pageNum, Integer pageSize) {
        if (null == pageNum) pageNum = PAGE_NUM;
        if (null == pageSize) pageSize = PAGE_SIZE;
        PageHelper.startPage(pageNum, pageSize);
    }
}
