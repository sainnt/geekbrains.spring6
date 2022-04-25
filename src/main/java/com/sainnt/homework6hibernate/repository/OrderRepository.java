package com.sainnt.homework6hibernate.repository;

import com.sainnt.homework6hibernate.entity.Order;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;



@Repository
public class OrderRepository extends AbstractRepository<Order, Long> {

    public OrderRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
