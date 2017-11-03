package com.brewery.content.contentDao;

import com.brewery.content.contentDao.impl.*;
import com.brewery.utils.ConstantParams;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * ContentDAO factory.
 * Switches between Content Dao implementations depending on context.
 */
@Component(value = "daoFactory")
public class ContentDaoFactory {

    @Autowired
    private ImageContentDaoImpl imageDao;

    @Autowired
    private ArticleContentDaoImpl articleDao;

    @Autowired
    private ProductContentDaoImpl productDao;

    @Autowired
    private ProductTypeDaoImpl productTypeDao;

    @Autowired
    private TranslationContentImpl translationContent;

    @Autowired
    private DescriptionContentDaoImpl descriptionContentDao;


    private static final Logger LOGGER = Logger.getLogger(ContentDaoFactory.class);

    private Map<String, AbstractContentDaoImpl> CONTENT_DAO_IMPLEMENTATIONS = new HashMap<>();

    @PostConstruct
    public void init(){
        CONTENT_DAO_IMPLEMENTATIONS.put(ConstantParams.IMAGE_CONTEXT, imageDao);
        CONTENT_DAO_IMPLEMENTATIONS.put(ConstantParams.PRODUCT_CONTEXT, productDao);
        CONTENT_DAO_IMPLEMENTATIONS.put(ConstantParams.ARTICLE_CONTEXT, articleDao);
        CONTENT_DAO_IMPLEMENTATIONS.put(ConstantParams.PRODUCT_TYPE_CONTEXT, productTypeDao);
        CONTENT_DAO_IMPLEMENTATIONS.put(ConstantParams.TRANSLATION_CONTEXT, translationContent);
        CONTENT_DAO_IMPLEMENTATIONS.put(ConstantParams.PRODUCT_DESCRIPTION, descriptionContentDao);
    }

    public ContentDao getContentDao(String context){

        AbstractContentDaoImpl contentDao = CONTENT_DAO_IMPLEMENTATIONS.get(context);

        if(contentDao == null){
            LOGGER.error("Content DAO implementation doesn't exist for specified context!");
            throw new NullPointerException("Content DAO implementation doesn't exist for specified context!");
        }

        return contentDao;
    }

}
