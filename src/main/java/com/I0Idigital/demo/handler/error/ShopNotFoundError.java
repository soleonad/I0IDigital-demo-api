package com.I0Idigital.demo.handler.error;

public class ShopNotFoundError extends NotFoundException {
    public ShopNotFoundError() {
        super("Shop not found");
    }

    public static ShopNotFoundError create() {
        return new ShopNotFoundError();
    }
}
