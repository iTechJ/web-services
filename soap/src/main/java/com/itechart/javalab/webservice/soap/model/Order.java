package com.itechart.javalab.webservice.soap.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order implements Serializable {

    @XmlElementWrapper(name = "positions")
    @XmlElement(name = "position")
    private List<OrderItem> positions;
    @XmlElement(name = "customer-name")
    private String customerName;
    private String address;
    private String phone;
    @XmlElement(name = "order-time")
    private String orderTime;
    private String comment;

    public List<OrderItem> getPositions() {
        return positions;
    }

    public void setPositions(List<OrderItem> positions) {
        this.positions = positions;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
