package com.I0Idigital.demo.payload.request;

import java.util.List;

public class OrderRequest {
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {
        private long shopDishId;
        private int quantity;
        public long getShopDishId() {
            return shopDishId;
        }

        public void setShopDishId(long shopDishId) {
            this.shopDishId = shopDishId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
