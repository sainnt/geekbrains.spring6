package com.sainnt.homework6hibernate.repository;

import com.sainnt.homework6hibernate.entity.Product;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends AbstractRepository<Product,Long>{

    public ProductRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
