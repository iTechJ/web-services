package com.itechart.javalab.webservice.rest.model;

import com.google.common.base.Strings;
import com.itechart.javalab.webservice.rest.util.LocalDateTimeAdapter;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@XmlRootElement(name = "Article")
@XmlAccessorType(XmlAccessType.FIELD)
public class Article {

    private Integer id;

    private String title;

    private String description;

    private String category;

    private BigDecimal price = BigDecimal.ZERO;

    private String address;

    @XmlElementWrapper(name = "comments")
    @XmlElement(name = "comment")
    private Set<Comment> comments = new HashSet<>();

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime modifiedDateTime = LocalDateTime.now();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(LocalDateTime modifiedDate) {
        this.modifiedDateTime = modifiedDate;
    }

    @OneToMany(targetEntity = Comment.class, orphanRemoval = true, mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public static String getReportHeader(String separator) {
        return "id" + separator +
                "title" + separator +
                "description" + separator +
                "category" + separator +
                "price" + separator +
                "address" + separator +
                "modifiedDateTime";
    }

    public String toDelimitedString(String separator) {
        return id + separator +
                appendDQ(title) + separator +
                appendDQ(Strings.nullToEmpty(description)) + separator +
                appendDQ(Strings.nullToEmpty(category)) + separator +
                appendDQ(String.valueOf(price)) + separator +
                appendDQ(Strings.nullToEmpty(address)) + separator +
                modifiedDateTime;
    }

    private static String appendDQ(String str) {
        return "\"" + str + "\"";
    }
}
