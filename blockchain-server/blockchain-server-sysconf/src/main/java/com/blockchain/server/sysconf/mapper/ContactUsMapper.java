package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.dto.ContactUsQueryConditionDTO;
import com.blockchain.server.sysconf.entity.ContactUs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ContactUsMapper extends Mapper<ContactUs> {
    ContactUs findContactUsById(@Param("id") String id);

    List<ContactUs> listAll(@Param("showStatus") Integer showStatus, @Param("userLocal") String userLocal);

    Integer deleteContactUs(@Param("id") String id);

    /**
     * 查询联系我们列表
     *
     * @param contactUsQueryConditionDTO 联系我们信息
     * @return 联系我们集合
     */
    List<ContactUs> selectContactUsList(ContactUsQueryConditionDTO contactUsQueryConditionDTO);


}
