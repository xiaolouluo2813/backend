package com.blockchain.server.sysconf.service.impl;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.server.sysconf.entity.Agreement;
import com.blockchain.server.sysconf.mapper.AgreementMapper;
import com.blockchain.server.sysconf.service.AgreementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class AgreementServiceImpl implements AgreementService {

    @Autowired
    private AgreementMapper agreementMapper;

    @Override
    public List<Agreement> list(String type) {
        Agreement agreement = new Agreement();
        agreement.setType(type);
        return agreementMapper.select(agreement);
    }

    @Override
    @Transactional
    public int insert(String type,String textContent, String languages) {
        Agreement agreement = new Agreement();
        agreement.setId(UUID.randomUUID().toString());
        agreement.setTextContent(textContent);
        agreement.setLanguages(languages);
        agreement.setType(type);
        agreement.setCreateTime(new Date());
        agreement.setModifyTime(new Date());
        return agreementMapper.insert(agreement);
    }

    @Override
    @Transactional
    public int update(String textContent, String id) {
        Agreement agreement = agreementMapper.selectByPrimaryKey(id);
        if (agreement!=null){
            agreement.setTextContent(textContent);
            agreement.setModifyTime(new Date());
            return agreementMapper.updateByPrimaryKey(agreement);
        }
        return 0;
    }

    @Override
    public int remove(String id) {
        return agreementMapper.deleteByPrimaryKey(id);
    }
}
