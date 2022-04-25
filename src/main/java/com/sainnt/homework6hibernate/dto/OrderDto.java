package com.sainnt.homework6hibernate.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private Long totalPrice;
    private List<OrderPositionDto> positionList;
}
