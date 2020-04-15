package com.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mapper.CommentMapper;
import com.pojo.Comment;
import com.pojo.PageInfo;
import com.pojo.Shop;
import com.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public List<Comment> commentList(int uid) {
        return commentMapper.commentList(uid);
    }

    @Override
    public void addComment(Comment comment) {
        commentMapper.addComment(comment);
    }

    @Override
    public Comment commentByTid(int uid, int tid) {
        return commentMapper.commentByTid(uid,tid);
    }

    @Override
    public Comment commentByAid(int uid, int aid) {
        return commentMapper.commentByAid(uid,aid);
    }

    @Override
    public void updateBack(int aid, String back,int id) {
        commentMapper.updateBack(aid,back,id);
    }

    @Override
    public List<Comment> commentList1(int aid) {
        return commentMapper.commentList1(aid);
    }

    @Override
    public List<Comment> commentList2(int tid) {
        return commentMapper.commentList2(tid);
    }

    @Override
    public PageInfo<Comment> queryComment(int pageCode, int pageSize, int tid) {
        PageHelper.startPage(pageCode,pageSize);
        Page<Comment> page = commentMapper.queryComment(tid);
        return new PageInfo<Comment>(page.getTotal(),page.getResult());
    }


}
