package com.itechart.javalab.webservice.rest.service;

import com.google.common.base.Strings;
import com.itechart.javalab.webservice.rest.model.Article;
import com.itechart.javalab.webservice.rest.model.Comment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class ArticleValidator {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int LONG_TEXT_FIELD_LENGTH = 255;
    private static final int DEFAULT_TEXT_FIELD_LENGTH = 50;

    private static final List<DateTimeFormatter> formatters = Arrays.asList(DateTimeFormatter.BASIC_ISO_DATE, DateTimeFormatter.ISO_LOCAL_DATE);
    private Logger logger = LogManager.getLogger(ArticleValidator.class);

    public boolean validateArticle(Article article) {
        return !(article == null ||
                Strings.isNullOrEmpty(article.getTitle()) || article.getTitle().length() > DEFAULT_TEXT_FIELD_LENGTH ||
                Strings.isNullOrEmpty(article.getAddress()) || article.getAddress().length() > DEFAULT_TEXT_FIELD_LENGTH ||
                (!Strings.isNullOrEmpty(article.getDescription()) && article.getDescription().length() > LONG_TEXT_FIELD_LENGTH) ||
                (!Strings.isNullOrEmpty(article.getCategory()) && article.getCategory().length() > DEFAULT_TEXT_FIELD_LENGTH)
        );
    }

    public ArticleFilter validateFilter(String searchText, String dateFrom, String dateTo, BigDecimal maxPrice, Integer page, Integer size) {
        if (page != null) {
            if (page > 0) {
                page = page - 1;
            } else {
                page = 0;
            }
        }
        if (size == null || size < 0) {
            size = DEFAULT_PAGE_SIZE;
        }
        searchText = Strings.emptyToNull(searchText);
        if (searchText != null) {
            searchText = searchText.trim().toLowerCase();
        }
        if (dateFrom == null && dateTo == null) {
            return new ArticleFilter(page, size, searchText, maxPrice);
        }
        LocalDateTime from = parseDate(dateFrom);
        LocalDateTime to = parseDate(dateTo);
        if (from != null && to != null && from.isAfter(to)) {
            return null; //dateFrom must be after dateTo
        }

        return new ArticleFilter(page, size, searchText, from, to, maxPrice);
    }

    public boolean validateComment(Comment comment) {
        return !(comment == null ||
            Strings.isNullOrEmpty(comment.getText()) ||
                comment.getText().length() > LONG_TEXT_FIELD_LENGTH ||
            Strings.isNullOrEmpty(comment.getUsername()) ||
                comment.getUsername().length() > DEFAULT_TEXT_FIELD_LENGTH);
    }

    private LocalDateTime parseDate(String date) {
        if (Strings.isNullOrEmpty(date)) {
            return null;
        }
        for (DateTimeFormatter formatter: formatters) {
            try {
                return LocalDate.parse(date, formatter).atStartOfDay();
            } catch (Exception e) {
                logger.error("Unable to parse date %s", date);
            }
        }
        return null;
    }
}
