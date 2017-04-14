package com.itechart.javalab.webservice.rest.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ArticleFilter {

    private Integer page;
    private Integer size;
    private String searchText;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private BigDecimal maxPrice;

    ArticleFilter(Integer page, Integer size, String searchText, BigDecimal maxPrice) {
        this.page = page;
        this.size = size;
        this.searchText = searchText;
        this.maxPrice = maxPrice;
    }

    ArticleFilter(Integer page, Integer size, String searchText, LocalDateTime dateFrom, LocalDateTime dateTo, BigDecimal maxPrice) {
        this.page = page;
        this.size = size;
        this.searchText = searchText;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.maxPrice = maxPrice;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public String getSearchText() {
        return searchText;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }
}
