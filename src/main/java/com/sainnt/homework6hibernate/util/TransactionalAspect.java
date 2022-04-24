package com.sainnt.homework6hibernate.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionalAspect {
    private final SessionFactory sessionFactory;

    public TransactionalAspect(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Around("@annotation(CustomTransactional)")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        CustomTransactional annotation = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(CustomTransactional.class);
        TransactionPropagation propagation = annotation.propagation();
        openTransaction(propagation);
        Object proceed = joinPoint.proceed();
        commitTransaction();
        return proceed;
    }

    public void openTransaction(TransactionPropagation propagation){
        Session session = sessionFactory.getCurrentSession();
        if(!session.isOpen())
            session = sessionFactory.openSession();
        if(propagation == TransactionPropagation.REQUIRE_NEW)
            session.beginTransaction();
        else if(propagation == TransactionPropagation.REQUIRED && !session.getTransaction().isActive())
            session.beginTransaction();
    }

    public void commitTransaction(){
        Session session = sessionFactory.getCurrentSession();
        session.getTransaction().commit();
        session.close();
    }


}
