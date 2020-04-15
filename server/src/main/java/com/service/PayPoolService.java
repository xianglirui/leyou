package com.service;

import com.pojo.PayPool;

import java.util.List;

public interface PayPoolService {
    void add(PayPool payPool);
    List<PayPool> payPoolList();
    void updateState(int id,int state);
    void updateToArea(int id);
    List<PayPool> queryByToAreaOne(int aid);
}
