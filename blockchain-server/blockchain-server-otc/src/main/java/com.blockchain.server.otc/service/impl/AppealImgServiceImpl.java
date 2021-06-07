package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.dto.appealimg.ListAppealImgResultDTO;
import com.blockchain.server.otc.mapper.AppealImgMapper;
import com.blockchain.server.otc.service.AppealImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppealImgServiceImpl implements AppealImgService {

    @Autowired
    private AppealImgMapper appealImgMapper;

    @Override
    public List<ListAppealImgResultDTO> listAppealImgByAppealDetailId(String appealDetailId) {
        return appealImgMapper.listAppealImgByAppealDetailId(appealDetailId);
    }
}
