package com.es.core.entity.cart.dto;

import com.es.core.entity.cart.Cart;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class CartItemsUpdateDto {
    @Valid
    private List<CartItemDto> items;

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public void copyFromCart(Cart cart) {
        items = new ArrayList<CartItemDto>();
        cart.getItems().stream()
                .forEach(cartItem -> items.add(new CartItemDto(cartItem.getcar().getId(), cartItem.getQuantity())));
    }
}
