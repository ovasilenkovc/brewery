package com.brewery.content.contentDao.impl;

import com.brewery.content.Content;
import com.brewery.content.contentDao.ContentDao;
import com.brewery.content.product.ProductType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository(value = "productTypeDao")
public class ProductTypeDaoImpl implements ContentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Long save(Content content) {
        sessionFactory.getCurrentSession().save(content);
        return 0L;
    }

    @Override
    public void update(Content content) {
        sessionFactory.getCurrentSession().update(content);
    }

    @Override
    public Content getOne(Long id) {
        Session session = sessionFactory.openSession();
        ProductType type = (ProductType) session.get(ProductType.class, id);
        type.getProducts().size();
        session.flush();
        session.close();
        return type;
    }

    @Override
    public List<Content> getAll() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("SELECT t.typeName, t.iconPath FROM ProductType AS t");
        List<Content> result = new ArrayList<>();

        for (Object content : query.list()) {
            Object[] o = (Object[]) content;
            String[] str = new String[o.length];
            for (int i = 0; i < o.length; i++) {
                try {
                    str[i] = o[i].toString();
                } catch (NullPointerException ex) {
                    // do some default initilization
                }
            }
            result.add(new ProductType(str[0], str[1]));
        }

        session.flush();
        session.close();
        return result;
    }

    @Override
    public boolean remove(Content content) {
        sessionFactory.getCurrentSession().delete(content);
        return true;
    }
}
