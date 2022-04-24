package com.sainnt.homework6hibernate.dto;

import lombok.Data;

@Data
public class OrderPositionDto {
    private Long id;
    private Long productId;
    private int amount;
    private Long productPrice;
}
