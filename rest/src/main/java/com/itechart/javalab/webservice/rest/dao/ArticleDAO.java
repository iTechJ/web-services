package com.itechart.javalab.webservice.rest.dao;

import com.itechart.javalab.webservice.rest.model.Article;
import com.itechart.javalab.webservice.rest.model.Comment;
import com.itechart.javalab.webservice.rest.service.ArticleFilter;
import com.itechart.javalab.webservice.rest.util.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleDAO {

    private static Logger logger = LogManager.getLogger(ArticleDAO.class);

    private final String articleSearchQuery = "from Article a" +
            " where (:dateFrom is null or a.modifiedDateTime > :dateFrom) " +
            "and (:dateTo is null or a.modifiedDateTime < :dateTo) " +
            "and (:searchText is null or " +
            "lower(a.address) like :searchText or " +
            "lower(a.title) like :searchText or " +
            "lower(a.description) like :searchText or " +
            "lower(a.category) like :searchText)" +
            "and (:maxPrice is null or a.price<:maxPrice)" ;

    public Article add(Article article) {
        Session session = HibernateUtil.getInstance().openSession();
        Article newArticle = new Article();
        newArticle.setTitle(article.getTitle());
        newArticle.setDescription(article.getDescription());
        newArticle.setAddress(article.getAddress());
        session.save(newArticle);
        HibernateUtil.getInstance().commitAndClose(session);
        logger.info("New Article added, id: {}", newArticle.getId());
        return newArticle;
    }

    public Article update(Integer id, Article article) {
        Session session = HibernateUtil.getInstance().openSession();
        Article existing = session.get(Article.class, id);
        if (existing != null) {
            existing.setAddress(article.getAddress());
            existing.setDescription(article.getDescription());
            existing.setTitle(article.getTitle());
            existing.setModifiedDateTime(LocalDateTime.now());
            session.update(existing);
            logger.info("Article updated, id: {}", id);
        }
        HibernateUtil.getInstance().commitAndClose(session);
        return existing;
    }

    public Article delete(Integer id) {
        Session session = HibernateUtil.getInstance().openSession();
        Article article = session.get(Article.class, id);
        if (article != null) {
            session.delete(article);
            logger.info("Article deleted, id: {}", article.getId());
        }
        HibernateUtil.getInstance().commitAndClose(session);
        return article;
    }

    public Article findById(Integer id) {
        Session session = HibernateUtil.getInstance().openSession();
        Article article = session.get(Article.class, id);
        HibernateUtil.getInstance().commitAndClose(session);
        return article;
    }

    public List<Article> find(ArticleFilter filter) {
        Session session = HibernateUtil.getInstance().openSession();
        Query searchQuery = session.createQuery(articleSearchQuery, Article.class);
        searchQuery.setParameter("dateFrom", filter.getDateFrom());
        searchQuery.setParameter("dateTo", filter.getDateTo());
        searchQuery.setParameter("searchText", convertText(filter.getSearchText()));
        searchQuery.setParameter("maxPrice", filter.getMaxPrice());
        if (filter.getPage() != null && filter.getSize() != null) {
            searchQuery.setFirstResult(filter.getPage() * filter.getSize());
            searchQuery.setMaxResults(filter.getSize());
        }
        List<Article> articles = searchQuery.getResultList();
        HibernateUtil.getInstance().commitAndClose(session);
        return articles;
    }

    public Long count(ArticleFilter filter) {
        String countQueryStr = "select count(*) " + articleSearchQuery;
        Session session = HibernateUtil.getInstance().openSession();
        Query countQuery = session.createQuery(countQueryStr);
        countQuery.setParameter("dateFrom", filter.getDateFrom());
        countQuery.setParameter("dateTo", filter.getDateTo());
        countQuery.setParameter("searchText", convertText(filter.getSearchText()));
        countQuery.setParameter("maxPrice", filter.getMaxPrice());
        Long count = (Long) countQuery.getResultList().get(0);
        HibernateUtil.getInstance().commitAndClose(session);
        return count;
    }

    private String convertText(String text) {
        if (text == null) {
            return null;
        }
        return "%" + text + "%";
    }

    public Article addComment(Integer id, Comment comment) {
        Session session = HibernateUtil.getInstance().openSession();
        Article article = session.get(Article.class, id);
        if (article != null) {
            comment.setArticle(article);
            article.getComments().add(comment);
            session.update(article);
        }
        HibernateUtil.getInstance().commitAndClose(session);
        return article;
    }



}
