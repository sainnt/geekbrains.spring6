package com.sainnt.homework6hibernate.repository;


import com.sainnt.homework6hibernate.entity.OrderPosition;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class OrderPositionRepository extends AbstractRepository<OrderPosition, Long> {

    public OrderPositionRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
