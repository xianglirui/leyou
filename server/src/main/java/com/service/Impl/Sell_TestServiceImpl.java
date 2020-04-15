package com.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mapper.Sell_TestMapper;
import com.pojo.Comment;
import com.pojo.PageInfo;
import com.pojo.Sell_Test;
import com.service.Sell_TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Sell_TestServiceImpl implements Sell_TestService {
    @Autowired
    Sell_TestMapper sell_testMapper;
    @Override
    public PageInfo<Sell_Test> queryComment(int pageCode, int pageSize, int uid) {
        PageHelper.startPage(pageCode,pageSize);
        Page<Sell_Test> page = sell_testMapper.queryByUid(uid);
        return new PageInfo<Sell_Test>(page.getTotal(),page.getResult());
    }
}
