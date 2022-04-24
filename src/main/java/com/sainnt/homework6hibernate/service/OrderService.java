package com.sainnt.homework6hibernate.service;

import com.sainnt.homework6hibernate.dto.OrderDto;
import com.sainnt.homework6hibernate.dto.OrderPositionDto;
import com.sainnt.homework6hibernate.dto.ProductWithAmountDto;
import com.sainnt.homework6hibernate.entity.Order;
import com.sainnt.homework6hibernate.entity.OrderPosition;
import com.sainnt.homework6hibernate.entity.Product;
import com.sainnt.homework6hibernate.entity.User;
import com.sainnt.homework6hibernate.exception.NotFoundException;
import com.sainnt.homework6hibernate.repository.OrderPositionRepository;
import com.sainnt.homework6hibernate.repository.OrderRepository;
import com.sainnt.homework6hibernate.repository.ProductRepository;
import com.sainnt.homework6hibernate.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderPositionRepository orderPositionRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    public List<OrderDto> listOrders() {
        List<Order> all = orderRepository.findAll();
        return all.stream().map(this::mapToDto).toList();
    }

    @Transactional
    public OrderDto createOrder(long userId, Collection<ProductWithAmountDto> orderItems) {
        User user = userRepository.load(userId);
        if(user == null)
            throw new NotFoundException(String.format("User with id %d not found",userId));
        Order order = new Order();
        order.setUser(user);
        order.setDate(new Date());

        order.setOrderPositions(orderItems.stream().map(
                item -> {
                    Product product = productRepository.load(item.getProductId());
                    OrderPosition position = new OrderPosition();
                    position.setAmount(item.getAmount());
                    position.setProduct(product);
                    position.setProductPrice(product.getPrice());
                    position.setOrder(order);
                    return position;
                }
        ).toList());
        orderRepository.save(order);
        return mapToDto(order);
    }


    private OrderDto mapToDto(Order order) {
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

    private OrderPositionDto mapToDto(OrderPosition orderPosition) {
        OrderPositionDto dto = new OrderPositionDto();
        dto.setId(orderPosition.getId());
        dto.setProductId(orderPosition.getProduct().getId());
        dto.setAmount(orderPosition.getAmount());
        dto.setProductPrice(orderPosition.getProductPrice());
        return dto;
    }
}
