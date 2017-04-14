package com.itechart.javalab.webservice.rest.service;

import com.itechart.javalab.webservice.rest.dao.ArticleDAO;
import com.itechart.javalab.webservice.rest.dto.ArticleWrapper;
import com.itechart.javalab.webservice.rest.model.Article;
import com.itechart.javalab.webservice.rest.model.Comment;

import java.util.List;

public class ArticleServiceImpl implements ArticleService {

    private ArticleDAO storage = new ArticleDAO();

    @Override
    public Article create(Article article) {
        return storage.add(article);
    }

    @Override
    public Article update(Integer id, Article article) {
        return storage.update(id, article);
    }

    @Override
    public Article delete(Integer id) {
        return storage.delete(id);
    }

    @Override
    public Article find(Integer id) {
        return storage.findById(id);
    }

    @Override
    public ArticleWrapper findAllCounted(ArticleFilter filter) {
        List<Article> data = storage.find(filter);
        Long count = storage.count(filter);
        Integer page = filter.getPage();
        if (page != null) {
            ++page;
        }
        return new ArticleWrapper(data, page, count);
    }

    @Override
    public List<Article> findAll(ArticleFilter filter) {
        return storage.find(filter);
    }

    @Override
    public Article addComment(Integer id, Comment comment) {
        return storage.addComment(id, comment);
    }
}
