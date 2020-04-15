package com.service.Impl;

import com.mapper.AreaMapper;
import com.pojo.Area;
import com.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaMapper areaMapper;
    @Override
    public Area queryArea(int uid) {
        return areaMapper.queryArea(uid);
    }

    @Override
    public Area queryAreaById(int id) {
        return areaMapper.queryAreaById(id);
    }

    @Override
    public Area queryAreaByName(String name) {
        return areaMapper.queryAreaByName(name);
    }

    @Override
    public void update(Area area) {
        areaMapper.update(area);
    }

    @Override
    public void updateForm(Area area) {
        areaMapper.updateForm(area);
    }

    @Override
    public void add(Area area) {
        areaMapper.add(area);
    }
}
