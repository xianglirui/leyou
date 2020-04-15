package com.service;

import com.pojo.Article;
import com.pojo.Comment;
import com.pojo.PageInfo;

import java.util.List;

public interface ArticleService {
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
    PageInfo<Article> queryArticle(int pageCode, int pageSize);
    List<Article> queryLocalArticle(String city);
    List<Article> queryLocalArticleWithoutCity();

}
