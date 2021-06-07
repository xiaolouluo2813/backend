package com.blockchain.server.otc.dto.appealimg;

import lombok.Data;

@Data
public class ListAppealImgResultDTO {
    private String id;
    private String appealDetailId;
    private String appealImgUrl;
    private java.util.Date createTime;
}
