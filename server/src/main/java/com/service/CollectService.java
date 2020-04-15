package com.service;

import com.pojo.Collect;

import java.util.List;

public interface CollectService {
    List<Collect> collectList(int uid);
    void addCollect(Collect collect);
    Collect selectByUidAndTid(int uid,int tid);
    Collect selectByUidAndAid(int uid,int aid);
    void updateZan(int tid,int aid);
}
