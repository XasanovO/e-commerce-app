package org.example.eewtihspringboot.controller;


import lombok.RequiredArgsConstructor;
import org.example.eewtihspringboot.dto.CategoryReq;
import org.example.eewtihspringboot.entity.Category;
import org.example.eewtihspringboot.repo.CategoryRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepo categoryRepo;

    @PostMapping
    public String addCategory(@ModelAttribute CategoryReq categoryReq) {
        categoryRepo.save(
                Category.builder()
                        .name(categoryReq.name())
                        .build()
        );
        return "redirect:/categories";
    }

    @PostMapping("/delete")
    public String deleteCategory(@RequestParam Integer id) {
        categoryRepo.findById(id).ifPresent(categoryRepo::delete);
        return "redirect:/categories";
    }

    @GetMapping
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryRepo.findAll());
        return "categories";
    }

    @GetMapping("/update")
    public String sendToUpdate(@RequestParam Integer id, Model model) {
        categoryRepo.findById(id).ifPresent(category -> {
            model.addAttribute("category", category);
        });
        return "updateCategory";
    }

    @PostMapping("/update")
    public String updateCategory(@ModelAttribute CategoryReq categoryReq, @RequestParam Integer id) {
        Category category = new Category(id, categoryReq.name());
        categoryRepo.save(category);
        return "redirect:/categories";
    }

}
