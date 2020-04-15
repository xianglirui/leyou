package com.service;

import com.pojo.Step;

public interface StepService {
    Step queryStep(int uid);
    void update(Step step);
    void update1(int active,int uid);
    void insert(Step step);
}
