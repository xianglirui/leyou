package com.mapper;

import com.pojo.LocalHot;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LocalHotMapper {
    List<LocalHot> localByTid(int tid);
    List<LocalHot> localByAid(int aid);
    void add(LocalHot localHot);
    void updateClick(int tid,int click);
}
