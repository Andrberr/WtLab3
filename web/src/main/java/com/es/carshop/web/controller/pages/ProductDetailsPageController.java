package com.es.carshop.web.controller.pages;

import com.es.core.dao.CarDao;
import com.es.core.entity.cart.Cart;
import com.es.core.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    @Resource
    private CarDao carDao;
    @Resource
    private CartService cartService;

    @GetMapping("/{id}")
    public String showProduct(@PathVariable("id") Long carId, Model model) {
        model.addAttribute("car", carDao.get(carId).orElse(null));
        return "productPage";
    }

    @ModelAttribute("cart")
    public Cart cartOnPage() {
        return cartService.getCart();
    }
}
