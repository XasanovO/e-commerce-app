package org.example.eewtihspringboot.controller;

import jakarta.servlet.ServletOutputStream;

import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.example.eewtihspringboot.dto.ProductReq;
import org.example.eewtihspringboot.entity.Product;
import org.example.eewtihspringboot.repo.CategoryRepo;
import org.example.eewtihspringboot.repo.ProductRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("categories", categoryRepo.findAll());
        return "products";
    }

    @GetMapping("/image")
    @ResponseBody
    public void getProductImage(@RequestParam Integer id, HttpServletResponse response) throws IOException {
        byte[] image = productRepo.findById(id).get().getImage();
        response.setContentType("image/jpeg");
        try (ServletOutputStream out = response.getOutputStream()) {
            out.write(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping
    public String addProduct(@ModelAttribute ProductReq productReq, @RequestParam MultipartFile image) throws IOException {
        productRepo.save(
                Product.builder()
                        .name(productReq.name())
                        .price(productReq.price())
                        .image(image.getBytes())
                        .category(categoryRepo.findById(productReq.categoryId()).get())
                        .build()
        );
        return "redirect:/products";
    }

    @GetMapping("/update")
    public String sendToUpdate(@RequestParam Integer id, Model model) {
        productRepo.findById(id).ifPresent(product -> {
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryRepo.findAll());
        });
        return "updateProduct";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute ProductReq productReq,
                                @RequestParam MultipartFile image,
                                @RequestParam Integer id
    ) throws IOException {
        Product product = productRepo.findById(id).get();

        productRepo.save(
                Product.builder()
                        .id(id)
                        .name(productReq.name())
                        .price(productReq.price())
                        .image(image.getBytes().length > 1 ? image.getBytes() : product.getImage())
                        .category(categoryRepo.findById(productReq.categoryId()).get())
                        .build()
        );

        return "redirect:/products";
    }


    @PostMapping("/delete")
    public String deleteProduct(@RequestParam Integer id) {
        productRepo.findById(id).ifPresent(productRepo::delete);
        return "redirect:/products";
    }
}
