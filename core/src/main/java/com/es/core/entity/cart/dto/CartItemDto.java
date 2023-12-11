package com.es.core.entity.cart.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class CartItemDto {
    private Long carId;
    @NotNull(message = "Quantity was empty")
    @Min(value = 1, message = "Quantity must be more then 0")
    private Long quantity;

    public CartItemDto() {
    }

    public CartItemDto(Long carId, Long quantity) {
        this.carId = carId;
        this.quantity = quantity;
    }

    public Long getcarId() {
        return carId;
    }

    public void setcarId(Long carId) {
        this.carId = carId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
