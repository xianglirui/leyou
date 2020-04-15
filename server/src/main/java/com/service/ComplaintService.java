package com.service;

import com.pojo.Complaint;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ComplaintService {
    void add(Complaint complaint);
    List<Complaint> compList();
    Complaint queryById(int id);
    List<Complaint> queryByUid(int uid);
    void updateState(int id);
    void updateFeedBack(int id,String feedback,String dealtime);
    void updateAdminId(int adid,int id);
}
