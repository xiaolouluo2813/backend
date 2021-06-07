package com.blockchain.server.otc.dto.appealdetail;

import com.blockchain.server.otc.dto.appealimg.ListAppealImgResultDTO;
import com.blockchain.server.otc.entity.AppealImg;
import lombok.Data;

import java.util.List;

@Data
public class ListAppealDetailResultDTO {
    private String id;
    private String appealId;
    private String userId;
    private String userName;
    private String realName;
    private String nickName;
    private String appealRole;
    private String remark;
    private List<ListAppealImgResultDTO> appealImgs;
    private java.util.Date createTime;
}
