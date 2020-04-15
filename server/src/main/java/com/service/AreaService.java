package com.service;

import com.pojo.Area;

public interface AreaService {
    Area queryArea(int uid);
    Area queryAreaById(int id);
    Area queryAreaByName(String name);
    void update(Area area);
    void updateForm(Area area);
    void add(Area area);
}
