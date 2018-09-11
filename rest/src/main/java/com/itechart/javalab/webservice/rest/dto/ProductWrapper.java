package com.itechart.javalab.webservice.rest.dto;

import com.itechart.javalab.webservice.rest.model.Product;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
public class ProductWrapper {

    private Set<Product> data = new HashSet<>();

    private Integer totalCount;

    public ProductWrapper() {
    }

    public ProductWrapper(Set<Product> data) {
        this.data = data;
        this.totalCount = data.size();
    }

    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    public Set<Product> getData() {
        return data;
    }

    public void setData(Set<Product> data) {
        this.data = data;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
