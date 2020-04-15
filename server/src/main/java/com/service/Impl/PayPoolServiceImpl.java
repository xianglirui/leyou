package com.service.Impl;

import com.mapper.PayPoolMapper;
import com.pojo.PayPool;
import com.service.PayPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayPoolServiceImpl implements PayPoolService {
    @Autowired
    private PayPoolMapper payPoolMapper;
    @Override
    public void add(PayPool payPool) {
        payPoolMapper.add(payPool);
    }

    @Override
    public List<PayPool> payPoolList() {
        return payPoolMapper.payPoolList();
    }

    @Override
    public void updateState(int id, int state) {
        payPoolMapper.updateState(id,state);
    }

    @Override
    public void updateToArea(int id) {
        payPoolMapper.updateToArea(id);
    }

    @Override
    public List<PayPool> queryByToAreaOne(int aid) {
        return payPoolMapper.queryByToAreaOne(aid);
    }


}
