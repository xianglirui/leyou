package com.mapper;

import com.pojo.OrderItem;
import jdk.jfr.Registered;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
@Registered
public interface OrderItemMapper {
    List<OrderItem> orderList(int uid);
    OrderItem queryOrderItem(int uid);
    int add(OrderItem orderItem);
    void updateComment(@Param("comment") String comment, @Param("rate") int rate, @Param("id") int id,@Param("comment_time") String comment_time);
    void updateState(int state,int id);
    void updateStateByUid(int state,int uid);
    void updateRefund(int refund,int id);
    OrderItem queryById(int id);
    int selectCount(int aid);
}
