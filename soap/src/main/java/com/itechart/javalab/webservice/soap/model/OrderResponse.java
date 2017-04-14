package com.itechart.javalab.webservice.soap.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderResponse implements Serializable {

    @XmlElement(name = "order-no")
    private String orderNo;
    private String address;
    @XmlElement(name = "delivery-time")
    private String deliveryTime;
    @XmlElement(name = "total-price")
    private BigDecimal totalPrice;

    public OrderResponse() {}
    
    public OrderResponse(String orderNo, String address, String deliveryTime, BigDecimal totalPrice) {
        this.orderNo = orderNo;
        this.address = address;
        this.deliveryTime = deliveryTime;
        this.totalPrice = totalPrice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
