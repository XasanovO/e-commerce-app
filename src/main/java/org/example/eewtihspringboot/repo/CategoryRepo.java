package org.example.eewtihspringboot.repo;

import org.example.eewtihspringboot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
