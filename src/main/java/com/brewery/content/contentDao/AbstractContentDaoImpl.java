package com.brewery.content.contentDao;

import com.brewery.content.Content;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "contentDao")
public abstract class AbstractContentDaoImpl implements ContentDao {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public abstract Content getOne(Long id);

    @Override
    public abstract List getAll();

    @Override
    public Long save(Content content) {
        return (Long) sessionFactory.getCurrentSession().save(content);
    }

    @Override
    public void update(Content content) {
        sessionFactory.getCurrentSession().update(content);
    }

    @Override
    public void remove(Content content) {
        try {
            sessionFactory.getCurrentSession().delete(content);
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }
}
