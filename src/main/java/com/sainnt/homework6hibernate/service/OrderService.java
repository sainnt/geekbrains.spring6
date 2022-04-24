package com.sainnt.homework6hibernate.service;

import com.sainnt.homework6hibernate.dto.OrderDto;
import com.sainnt.homework6hibernate.dto.OrderPositionDto;
import com.sainnt.homework6hibernate.dto.ProductWithAmountDto;
import com.sainnt.homework6hibernate.entity.Order;
import com.sainnt.homework6hibernate.entity.OrderPosition;
import com.sainnt.homework6hibernate.repository.OrderRepository;
import com.sainnt.homework6hibernate.util.CustomTransactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public List<OrderDto> listOrders(){
        List<Order> all = orderRepository.findAll();
        return all.stream().map(this::mapToDto).toList();
    }

    private OrderDto mapToDto(Order order){
        OrderDto dto = new OrderDto();
        dto.setUserId(order.getUser().getId());
        dto.setId(order.getId());
        dto.setTotalPrice(order.getOrderPrice());
        dto.setPositionList(
                order
                        .getOrderPositions()
                        .stream().map(this::mapToDto)
                        .toList()
        );
        return dto;
    }

    private OrderPositionDto mapToDto(OrderPosition orderPosition){
        OrderPositionDto dto = new OrderPositionDto();
        dto.setId(orderPosition.getId());
        dto.setProductId(orderPosition.getProduct().getId());
        dto.setAmount(orderPosition.getAmount());
        dto.setProductPrice(orderPosition.getProductPrice());
        return dto;
    }
}
