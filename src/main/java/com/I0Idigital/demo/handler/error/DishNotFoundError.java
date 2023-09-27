package com.I0Idigital.demo.handler.error;

public class DishNotFoundError extends NotFoundException {
    public DishNotFoundError() {
        super("Dish not found");
    }

    public static DishNotFoundError create() {
        return new DishNotFoundError();
    }
}
