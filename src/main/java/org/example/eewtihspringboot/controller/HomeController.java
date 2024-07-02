package org.example.eewtihspringboot.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.eewtihspringboot.entity.Basket;
import org.example.eewtihspringboot.entity.BasketProduct;
import org.example.eewtihspringboot.entity.Category;
import org.example.eewtihspringboot.entity.Product;
import org.example.eewtihspringboot.repo.CategoryRepo;
import org.example.eewtihspringboot.repo.ProductRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;

    @GetMapping("/")
    public String home(@RequestParam(required = false) Integer categoryId, Model model, HttpSession session) {
        List<Category> categories = categoryRepo.findAll();
        List<Product> products = productRepo.findAll();
        model.addAttribute("categories", categories);
        Basket basket = (Basket) session.getAttribute("basket");
        if (categoryId != null) {
            products = productRepo.findAllByCategoryId(categoryId);
        }

        for (Product product : products) {
            if (hasInBasket(product.getId(), session)) {
                product.setHasInBasket(true);
            }
        }

        model.addAttribute("products", products);
        model.addAttribute("basketSize", basket != null ? basket.basketProducts.size() : 0);
        return "home";
    }

    private boolean hasInBasket(Integer productId, HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        if (basket == null) {
            return false;
        } else {
            for (BasketProduct basketProduct : basket.basketProducts) {
                if (basketProduct.getProduct().getId().equals(productId)) {
                    return true;
                }
            }
        }
        return false;
    }
}
