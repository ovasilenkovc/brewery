package com.brewery.content.contentDao.impl;

import com.brewery.content.Content;
import com.brewery.content.contentDao.ContentDao;
import com.brewery.content.image.Image;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Image implementation of Content DAO.
 */
@Repository(value = "imageDao")
public class ImageContentDaoImpl implements ContentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long save(Content content) {
        Image image = (Image) content;
        if (isImgExist(image)) {
            throw new HibernateException("Image has already exist!");
        }
        return (Long) sessionFactory.getCurrentSession().save(image);
    }

    @Override
    //Don't need to implement
    public void update(Content content) {

    }

    @Override
    public Image getOne(Long id) {
        return (Image) sessionFactory.getCurrentSession().get(Image.class, id);

    }

    @Override
    public List<Content> getAll() {
        Session session = sessionFactory.openSession();
        List<Content> articles = session.createCriteria(Image.class).list();
        session.flush();
        session.close();
        return articles;
    }

    @Override
    public void remove(Content content) {
        try {
            sessionFactory.getCurrentSession().delete(content);
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    private boolean isImgExist(Image image) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT COUNT (*) FROM Image img WHERE img.name=:name AND img.path=:path");
        query.setString("name", image.getName());
        query.setString("path", image.getPath());
        Number count = (Number) query.uniqueResult();
        return count.longValue() > 0;
    }
}
