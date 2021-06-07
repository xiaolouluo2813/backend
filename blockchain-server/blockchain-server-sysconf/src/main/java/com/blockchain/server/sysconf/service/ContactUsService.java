package com.blockchain.server.sysconf.service;



import com.blockchain.server.sysconf.dto.ContactUsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.ContactUs;

import java.util.List;

public interface ContactUsService {
    /**
     * 保存联系我们信息
     *
     * @param contactName
     * @param contactValue
     * @param showStatus
     * @param rank
     */
    Integer saveContactUs(String contactName, String contactValue, String userLocal, Integer showStatus, int rank);

    /**
     * 更新联系我们信息
     *
     * @param id
     * @param contactName
     * @param contactValue
     * @param showStatus
     */
    Integer updateContactUs(String id, String contactName, String contactValue, String userLocal, Integer showStatus, int rank);

    /**
     * 根据id查询联系我们信息
     *
     * @param id
     */
    ContactUs findContactUsById(String id);

    /**
     * 查询联系我们信息列表
     *
     * @return
     */
    List<ContactUs> listAll(Integer showStatus, String userLocal);

    /**
     * 删除联系我们信息
     *
     * @param id
     */
    Integer deleteContactUs(String id);

    /**
     * 查询联系我们列表
     *
     * @param contactUsQueryConditionDTO 联系我们信息
     * @return 联系我们集合
     */
    List<ContactUs> selectContactUsList(ContactUsQueryConditionDTO contactUsQueryConditionDTO);


}
