package org.example.eewtihspringboot.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasketProduct {
    private Product product;
    private Integer amount;
}
