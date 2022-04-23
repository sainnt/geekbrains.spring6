package com.sainnt.homework6hibernate.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class OrderPosition {
    @Id
    @GeneratedValue
    private long id;
    @OneToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private long productPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPosition that = (OrderPosition) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
