package com.brewery.content.contentDao;

import com.brewery.content.contentDao.impl.ArticleContentDaoImpl;
import com.brewery.content.contentDao.impl.ImageContentDaoImpl;
import com.brewery.content.contentDao.impl.TranslationContentImpl;
import com.brewery.utils.ConstantParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private TranslationContentImpl translationContent;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentDaoFactory.class);

    private Map<String, ContentDao> CONTENT_DAO_IMPLEMENTATIONS = new HashMap<>();

    @PostConstruct
    public void init(){
        CONTENT_DAO_IMPLEMENTATIONS.put(ConstantParams.IMAGE_CONTEXT, imageDao);
        CONTENT_DAO_IMPLEMENTATIONS.put(ConstantParams.ARTICLE_CONTEXT, articleDao);
        CONTENT_DAO_IMPLEMENTATIONS.put(ConstantParams.TRANSLATION_CONTEXT, translationContent);
    }

    public ContentDao getContentDao(String context){

        ContentDao contentDao = CONTENT_DAO_IMPLEMENTATIONS.get(context);

        if(contentDao == null){
            LOGGER.error("Content DAO implementation doesn't exist for specified context!");
            throw new NullPointerException("Content DAO implementation doesn't exist for specified context!");
        }

        return contentDao;
    }

}
