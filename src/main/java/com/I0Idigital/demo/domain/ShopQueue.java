package com.I0Idigital.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "shop_queue")
public class ShopQueue implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "max_quantity")
    private Integer maxQuantity;

    @Column(name = "current_index")
    private Long currentIndex;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @JsonIgnore
    private transient List<Order> ordersInQueue;

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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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

    public List<Order> getOrdersInQueue() {
        return ordersInQueue;
    }

    public void setOrdersInQueue(List<Order> ordersInQueue) {
        this.ordersInQueue = ordersInQueue;
    }

    public void increaseIndex() {
        this.currentIndex++;
    }
}
