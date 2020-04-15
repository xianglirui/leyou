package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapper.CommentMapper;
import com.pojo.Comment;
import com.pojo.OrderItem;
import com.pojo.PageInfo;
import com.service.CommentService;
import com.service.OrderItemService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CommentMapper commentMapper;

    @GetMapping("/addCommentWithArea")
    public String addCommentWithArea(@RequestParam int uid,@RequestParam int aid,@RequestParam String html,@RequestParam int value,@RequestParam int oid){
        Comment c = commentService.commentByAid(uid,aid);
        if(c==null){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String starttime = df.format(new Date());
            Comment comment = new Comment();
            comment.setAid(aid);
            comment.setContent(html);
            comment.setUid(uid);
            comment.setOid(oid);
            comment.setStar(value);
            comment.setTimes(starttime);
            commentService.addComment(comment);
            orderItemService.updateState(2,oid);
            return "ok";
        }else {
            return "err";
        }
    }

    @GetMapping("/addCommentWithTravel")
    public String addCommentWithTravel(@RequestParam int uid,@RequestParam int tid,@RequestParam String html){
        Comment c = commentService.commentByTid(uid,tid);
        if(c==null){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String starttime = df.format(new Date());
            Comment comment = new Comment();
            comment.setTid(tid);
            comment.setTimes(starttime);
            comment.setContent(html);
            comment.setUid(uid);
            commentService.addComment(comment);
            return "ok";
        }else {
            return "err";
        }
    }

    @GetMapping("/selectCommentListByUid")
    public String selectCommentListByUid(@RequestParam int uid) throws JsonProcessingException {
        List<Comment> comments = commentService.commentList(uid);
        if(comments==null){
            return "err";
        }else {
            return new ObjectMapper().writeValueAsString(comments);
        }
    }

    @GetMapping("/getCommentByAid")
    public String getCommentByAid(@RequestParam int aid) throws JsonProcessingException {
        List<Comment> commentList = commentService.commentList1(aid);
        return new ObjectMapper().writeValueAsString(commentList);
    }

    @GetMapping("/getCommentByTid")
    public String getCommentByTid(@RequestParam int tid,@RequestParam int pageCode) throws JsonProcessingException {
        PageInfo<Comment> pageBean = commentService.queryComment(pageCode,3,tid);
        return new ObjectMapper().writeValueAsString(pageBean);
    }

    @GetMapping("/updateBack")
    public String updateBack(@RequestParam String text,@RequestParam int aid,@RequestParam int id){
        commentService.updateBack(aid,text,id);
        return "ok";
    }

    @PostMapping("/getArticleComment")
    public String getArticleComment(@RequestBody List<Integer> idList) throws JsonProcessingException {
        List<Integer> count = new ArrayList<>();
        for(int i : idList){
            count.add(commentMapper.selectCountByTid(i));
        }
        return new ObjectMapper().writeValueAsString(count);
    }

}
