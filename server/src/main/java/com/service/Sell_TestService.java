package com.service;

import com.pojo.Comment;
import com.pojo.PageInfo;
import com.pojo.Sell_Test;

public interface Sell_TestService {
    PageInfo<Sell_Test> queryComment(int pageCode, int pageSize, int uid);
}
