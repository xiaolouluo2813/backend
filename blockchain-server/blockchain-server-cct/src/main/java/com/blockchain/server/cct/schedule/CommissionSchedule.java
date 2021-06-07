package com.blockchain.server.cct.schedule;

import com.blockchain.server.cct.common.constant.CCTConstant;
import com.blockchain.server.cct.service.CommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommissionSchedule implements Runnable {

    @Autowired
    private CommissionService commissionService;

    @Override
    public void run() {
        int pageNum = 1;
        int pageSize = 10;
        issueCommission(pageNum, pageSize);
    }

    /***
     * 发放佣金
     * @param pageNum
     * @param pageSize
     */
    public void issueCommission(int pageNum, int pageSize) {
        //分页查询佣金
        List<String> commissionsIds = commissionService.listByStatus(CCTConstant.STATUS_NO, (pageNum - 1) * pageSize, pageSize);
        //迭代
        for (String commissionId : commissionsIds) {
            //发放佣金
            commissionService.issueCommission(commissionId);
        }
        //还有数据
        if (commissionsIds.size() >= pageSize) {
            //翻页
            pageNum++;
            //递归
            issueCommission(pageNum, pageSize);
        }
    }
}
