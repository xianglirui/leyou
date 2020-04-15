package com.mapper;

import com.pojo.Collect;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CollectMapper {
    List<Collect> collectList(int uid);
    Collect selectByUidAndTid(int uid,int tid);
    Collect selectByUidAndAid(int uid,int aid);
    void updateZan(int tid,int aid);
    void addCollect(Collect collect);
    int selectCount(int aid);
}
