package com.blockchain.server.cct.service;

import com.blockchain.server.cct.dto.commission.ListCommissionResultDTO;
import com.blockchain.server.cct.entity.Commission;

import java.util.List;

public interface CommissionService {

    /***
     * 查询佣金列表
     * @param userName
     * @param puserName
     * @param coinName
     * @param status
     * @return
     */
    List<ListCommissionResultDTO> list(String userName, String puserName, String coinName, String status);

    /***
     * 根据状态查询佣金记录id
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<String> listByStatus(String status, Integer pageNum, Integer pageSize);

    /***
     * 发放佣金
     * @param id
     * @return
     */
    int issueCommission(String id);

    /***
     * 排他锁查询
     * @param id
     * @return
     */
    Commission selectByIdForUpdate(String id);
}
