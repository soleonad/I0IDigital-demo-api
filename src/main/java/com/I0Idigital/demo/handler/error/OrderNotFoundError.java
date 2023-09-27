package com.I0Idigital.demo.handler.error;

public class OrderNotFoundError extends NotFoundException {
    public OrderNotFoundError() {
        super("Order not found");
    }

    public static OrderNotFoundError create() {
        return new OrderNotFoundError();
    }
}
