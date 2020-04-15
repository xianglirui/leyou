package com.mapper;

import com.pojo.Search;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SearchMapper {
    List<Search> queryAutoToArea(String name);
    List<Search> queryArticleIfHaveYOUJI(String name);
    List<Search> queryAreaByClick();
    void add(Search search);
}
