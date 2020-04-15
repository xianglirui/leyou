package com.mapper;

import com.github.pagehelper.Page;
import com.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    List<Comment> commentList(int uid);
    void addComment(Comment comment);
    Comment commentByTid(int uid,int tid);
    Comment commentByAid(int uid,int aid);
    void updateBack(@Param("aid") int aid,@Param("back") String back,@Param("id") int id);
    List<Comment> commentList1(int aid);
    List<Comment> commentList2(int tid);
    Page<Comment> queryComment(int tid);
    int selectCount(int aid);
    int selectCountByTid(int tid);
}
