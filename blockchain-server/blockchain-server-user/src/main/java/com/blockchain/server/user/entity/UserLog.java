package com.blockchain.server.user.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * UserLoginLog 数据传输类
 * @date 2019-02-21 13:37:18
 * @version 1.0
 */
@Table(name = "pc_u_user_log")
@Data
public class UserLog extends BaseModel {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "ip_address")
	private String ipAddress;
	@Column(name = "sys_user_id")
	private String sysUserId;
	@Column(name = "content")
	private String content;
	@Column(name = "create_time")
	private Date createTime;

}