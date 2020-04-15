package com.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mapper.ArticleMapper;
import com.pojo.Article;
import com.pojo.Comment;
import com.pojo.PageInfo;
import com.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public Article article(int id) {
        return articleMapper.article(id);
    }

    @Override
    public void addArticle(Article article) {
        articleMapper.addArticle(article);
    }

    @Override
    public List<Article> articleList(int uid) {
        return articleMapper.articleList(uid);
    }

    @Override
    public List<Article> queryByCity(String city) {
        return articleMapper.queryByCity(city);
    }

    @Override
    public List<Article> queryNotCityOrderByClick(String city) {
        return articleMapper.queryNotCityOrderByClick(city);
    }

    @Override
    public void updateZan(int good, int id) {
        articleMapper.updateZan(good,id);
    }

    @Override
    public void updateCollect(int collect, int id) {
        articleMapper.updateCollect(collect,id);
    }

    @Override
    public void updateDis(int id) {
        articleMapper.updateDis(id);
    }

    @Override
    public void updateClick(int click, int id) {
        articleMapper.updateClick(click,id);
    }

    @Override
    public void editArticle(Article article) {
        articleMapper.editArticle(article);
    }

    @Override
    public PageInfo<Article> queryArticle(int pageCode, int pageSize) {
        PageHelper.startPage(pageCode,pageSize);
        Page<Article> page = articleMapper.queryArticle();
        return new PageInfo<Article>(page.getTotal(),page.getResult());
    }

    @Override
    public List<Article> queryLocalArticle(String city) {
        return articleMapper.queryLocalArticle(city);
    }

    @Override
    public List<Article> queryLocalArticleWithoutCity() {
        return articleMapper.queryLocalArticleWithoutCity();
    }
}
