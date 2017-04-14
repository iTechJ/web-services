package com.itechart.javalab.webservice.rest.controller;

import com.itechart.javalab.webservice.rest.dto.ArticleWrapper;
import com.itechart.javalab.webservice.rest.model.Article;
import com.itechart.javalab.webservice.rest.model.Comment;
import com.itechart.javalab.webservice.rest.service.ArticleFilter;
import com.itechart.javalab.webservice.rest.service.ArticleService;
import com.itechart.javalab.webservice.rest.service.ArticleServiceImpl;
import com.itechart.javalab.webservice.rest.service.ArticleValidator;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Path("/article")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ArticleController {

    private static final int UNPROCESSABLE_ENTITY_STATUS = 422;

    private ArticleService articleService = new ArticleServiceImpl();
    private ArticleValidator validator = new ArticleValidator();

    @GET
    public Response getArticles(@QueryParam("searchText") String searchText,
                                @QueryParam("dateFrom") String dateFrom,
                                @QueryParam("dateTo") String dateTo,
                                @QueryParam("maxPrice") BigDecimal maxPrice,
                                @QueryParam("page") Integer page,
                                @QueryParam("size") Integer size) {
        ArticleFilter filter = validator.validateFilter(searchText, dateFrom, dateTo, maxPrice, page, size);
        if (filter == null) {
            return Response.status(UNPROCESSABLE_ENTITY_STATUS).build();
        }
        ArticleWrapper articles = articleService.findAllCounted(filter);
        return Response.ok(articles).build();
    }

    @GET
    @Path("/{id}")
    public Response getArticle(@PathParam("id") Integer articleId) {
        Article article = articleService.find(articleId);
        if (article == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(article).build();
    }

    @GET
    @Path("/report")
    @Produces("text/csv")
    public Response buildReport(@QueryParam("searchText") String searchText,
                                @QueryParam("dateFrom") String dateFrom,
                                @QueryParam("dateTo") String dateTo,
                                @QueryParam("maxPrice") BigDecimal maxPrice,
                                @QueryParam("page") Integer page,
                                @QueryParam("size") Integer size) {
        ArticleFilter filter = validator.validateFilter(searchText, dateFrom, dateTo, maxPrice, page, size);
        if (filter == null) {
            return Response.status(UNPROCESSABLE_ENTITY_STATUS).build();
        }
        List<Article> searchResults = articleService.findAll(filter);
        final String reportSeparator = ",";
        String result = searchResults.stream()
                .map(article -> article.toDelimitedString(reportSeparator))
                .reduce(Article.getReportHeader(reportSeparator), (s1, s2) -> s1 += System.getProperty("line.separator") + s2);
        return Response.ok(result)
                .header("Content-Disposition", String.format("attachment; filename=report_%s.csv", LocalDate.now().toString()))
                .build();
    }

    @POST
    public Response createArticle(Article article) {
        if (!validator.validateArticle(article)) {
            return Response.status(UNPROCESSABLE_ENTITY_STATUS).build();
        }
        Article newArticle = articleService.create(article);
        return Response.status(ClientResponse.Status.CREATED)
                .entity(newArticle)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateArticle(@PathParam("id") Integer articleId, Article article) {
        if (!validator.validateArticle(article)) {
            return Response.status(UNPROCESSABLE_ENTITY_STATUS).build();
        }
        Article updated = articleService.update(articleId, article);
        if (updated == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updated).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteArticle(@PathParam("id") Integer articleId) {
        Article article = articleService.delete(articleId);
        if (article == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();

    }

    @POST
    @Path("/{id}/comment")
    public Response postComment(@PathParam("id") Integer id, Comment comment) {
        if (!validator.validateComment(comment)) {
            return Response.status(UNPROCESSABLE_ENTITY_STATUS).build();
        }
        Article article = articleService.addComment(id, comment);
        if (article == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(article)
                .build();
    }

}
