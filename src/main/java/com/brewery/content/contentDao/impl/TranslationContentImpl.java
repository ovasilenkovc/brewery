package com.brewery.content.contentDao.impl;

import com.brewery.content.Content;
import com.brewery.content.article.Translations;
import com.brewery.content.contentDao.ContentDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Translations implementation of Content DAO.
 */
@Repository(value = "translationsDao")
public class TranslationContentImpl implements ContentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    //Don't need to implement
    public Long save(Content content) {
        return null;
    }

    @Override
    public void update(Content content) {
        sessionFactory.getCurrentSession().update(content);
    }

    @Override
    public Translations getOne(Long id) {
        return (Translations) sessionFactory.getCurrentSession().get(Translations.class, id);
    }

    @Override
    public List<Content> getAll() {
        return null;
    }

    @Override
    public boolean remove(Content content) {
        sessionFactory.getCurrentSession().delete(content);
        return true;
    }
}
