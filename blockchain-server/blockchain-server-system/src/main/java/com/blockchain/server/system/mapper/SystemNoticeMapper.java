package com.blockchain.server.system.mapper;

import com.blockchain.server.system.entity.SystemNotice;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-04 17:34
 **/
@Repository
public interface SystemNoticeMapper extends Mapper<SystemNotice> {

}