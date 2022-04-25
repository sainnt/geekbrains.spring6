package com.sainnt.homework6hibernate.repository;

import com.sainnt.homework6hibernate.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractRepository<User,Long>{
    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
