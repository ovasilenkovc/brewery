package com.brewery.content.contentDao.impl;

import com.brewery.content.Content;
import com.brewery.content.contentDao.ContentDao;
import com.brewery.content.product.Product;
import com.brewery.content.product.ProductType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository(value = "productDao")
public class ProductContentDaoImpl implements ContentDao {

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
        Session session = sessionFactory.openSession();
        Product product = (Product) session.get(Product.class, id);

        if (product == null) {
            throw new NullPointerException("Product with specified id " + id + "doesn't exist!");
        }

        product.setProductType(initProductType(product));
        product.getDescriptions().size();
        session.flush();
        session.close();
        return product;
    }

    @Override
    public List<Content> getAll() {
        Session session = sessionFactory.openSession();
        List<Content> products = (ArrayList<Content>) session.createCriteria(Product.class).list();

        for (Content product : products) {
            Product processing = (Product) product;
            processing.setProductType(initProductType(processing));
            ((Product) product).getDescriptions().size();
        }
        session.flush();
        session.close();
        return products;
    }

    @Override
    public boolean remove(Content content) {
        sessionFactory.getCurrentSession().delete(content);
        return true;
    }

    private ProductType initProductType(Product product) {
        String typeName = product.getProductType().getTypeName();
        String typePath = product.getProductType().getIconPath();
        return new ProductType(typeName, typePath);
    }
}
