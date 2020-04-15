package com.mapper;

import com.pojo.Area;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AreaMapper {
    Area queryArea(int uid);
    Area queryAreaByName(String name);
    Area queryAreaById(int id);
    void update(Area area);
    void updateForm(Area area);
    void add(Area area);
    List<Area> queryListByCity(String city);
}
