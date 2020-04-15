package com.service.Impl;

import com.mapper.CollectMapper;
import com.pojo.Collect;
import com.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Override
    public List<Collect> collectList(int uid) {
        return collectMapper.collectList(uid);
    }

    @Override
    public void addCollect(Collect collect) {
        collectMapper.addCollect(collect);
    }

    @Override
    public Collect selectByUidAndTid(int uid, int tid) {
        return collectMapper.selectByUidAndTid(uid,tid);
    }

    @Override
    public Collect selectByUidAndAid(int uid, int aid) {
        return collectMapper.selectByUidAndAid(uid,aid);
    }

    @Override
    public void updateZan(int tid, int aid) {
        collectMapper.updateZan(tid,aid);
    }
}
