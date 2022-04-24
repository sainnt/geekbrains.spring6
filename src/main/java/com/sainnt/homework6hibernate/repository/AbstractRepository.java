package com.sainnt.homework6hibernate.repository;

import com.sainnt.homework6hibernate.entity.Order;
import com.sainnt.homework6hibernate.exception.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractRepository <E, ID extends Serializable> {
    private final Class<E> entityClass;
    private final String idPropertyName;
    private final SessionFactory sessionFactory;

    public AbstractRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
        Class<ID> idClass = (Class<ID>) genericSuperclass.getActualTypeArguments()[1];
        this.idPropertyName = sessionFactory.getMetamodel().entity(this.entityClass).getId(idClass).getName();
    }

    protected void performTransactional(Consumer<Session> daoOperation){
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.getTransaction();
        boolean noActiveTransaction = !transaction.isActive();
        if(noActiveTransaction)
            transaction = currentSession.beginTransaction();
        try{
            daoOperation.accept(currentSession);
        }
        catch (Exception e)
        {
            transaction.rollback();
            throw new DaoException(e);
        }
        if(noActiveTransaction)
            transaction.commit();
    }

    protected  <T> T  performTransactional(Function<Session,T> daoOperation){
        Session currentSession = sessionFactory.getCurrentSession();
        Transaction transaction = currentSession.getTransaction();
        boolean noActiveTransaction = !transaction.isActive();
        if(noActiveTransaction)
            transaction = currentSession.beginTransaction();
        try{
            T apply = daoOperation.apply(currentSession);
            if(noActiveTransaction)
                transaction.commit();
            return apply;
        }
        catch (Exception e)
        {
            transaction.rollback();
            throw new DaoException(e);
        }

    }

    public E getById(ID id){
        return performTransactional(
                session -> {
                    return session.get(entityClass, id);
                }
        );
    }
    public List<E> findAll() {
        return performTransactional(session -> {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<E> query = cb.createQuery(entityClass);
            Root<E> root = query.from(entityClass);
            query.select(root);
            return session.createQuery(query).list();
        });
    }

    public void persist(E entity) {
        performTransactional(session -> {
            session.persist(entity);
        });
    }

    public boolean delete(ID id) {
        return
                performTransactional(session ->
                {
                    CriteriaBuilder cb =  session.getCriteriaBuilder();
                    CriteriaDelete<E> query = cb.createCriteriaDelete(entityClass);
                    Root<E> root = query.from(entityClass);
                    query.where(cb.equal(root.get(idPropertyName),id));
                    return session.createQuery(query).executeUpdate() > 0;
                });

    }
}
