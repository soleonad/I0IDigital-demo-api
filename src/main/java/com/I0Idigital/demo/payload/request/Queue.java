package com.I0Idigital.demo.payload.request;

import com.I0Idigital.demo.domain.Order;

import java.util.List;

public class Queue {
    private Long id;
    private String name;
    private Integer maxQuantity;
    private Long currentIndex;

    private List<Order> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Long getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Long currentIndex) {
        this.currentIndex = currentIndex;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
