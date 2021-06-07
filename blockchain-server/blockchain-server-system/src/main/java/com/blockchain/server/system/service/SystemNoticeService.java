package com.blockchain.server.system.service;

import com.blockchain.server.system.entity.SystemNotice;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:54
 **/
public interface SystemNoticeService {
    int insert(String details, String jumpUrl, String status, Integer rank);

    int update(String details, String jumpUrl, Integer rank, String id);

    int updateStatus(String status, String id);

    int deleteNotice(String id);

    List<SystemNotice> systemNoticeList(String status);
}
