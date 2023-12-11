package com.es.core.entity.cart;

import com.es.core.entity.car.car;

public class CartItem {

    private car car;
    private Long quantity;

    public car getcar() {
        return car;
    }

    public void setcar(car car) {
        this.car = car;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
