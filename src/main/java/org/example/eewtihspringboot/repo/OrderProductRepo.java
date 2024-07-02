package org.example.eewtihspringboot.repo;


import org.example.eewtihspringboot.entity.Order;
import org.example.eewtihspringboot.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepo extends JpaRepository<OrderProduct, Integer> {
    public List<OrderProduct> findAllByOrder(Order order);
}
