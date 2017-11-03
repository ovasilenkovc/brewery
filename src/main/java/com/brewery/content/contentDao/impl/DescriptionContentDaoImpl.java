package com.brewery.content.contentDao.impl;

import com.brewery.content.Content;
import com.brewery.content.contentDao.AbstractContentDaoImpl;
import com.brewery.content.product.Description;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "descriptionDao")
public class DescriptionContentDaoImpl extends AbstractContentDaoImpl {

    @Override
    public Content getOne(Long id) {
        return (Content) sessionFactory.getCurrentSession().get(Description.class, id);
    }

    @Override
    public List<Content> getAll() {
        return sessionFactory.getCurrentSession().createCriteria(Description.class).list();
    }

}
