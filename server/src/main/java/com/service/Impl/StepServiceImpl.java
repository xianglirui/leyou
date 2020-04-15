package com.service.Impl;

import com.mapper.StepMapper;
import com.pojo.Step;
import com.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StepServiceImpl implements StepService {
    @Autowired
    private StepMapper stepMapper;
    @Override
    public Step queryStep(int uid) {
        return stepMapper.queryStep(uid);
    }

    @Override
    public void update(Step step) {
        stepMapper.update(step);
    }

    @Override
    public void update1(int active,int uid) {
        stepMapper.update1(active,uid);
    }

    @Override
    public void insert(Step step) {
        stepMapper.insert(step);
    }


}
