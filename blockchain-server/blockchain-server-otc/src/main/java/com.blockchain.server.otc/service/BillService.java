package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.bill.ListBillResultDTO;

import java.math.BigDecimal;
import java.util.List;

public interface BillService {

    /***
     * 查询用户对账列表
     * @param userName
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListBillResultDTO> listBill(String userName, String beginTime, String endTime);

    /***
     * 新增资金对账记录
     * @param userId
     * @param recordNumber
     * @param freeBalance
     * @param freezeBalance
     * @param billType
     * @param coinName
     * @return
     */
    int insertBill(String userId, String recordNumber, BigDecimal freeBalance, BigDecimal freezeBalance, String billType, String coinName);
}
