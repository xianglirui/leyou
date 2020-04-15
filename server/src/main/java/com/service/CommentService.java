package com.service;

import com.pojo.Comment;
import com.pojo.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentService {
    List<Comment> commentList(int uid);
    void addComment(Comment comment);
    Comment commentByTid(int uid,int tid);
    Comment commentByAid(int uid,int aid);
    void updateBack(int aid, String back,int id);
    List<Comment> commentList1(int aid);
    List<Comment> commentList2(int tid);
    PageInfo<Comment> queryComment(int pageCode, int pageSize,int tid);
}
