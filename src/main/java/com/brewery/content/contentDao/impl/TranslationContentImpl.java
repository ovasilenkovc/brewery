package com.brewery.content.contentDao.impl;

import com.brewery.content.Content;
import com.brewery.content.article.Translations;
import com.brewery.content.contentDao.AbstractContentDaoImpl;
import com.brewery.content.contentDao.ContentDao;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Translations implementation of Content DAO.
 */
@Repository(value = "translationsDao")
public class TranslationContentImpl extends AbstractContentDaoImpl {

    @Override
    public Translations getOne(Long id) {
        return (Translations) sessionFactory.getCurrentSession().get(Translations.class, id);
    }

    @Override
    public List<Content> getAll() {
        return null;
    }
}
