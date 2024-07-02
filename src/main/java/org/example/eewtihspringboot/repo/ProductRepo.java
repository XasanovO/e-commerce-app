package org.example.eewtihspringboot.repo;

import org.example.eewtihspringboot.entity.Category;
import org.example.eewtihspringboot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    public List<Product> findAllByCategoryId(int categoryId);
}
