package com.itechart.javalab.webservice.rest.model;

import com.google.common.base.Strings;
import com.itechart.javalab.webservice.rest.util.IdGenerator;
import com.itechart.javalab.webservice.rest.util.LocalDateTimeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "Product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {

    private Integer id;

    private String title;

    private String description;

    private String category;

    private BigDecimal price = BigDecimal.ZERO;

    private Integer amount;

    @XmlElementWrapper
    @XmlElement(name = "review")
    private Set<Review> reviews = new HashSet<>();

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime modifiedDateTime = LocalDateTime.now();

    public Product() {

    }

    public Product(String title, String description, String category, BigDecimal price, Integer amount) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.amount = amount;
        this.id = IdGenerator.nextId();
    }

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(LocalDateTime modifiedDate) {
        this.modifiedDateTime = modifiedDate;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public static String getReportHeader(String separator) {
        return "id" + separator +
                "title" + separator +
                "description" + separator +
                "category" + separator +
                "price" + separator +
                "amount" + separator +
                "modifiedDateTime";
    }

    public String toDelimitedString(String separator) {
        return id + separator +
                appendDQ(title) + separator +
                appendDQ(Strings.nullToEmpty(description)) + separator +
                appendDQ(Strings.nullToEmpty(category)) + separator +
                appendDQ(String.valueOf(price)) + separator +
                appendDQ(String.valueOf(amount)) + separator +
                modifiedDateTime;
    }

    private static String appendDQ(String str) {
        return "\"" + str + "\"";
    }
}
