package com.service.Impl;

import com.mapper.ComplaintMapper;
import com.pojo.Complaint;
import com.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private ComplaintMapper complaintMapper;
    @Override
    public void add(Complaint complaint) {
        complaintMapper.add(complaint);
    }

    @Override
    public List<Complaint> compList() {
        return complaintMapper.compList();
    }

    @Override
    public Complaint queryById(int id) {
        return complaintMapper.queryById(id);
    }

    @Override
    public List<Complaint> queryByUid(int uid) {
        return complaintMapper.queryByUid(uid);
    }

    @Override
    public void updateState(int id) {
        complaintMapper.updateState(id);
    }

    @Override
    public void updateFeedBack(int id, String feedback,String dealtime) {
        complaintMapper.updateFeedBack(id,feedback,dealtime);
    }

    @Override
    public void updateAdminId(int adid, int id) {
        complaintMapper.updateAdminId(adid,id);
    }


}
