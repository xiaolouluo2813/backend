package com.blockchain.server.sysconf.service;



import com.blockchain.server.sysconf.entity.Agreement;

import java.util.List;


public interface AgreementService {

    List<Agreement> list(String type);

    int insert(String type,String textContent, String languages);

    int update(String textContent, String id);

    int remove(String id);
}
