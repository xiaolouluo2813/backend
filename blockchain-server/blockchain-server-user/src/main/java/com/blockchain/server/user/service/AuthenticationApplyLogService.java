package com.blockchain.server.user.service;

import com.blockchain.server.user.entity.AuthenticationApplyLog;

public interface AuthenticationApplyLogService {


    Integer insert(AuthenticationApplyLog authenticationApplyLog);

    AuthenticationApplyLog selectRemarkByUserId(String type,String userId);

}
