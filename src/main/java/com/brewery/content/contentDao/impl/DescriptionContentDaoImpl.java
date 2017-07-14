package com.brewery.content.contentDao.impl;

import com.brewery.content.Content;
import com.brewery.content.contentDao.ContentDao;
import com.brewery.content.product.Description;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "descriptionDao")
public class DescriptionContentDaoImpl implements ContentDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long save(Content content) {
        return (Long) sessionFactory.getCurrentSession().save(content);
    }

    @Override
    public void update(Content content) {
        sessionFactory.getCurrentSession().update(content);
    }

    @Override
    public Content getOne(Long id) {
        return (Content) sessionFactory.getCurrentSession().get(Description.class, id);    }

    @Override
    public List<Content> getAll() {
        return sessionFactory.getCurrentSession().createCriteria(Description.class).list();
    }

    @Override
    public boolean remove(Content content) {
        sessionFactory.getCurrentSession().delete(content);
        return true;
    }
}
