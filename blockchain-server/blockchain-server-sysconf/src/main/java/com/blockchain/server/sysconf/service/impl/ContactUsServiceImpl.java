package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.common.constant.UserConstant;
import com.blockchain.server.sysconf.dto.ContactUsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.ContactUs;
import com.blockchain.server.sysconf.mapper.ContactUsMapper;
import com.blockchain.server.sysconf.service.ContactUsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ContactUsServiceImpl implements ContactUsService {

    @Autowired
    private ContactUsMapper contactUsMapper;

    @Override
    @Transactional
    public Integer saveContactUs(String contactName, String contactValue, String userLocal, Integer showStatus, int rank) {
        ContactUs contactUs = new ContactUs();
        contactUs.setId(UUID.randomUUID().toString());
        contactUs.setContactName(contactName);
        contactUs.setContactValue(contactValue);
        contactUs.setRank(rank);
        contactUs.setUserLocal(userLocal);
        contactUs.setShowStatus(showStatus);
        contactUs.setCreateTime(new Date());
        contactUs.setModifyTime(new Date());
        return contactUsMapper.insert(contactUs);
    }

    @Override
    @Transactional
    public Integer updateContactUs(String id, String contactName, String contactValue, String userLocal, Integer showStatus, int rank) {
        ContactUs contactUs = new ContactUs();
        contactUs.setId(id);
        contactUs.setContactName(contactName);
        contactUs.setContactValue(contactValue);
        contactUs.setRank(rank);
        contactUs.setUserLocal(userLocal);
        contactUs.setShowStatus(showStatus);
        contactUs.setModifyTime(new Date());
        return contactUsMapper.updateByPrimaryKeySelective(contactUs);
    }

    @Override
    public ContactUs findContactUsById(String id) {
        return contactUsMapper.findContactUsById(id);
    }

    @Override
    public List<ContactUs> listAll(Integer showStatus,String userLocal) {
        //查询默认为中文语种
        if(StringUtils.isEmpty(userLocal)){
            userLocal = UserConstant.USER_LOCAL_CHINA;
        }
        return contactUsMapper.listAll(showStatus,userLocal);
    }

    @Override
    @Transactional
    public Integer deleteContactUs(String id) {
        return contactUsMapper.deleteContactUs(id);
    }

    /**
     * 查询联系我们列表
     *
     * @param contactUsQueryConditionDTO 联系我们信息
     * @return 联系我们集合
     */
    @Override
    public List<ContactUs> selectContactUsList(ContactUsQueryConditionDTO contactUsQueryConditionDTO)
    {
//        contactUsQueryConditionDTO.setShowStatus(ContactUsConstant.STATUS_SHOW);
        return contactUsMapper.selectContactUsList(contactUsQueryConditionDTO);
    }
}
