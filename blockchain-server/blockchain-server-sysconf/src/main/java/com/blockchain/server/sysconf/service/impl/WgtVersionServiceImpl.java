package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.entity.WgtVersion;
import com.blockchain.server.sysconf.mapper.WgtVersionMapper;
import com.blockchain.server.sysconf.service.WgtVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class WgtVersionServiceImpl implements WgtVersionService {
    @Autowired
    private WgtVersionMapper wgtVersionMapper;

    @Override
    @Transactional
    public int updatewgtVersion(String id, String wgtVersion, String wgtUrl, String remark) {
        WgtVersion wgtVersionObj = new WgtVersion();
        wgtVersionObj.setId(id);
        wgtVersionObj.setWgtVersion(wgtVersion);
        wgtVersionObj.setWgtUrl(wgtUrl);
        wgtVersionObj.setRemark(remark);
        wgtVersionObj.setModifyTime(new Date());
        return  wgtVersionMapper.updateByPrimaryKeySelective(wgtVersionObj);
    }

    @Override
    public WgtVersion findNewWgtVersion() {
        return wgtVersionMapper.findNewWgtVersion();
    }

    @Override
    public List<WgtVersion> listAll() {
        return wgtVersionMapper.listAll();
    }


    @Override
    @Transactional
    public void deleteWgtVersionById(String id) {
        wgtVersionMapper.deleteWgtVersionById(id);
    }

    @Override
    @Transactional
    public int saveWgtVersion(String wgtVersion, String wgtUrl, String remark) {
        WgtVersion wgtVersionObj = new WgtVersion();
        wgtVersionObj.setId(UUID.randomUUID().toString());
        wgtVersionObj.setWgtVersion(wgtVersion);
        wgtVersionObj.setWgtUrl(wgtUrl);
        wgtVersionObj.setRemark(remark);
        wgtVersionObj.setModifyTime(new Date());
        wgtVersionObj.setCreateTime(new Date());
        return  wgtVersionMapper.insert(wgtVersionObj);
    }

    @Override
    public WgtVersion findWgtVersionById(String id) {
        return wgtVersionMapper.findWgtVersionById(id);
    }

    @Override
    public List<WgtVersion> selectVersionList(WgtVersion wgtVersion) {
        return wgtVersionMapper.selectWgtVersionList(wgtVersion);
    }
}
