package org.example.eewtihspringboot.controller;


import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.eewtihspringboot.entity.*;
import org.example.eewtihspringboot.repo.OrderProductRepo;
import org.example.eewtihspringboot.repo.OrderRepo;
import org.example.eewtihspringboot.repo.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProductRepo orderProductRepo;
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;

    @GetMapping
    public String order(Model model) {
        model.addAttribute("orders", orderRepo.findAll());
        return "order";
    }

    @GetMapping("/orderProducts")
    public String orderProduct(Model model, @RequestParam Integer orderId) {
        Order order = orderRepo.findById(orderId).get();
        List<OrderProduct> orderProducts = orderProductRepo.findAllByOrder(order);
        model.addAttribute("orderProducts", orderProducts);
        return "orderProduct";
    }


    @Transactional
    @PostMapping("/make")
    public String makeOrder(HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        if (basket != null) {
            User admin = userRepo.findByUsername("admin").get();
            Order order = Order.builder()
                    .user(admin)
                    .build();
            Order savedOrder = orderRepo.save(order);
            for (BasketProduct basketProduct : basket.basketProducts) {
                OrderProduct orderProduct = OrderProduct
                        .builder()
                        .product(basketProduct.getProduct())
                        .amount(basketProduct.getAmount())
                        .order(savedOrder)
                        .build();
                orderProductRepo.save(orderProduct);
            }
            session.removeAttribute("basket");
        }
        return "redirect:/";
    }


}
