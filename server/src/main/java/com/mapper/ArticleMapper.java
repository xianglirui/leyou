package com.mapper;

import com.github.pagehelper.Page;
import com.pojo.Article;
import com.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {
    Article article(int id);
    void addArticle(Article article);
    List<Article> articleList(int uid);
    List<Article> queryByCity(String city);
    List<Article> queryNotCityOrderByClick(String city);
    void updateZan(int good,int id);
    void updateCollect(int collect,int id);
    void updateDis(int id);
    void updateClick(int click,int id);
    void editArticle(Article article);
    Page<Article> queryArticle();
    List<Article> queryLocalArticle(String city);
    List<Article> queryLocalArticleWithoutCity();
}
