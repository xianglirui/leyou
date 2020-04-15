package com.service;

import com.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> orderList(int uid);
    OrderItem queryOrderItem(int uid);
    int add(OrderItem orderItem);
    void updateComment(String comment,int rate,int id,String comment_time);
    void updateState(int state,int id);
    void updateStateByUid(int state,int uid);
    void updateRefund(int refund,int id);
    OrderItem queryById(int id);
}
