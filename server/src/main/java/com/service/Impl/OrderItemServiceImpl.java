package com.service.Impl;

import com.mapper.OrderItemMapper;
import com.pojo.OrderItem;
import com.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItem> orderList(int uid) {
        return orderItemMapper.orderList(uid);
    }

    @Override
    public OrderItem queryOrderItem(int uid) {
        return orderItemMapper.queryOrderItem(uid);
    }

    @Override
    public int add(OrderItem orderItem) {
        return orderItemMapper.add(orderItem);
    }

    @Override
    public void updateComment(String comment,int rate,int id,String comment_time) {
        orderItemMapper.updateComment(comment,rate,id,comment_time);
    }

    @Override
    public void updateState(int state,int id) {
        orderItemMapper.updateState(state,id);
    }

    @Override
    public void updateStateByUid(int state, int uid) {
        orderItemMapper.updateStateByUid(state,uid);
    }


    @Override
    public void updateRefund(int refund,int id) {
        orderItemMapper.updateRefund(refund,id);
    }

    @Override
    public OrderItem queryById(int id) {
        return orderItemMapper.queryById(id);
    }


}
