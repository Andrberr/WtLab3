package com.es.carshop.web.controller.pages;

import com.es.core.dao.CarDao;
import com.es.core.entity.cart.Cart;
import com.es.core.service.CartService;
import com.es.core.entity.car.sort.SortField;
import com.es.core.entity.car.sort.SortOrder;
import com.es.core.entity.car.car;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/productList")
public class ProductListPageController {
    @Resource
    private CarDao carDao;
    @Resource
    private CartService cartService;

    private static final int carS_ON_PAGE = 10;

    @GetMapping
    public String showProductList(@RequestParam(name = "page", required = false) Integer pageNumber,
                                  @RequestParam(name = "sort", required = false) String sortField,
                                  @RequestParam(name = "order", required = false) String sortOrder,
                                  @RequestParam(name = "query", required = false) String query,
                                  Model model) {
        List<car> cars = carDao.findAll(((pageNumber == null ? 1 : pageNumber) - 1) * carS_ON_PAGE, carS_ON_PAGE,
                SortField.getValue(sortField), SortOrder.getValue(sortOrder), query);
        model.addAttribute("cars", cars);
        Long number = carDao.numberByQuery(query);
        model.addAttribute("numberOfcars", number);
        model.addAttribute("numberOfPages", (number + carS_ON_PAGE - 1) / carS_ON_PAGE);
        return "productList";
    }

    @ModelAttribute("cart")
    public Cart cartOnPage() {
        return cartService.getCart();
    }
}
