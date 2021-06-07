package com.blockchain.server.sysconf.service;

import com.blockchain.server.sysconf.entity.SystemNotice;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:54
 **/
public interface SystemNoticeService {

    int updateStatus(String status, String id);

    int deleteNotice(String id);

    List<SystemNotice> systemNoticeList(String status);

    int insert(String title, String details, String jumpUrl, String status, Integer rank, String languages);

    int update(String title, String details, String jumpUrl, Integer rank, String id, String languages);
}
