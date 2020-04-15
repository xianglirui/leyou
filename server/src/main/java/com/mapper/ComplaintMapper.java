package com.mapper;

import com.pojo.Complaint;
import jdk.jfr.Registered;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@Registered
public interface ComplaintMapper {
    void add(Complaint complaint);
    List<Complaint> compList();
    List<Complaint> queryByUid(int uid);
    Complaint queryById(int id);
    void updateFeedBack(@Param("id") int id,@Param("feedback") String feedback,@Param("dealtime") String dealtime);
    void updateState(int id);
    void updateAdminId(int adid,int id);
}
