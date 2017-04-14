package com.itechart.javalab.webservice.rest.service;

import com.itechart.javalab.webservice.rest.dto.ArticleWrapper;
import com.itechart.javalab.webservice.rest.model.Article;
import com.itechart.javalab.webservice.rest.model.Comment;

import java.util.List;

public interface ArticleService {

    Article create(Article article);

    Article update(Integer id, Article article);

    Article delete(Integer id);

    Article find(Integer id);

    ArticleWrapper findAllCounted(ArticleFilter filter);

    List<Article> findAll(ArticleFilter filter);

    Article addComment(Integer id, Comment comment);

}
