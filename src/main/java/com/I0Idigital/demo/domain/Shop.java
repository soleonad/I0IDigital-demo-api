package com.I0Idigital.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shop")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "branch")
    private String branch;

    @Column(name = "opening_time")
    private String openingTime;

    @Column(name = "closing_time")
    private String closingTime;

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "shop_admin",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_id"))
    private List<User> admins;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShopDish> dishes;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShopQueue> queues;

    private transient User currentLoggedInUserAsAdmin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() { return address; }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public User getCurrentLoggedInUserAsAdmin() {
        return currentLoggedInUserAsAdmin;
    }

    public void setCurrentLoggedInUserAsAdmin(User currentLoggedInUserAsAdmin) {
        this.currentLoggedInUserAsAdmin = currentLoggedInUserAsAdmin;
    }

    public List<User> getAdmins() {
        return admins;
    }

    public List<ShopDish> getDishes() {
        return dishes;
    }

    public void setDishes(List<ShopDish> dishes) {
        this.dishes = dishes;
    }

    public List<ShopQueue> getQueues() {
        return queues;
    }

    public void setQueues(List<ShopQueue> queues) {
        this.queues = queues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Shop car = (Shop) o;
        if (car.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", branch='" + branch + "'" +
                ", address='" + address + "'" +
                '}';
    }
}
