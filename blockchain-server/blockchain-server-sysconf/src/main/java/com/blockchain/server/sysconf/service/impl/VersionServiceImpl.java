package com.blockchain.server.sysconf.service.impl;

import com.blockchain.server.sysconf.common.constant.VersionConstant;
import com.blockchain.server.sysconf.entity.Version;
import com.blockchain.server.sysconf.mapper.VersionMapper;
import com.blockchain.server.sysconf.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class VersionServiceImpl implements VersionService {

    @Autowired
    private VersionMapper versionMapper;

    @Override
    @Transactional
    public int updateVersion(String id, String version, String appUrl, String remark, Integer compel, String device) {
        Version ver = new Version();
        ver.setId(id);
        ver.setVersion(version);
        ver.setAppUrl(appUrl);
        ver.setRemark(remark);
        ver.setCompel(compel);
        ver.setDevice(device);
        ver.setModifyTime(new Date());
        return  versionMapper.updateByPrimaryKeySelective(ver);

    }

    @Override
    public Version findNewVersion(String device) {
        return versionMapper.findNewVersion(device);
    }

    @Override
    public List<Version> listAll(String device) {
        return versionMapper.listAll(device);
    }

    @Override
    public Map<String, Version> findNewVersionAll() {
        Version versionIos = versionMapper.findNewVersion(VersionConstant.SYSTEMTYPE_IOS);
        Version versionAndroid = versionMapper.findNewVersion(VersionConstant.SYSTEMTYPE_ANDROID);
        Map<String, Version> VersionMap = new HashMap();
        VersionMap.put(VersionConstant.SYSTEMTYPE_IOS, versionIos);
        VersionMap.put(VersionConstant.SYSTEMTYPE_ANDROID, versionAndroid);
        return VersionMap;
    }

    @Override
    @Transactional
    public void deleteVersionById(String id) {
        versionMapper.deleteVersionById(id);
    }

    @Override
    @Transactional
    public int saveVersion(String version, String appUrl, String remark, Integer compel, String device) {
        Version ver = new Version();
        ver.setId(UUID.randomUUID().toString());
        ver.setVersion(version);
        ver.setAppUrl(appUrl);
        ver.setRemark(remark);
        ver.setCompel(compel);
        ver.setDevice(device);
        ver.setCreateTime(new Date());
        ver.setModifyTime(new Date());
        return versionMapper.insert(ver);
    }

    @Override
    public Version findVersionById(String id) {
        return versionMapper.findVersionById(id);
    }

    @Override
    public List<Version> selectVersionList(Version version) {
        return versionMapper.selectVersionList(version);
    }

}
