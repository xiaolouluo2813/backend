package com.blockchain.server.system.dto;

import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 * @author: Liusd
 * @create: 2019-03-04 17:43
 **/
@Data
public class SystemRoleResultDto implements Serializable {

    /**
     * id
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 标识
     */
    private String code;
    /**
     * 序号
     */
    private Integer ranking;
    /**
     * 状态 可用(Y)、禁用(N)
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createTime;
}
