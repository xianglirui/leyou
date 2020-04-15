package com.mapper;

import com.pojo.PayPool;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Repository
public interface PayPoolMapper {
    void add(PayPool payPool);
    List<PayPool> payPoolList();
    void updateState(int id,int state);
    void updateToArea(int id);
    List<PayPool> queryByToAreaOne(int aid);
}
