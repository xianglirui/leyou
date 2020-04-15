package com.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapper.SearchMapper;
import com.pojo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {
    @Autowired
    private SearchMapper searchMapper;

    @GetMapping("/SearchKey")
    public String SearchKey(@RequestParam String name) throws JsonProcessingException {
        List<Search> queryAutoToArea = searchMapper.queryAutoToArea(name);
        List<Search> queryArticleIfHaveYOUJI = searchMapper.queryArticleIfHaveYOUJI(name);
        queryArticleIfHaveYOUJI.addAll(queryAutoToArea);
        return new ObjectMapper().writeValueAsString(queryArticleIfHaveYOUJI);
    }

    @GetMapping("/hotSearchList")
    public String hotSearchList() throws JsonProcessingException {
        List<Search> queryAreaByClick = searchMapper.queryAreaByClick();
        return new ObjectMapper().writeValueAsString(queryAreaByClick);
    }
}
