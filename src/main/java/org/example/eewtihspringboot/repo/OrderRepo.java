package org.example.eewtihspringboot.repo;

import org.example.eewtihspringboot.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {
}
