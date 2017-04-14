package com.itechart.javalab.webservice.rest.dto;

import com.itechart.javalab.webservice.rest.model.Article;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ArticleWrapper {

    private List<Article> data;

    private Integer page;

    private Long totalCount;

    public ArticleWrapper() {
    }

    public ArticleWrapper(List<Article> data, Integer page, Long totalCount) {
        this.data = data;
        this.page = page;
        this.totalCount = totalCount;
    }

    @XmlElementWrapper(name = "articles")
    @XmlElement(name = "article")
    public List<Article> getData() {
        return data;
    }

    public void setData(List<Article> data) {
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
