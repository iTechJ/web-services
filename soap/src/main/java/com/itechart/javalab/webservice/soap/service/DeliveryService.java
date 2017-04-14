package com.itechart.javalab.webservice.soap.service;

import com.google.common.base.Strings;
import com.itechart.javalab.webservice.soap.model.Order;
import com.itechart.javalab.webservice.soap.model.OrderResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@WebService(targetNamespace = "http://itechart.com/delivery")
public class DeliveryService {

    private final SecureRandom random = new SecureRandom();
    private static final int DEFAULT_DELIVERY_TIME = 1; // 1 hour
    private Logger logger = LogManager.getLogger(DeliveryService.class);

    @WebMethod(operationName = "place-order")
    @WebResult(name = "order-response", targetNamespace = "http://itechart.com/delivery")
    public OrderResponse placeOrder(@WebParam(name = "order", targetNamespace = "http://itechart.com/delivery") Order order) {
        if (!validate(order)) {
            throw new IllegalArgumentException("Fill all order fields");
        }
        BigDecimal totalPrice = order.getPositions()
                .stream()
                .map(item -> item.getAmount().multiply(item.getPrice())) // extract total price for each item
                .reduce(BigDecimal.ZERO, BigDecimal::add); // sum total prices
        return new OrderResponse(generateOrderNumber(), order.getAddress(), calculateOrderTime(order), totalPrice);
    }

    private boolean validate(Order order) {
        return !(order == null ||
            order.getPositions() == null || order.getPositions().isEmpty() ||
            Strings.isNullOrEmpty(order.getPhone()) ||
            Strings.isNullOrEmpty(order.getAddress()) ||
            order.getPositions()
                    .stream()
                    .anyMatch(item -> invalidAmount(item.getPrice()) || invalidAmount(item.getAmount())) // amount OR price is invalid
        );
    }

    private boolean invalidAmount(BigDecimal amount) {
        return amount == null || BigDecimal.ZERO.compareTo(amount) == 1;
    }

    private String generateOrderNumber() {
        return new BigInteger(130, random).toString(32);
    }

    private String calculateOrderTime(Order order) {

        if (order.getOrderTime() != null) {
            try {
                LocalTime deliveryTime = LocalTime.parse(order.getOrderTime(), DateTimeFormatter.ISO_LOCAL_TIME);
                if (deliveryTime.isAfter(LocalTime.now())) {
                    return deliveryTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
                }
            } catch (DateTimeParseException ex) {
                logger.warn("Unable to parse input time");
            }
        }
        return LocalDateTime.now().plusHours(DEFAULT_DELIVERY_TIME).format(DateTimeFormatter.ISO_LOCAL_TIME);

    }
}
