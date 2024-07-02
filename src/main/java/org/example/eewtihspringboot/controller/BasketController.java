package org.example.eewtihspringboot.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.eewtihspringboot.entity.Basket;
import org.example.eewtihspringboot.entity.BasketProduct;
import org.example.eewtihspringboot.repo.ProductRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;


@Controller
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final ProductRepo productRepo;


    @GetMapping
    public String getBasket(Model model, HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        if (basket == null) {
            return "redirect:/";
        } else {
            int sum = basket.basketProducts
                    .stream()
                    .mapToInt(item -> item.getProduct().getPrice() * item.getAmount())
                    .sum();
            model.addAttribute("totalSum", sum);
            model.addAttribute("basketProducts", basket.basketProducts);
        }
        return "basket";
    }


    @PostMapping("/add")
    public String addBasket(@RequestParam int productId, Model model, HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        if (basket != null) {
            basket.basketProducts.add(
                    new BasketProduct(productRepo.findById(productId).get(), 1)
            );
            session.setAttribute("basket", basket);
        } else {
            Basket basket1 = new Basket();
            basket1.basketProducts.add(
                    new BasketProduct(productRepo.findById(productId).get(), 1)
            );
            session.setAttribute("basket", basket1);
        }
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteBasket(@RequestParam int productId, HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        deleteFromBasket(basket, productId);
        session.setAttribute("basket", basket);
        return "redirect:/";
    }

    private void deleteFromBasket(Basket basket, int productId) {
        basket.basketProducts = new ArrayList<>(basket.basketProducts
                .stream()
                .filter(item -> !item.getProduct().getId().equals(productId))
                .toList());
    }


    @PostMapping("/amount")
    public String amount(@RequestParam int productId, HttpSession session, @RequestParam String operation) {
        Basket basket = (Basket) session.getAttribute("basket");
        if (basket != null) {
            for (BasketProduct basketProduct : basket.basketProducts) {
                if (basketProduct.getProduct().getId().equals(productId)) {
                    if (operation.equals("++")) {
                        basketProduct.setAmount(basketProduct.getAmount() + 1);
                        session.setAttribute("basket", basket);
                    } else {
                        if (basketProduct.getAmount() == 1) {
                            deleteFromBasket(basket, productId);
                        }
                        basketProduct.setAmount(basketProduct.getAmount() - 1);
                        session.setAttribute("basket", basket);
                    }
                    if (basket.basketProducts.isEmpty()) {
                        return "redirect:/";
                    } else {
                        return "redirect:/basket";
                    }
                }
            }
        }
        return "redirect:/";
    }
}
