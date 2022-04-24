package com.sainnt.homework6hibernate.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<OrderPosition> orderPositions;
    @Column(nullable = false)
    private Date date;

    public Long getOrderPrice() {
        return orderPositions.stream()
                .map(order -> order.getProductPrice() * order.getAmount())
                .reduce(0L, Long::sum);
    }
}
