package com.blockchain.server.eth.dto.wallet;

import com.blockchain.common.base.dto.wallet.GasDTO;
import com.blockchain.common.base.entity.BaseModel;
import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * EthApplication 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Data
public class ConfigWalletParamDto extends BaseModel {
    private Integer id;
    private String moduleType;
    private String paramName;
    private GasDTO paramValue;
    private String paramDescr;
    private Integer status;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}