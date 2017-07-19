package com.brewery.content.contentDao.impl;

import com.brewery.content.Content;
import com.brewery.content.article.Article;
import com.brewery.content.contentDao.ContentDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Article implementation of Content DAO.
 */
@Repository(value = "articleDao")
public class ArticleContentDaoImpl implements ContentDao{

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
    public Article getOne(Long id) {
        Session session = sessionFactory.openSession();
        Article article = (Article) session.get(Article.class, id);

        if(article == null){
            throw new NullPointerException("Article with specified id: " + id + " was not found!");
        }

        article.getTranslations().size();
        session.flush();
        session.close();
        return article;
    }

    @Override
    public List<Content> getAll() {
        Session session = sessionFactory.openSession();
        List<Content> articles = (ArrayList<Content>) session.createCriteria(Article.class).list();

        for (Content article : articles) {
            ((Article)article).getTranslations().size();
        }

        session.flush();
        session.close();
        return articles;
    }

    @Override
    public boolean remove(Content content) {
        sessionFactory.getCurrentSession().delete(content);
        return true;
    }

}
