package com.es.carshop.web.controller.pages;

import com.es.core.entity.cart.Cart;
import com.es.core.entity.cart.dto.CartItemDto;
import com.es.core.entity.cart.dto.CartItemsUpdateDto;
import com.es.core.service.CartService;
import com.es.core.entity.order.OutOfStockException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping(value = "/cart")
public class CartPageController {
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String getCart(@ModelAttribute("cartItemsQuantities") CartItemsUpdateDto dto) {
        dto.copyFromCart(cartService.getCart());
        return "cart";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateCart(@ModelAttribute("cartItemsQuantities") @Valid CartItemsUpdateDto dto,
                             BindingResult bindingResult, Model model) {
        List<String> validationErrors = new ArrayList<>(Collections.nCopies(dto.getItems().size(), null));
        ;
        List<String> outOfStockErrors = new ArrayList<>(Collections.nCopies(dto.getItems().size(), null));
        ;
        boolean hasErrors = false;
        int i = 0;
        if (bindingResult.hasErrors()) {
            for (CartItemDto cartItemDto : dto.getItems()) {
                if (bindingResult.hasFieldErrors("items[" + i + "].quantity")) {
                    validationErrors.set(i, bindingResult.getFieldError("items[" + i + "].quantity").getDefaultMessage());
                    hasErrors = true;
                }
                i++;
            }
        }
        i = 0;
        for (CartItemDto cartItem : dto.getItems()) {
            if (validationErrors.get(i) == null) {
                try {
                    cartService.update(cartItem.getcarId(), cartItem.getQuantity());
                } catch (OutOfStockException e) {
                    outOfStockErrors.set(i, "Out of stock error - " + e.getMessage());
                    hasErrors = true;
                }
            }
            i++;
        }
        dto.copyFromCart(cartService.getCart());
        model.addAttribute("validationErrors", validationErrors);
        model.addAttribute("outOfStockErrors", outOfStockErrors);

        if (hasErrors) {
            model.addAttribute("errorMessage", "There was some errors during updating");
        } else {
            model.addAttribute("successMessage", "Cart successfully updated");
        }

        return "cart";
    }


    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteFromCart(@RequestParam("carId") Long carId, Model model) {
        cartService.remove(carId);
        CartItemsUpdateDto dto = new CartItemsUpdateDto();
        dto.copyFromCart(cartService.getCart());
        model.addAttribute("cartItemsQuantities", dto);
        model.addAttribute("successMessage", "Successfully deleted from cart");
        return "cart";
    }

    @ModelAttribute("cart")
    public Cart cartAttribute() {
        return cartService.getCart();
    }
}
