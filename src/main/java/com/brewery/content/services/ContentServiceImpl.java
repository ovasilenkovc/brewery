package com.brewery.content.services;

import com.brewery.content.Content;
import com.brewery.content.File;
import com.brewery.content.article.Article;
import com.brewery.content.article.Translations;
import com.brewery.content.contentDao.ContentDaoFactory;
import com.brewery.content.image.Image;
import com.brewery.services.fileFolder.FileFolderService;
import com.brewery.utils.ConstantParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service that provides to us managing all project's content data instances.
 * Such as Article, Translations, Product, Images, Types, etc.
 * It functions as a buffer between Web Container which is represented by Servlets
 * and data access objects for managing data on a database level.
 * <p>
 * It uses only one set of the methods for providing CRUD operations
 * for all data types that implements Content.java type.
 * To make this possible it uses factory class for switching between content DAO implementations.
 */
@Service(value = "contentService")
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentDaoFactory daoFactory;

    @Autowired
    private FileFolderService fileFolderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);

    /**
     * Transactional method for data base saving of Content items.
     * Chooses where items should be stored uses current context parameter.
     * For an example if context will be equals to "ARTICLE", factory returns ArticleDao implementation.
     *
     * @param content Content item for saving.
     * @param context current context.
     * @return Long id of created item.
     */
    @Transactional
    public Long save(Content content, String context) {
        LOGGER.error("content saving");
        return daoFactory.getContentDao(context).save(content);
    }

    /**
     * Method for getting all rows from the data base.
     * As a method above, it uses a current runtime context
     * to select where it needs to get the necessary data.
     *
     * @param context current context
     * @return List of Content instances.
     */
    public List<Content> getAll(String context) {
        LOGGER.error("getting all content instances");
        return daoFactory.getContentDao(context).getAll();
    }

    /**
     * Method for getting one row from the data base.
     * Uses id parameter for getting needed item.
     * Also as a method above, it uses a current runtime context
     * to select where it needs to get the necessary data.
     *
     * @param id      id of searched item.
     * @param context current context.
     * @return Content instance.
     */
    public Content getOne(Long id, String context) {
        LOGGER.error("getting one content instance");
        return daoFactory.getContentDao(context).getOne(id);
    }

    /**
     * Transactional method for removing item from the data base.
     * Chooses where from it should be removed uses current context parameter.
     *
     * @param id      id of removing item.
     * @param context current context.
     */
    @Transactional
    public void remove(Long id, String context) throws IOException {
        Content content = daoFactory.getContentDao(context).getOne(id);
        if (content == null) {
            LOGGER.info("Item with specified id: " + id + " was not found!");
            throw new NullPointerException("Item with specified id: " + id + " was not found!");
        }
        if (daoFactory.getContentDao(context).remove(content)
                && (ConstantParams.IMAGE_CONTEXT.equals(context) || ConstantParams.FILE_CONTEXT.equals(context))) {

            File file = (File) content;
            fileFolderService.removeFile(file.getName(), file.getPath());
        }
    }

    /**
     * Transactional method for updating specified item.
     *
     * @param content Content item for updating.
     * @param context current context.
     */
    @Transactional
    public void update(Content content, String context) {
        daoFactory.getContentDao(context).update(content);
    }

    /**
     * Method for getting all Translations for the specified Article id.
     * Uses an article id parameter for getting an Article.
     *
     * @param articleId article id
     * @return List of Content instances.
     */
    public Set<Translations> getTranslations(Long articleId) {
        Article article = (Article) daoFactory.getContentDao(ConstantParams.ARTICLE_CONTEXT).getOne(articleId);

        if (article == null) {
            String message = "Article with specified id: " + articleId + " was not found!";
            LOGGER.error(message);
            throw new NullPointerException(message);
        }
        return article.getTranslations();
    }

    /**
     * A transactional method for adding/update Translations for needed Article.
     * Uses an article id parameter for getting an Article
     * for which the translation should be saved.
     *
     * @param articleId article id
     */
    @Transactional()
    public void addTranslation(Long articleId, Translations translation, String context) {
        Article article = (Article) daoFactory.getContentDao(context).getOne(articleId);

        if (article == null) {
            String message = "Article with specified id: " + articleId + " was not found!";
            LOGGER.error(message);
            throw new NullPointerException(message);
        }

        Translations articleTranslation = getTranslationByType(article, translation.getType());
        if (articleTranslation != null) {
            articleTranslation.setTitle(translation.getTitle());
            articleTranslation.setTranslation(translation.getTranslation());
            article.getTranslations().add(articleTranslation);
        } else {
            article.getTranslations().add(translation);
        }
        daoFactory.getContentDao(context).update(article);
    }

    /**
     * A transactional method for saving files into DB and Store.
     * At first it saves new item information into the data base, if all operations was succeed
     * it would save file into physical store using specified path.
     * If not, it will switch to the next file iteration.
     *
     * @param files   list of files for saving.
     * @param context current context.
     * @param path    path for saving.
     * @return Map with information about saved files.
     */
    @Transactional
    public Map<Long, String> saveFiles(MultipartFile[] files, String context, String path) throws IOException {
        Map<Long, String> result = new HashMap<>();
        LOGGER.info("Files saving started");
        String storagePath = ConstantParams.ROOT_PROJECT_DIR + "/" + path;

        for (MultipartFile file : files) {
            if (!file.isEmpty() && fileFolderService.checkIsValidDataType(file.getContentType(), context)) {
                String fileOriginalName = file.getOriginalFilename();
                Content content = getFileContentInstance(fileOriginalName, storagePath, context);

                try {
                    saveFile(content, context, file, storagePath, result);
                } catch (Exception e) {
                    LOGGER.error("File with name: " + fileOriginalName + " saving failed");
                }
            }
        }
        return result;
    }

    @Transactional
    public void saveFile(Content content, String context, MultipartFile file, String storagePath, Map<Long, String> result) throws Exception {
        Long fileId = daoFactory.getContentDao(context).save(content);
        String savingResult = fileFolderService.saveFile(file, storagePath);
        if (savingResult != null) {
            result.put(fileId, savingResult);
        }
    }

    /**
     * A method for getting files from DB and Store.
     * Uses context for switching between stores (images/files)
     * Generate base64 encoding for all obtained files.
     *
     * @param context current context.
     * @return Map with files.
     */
    public Map<String, File> getBase64EncodedFiles(String context) throws IOException {
        Map<String, File> result = new HashMap<>();
        List<Content> files = daoFactory.getContentDao(context).getAll();

        for (Content content : files) {
            File file = (File) content;
            String absolutePath = ConstantParams.TEMP_FOLDER_PATH + file.getPath() + "/" + file.getName();
            String encoded64File = fileFolderService.getBase64StringEncoded(absolutePath);

            if (encoded64File != null) {
                file.setBase64encodeString(encoded64File);
                String key = "file id: " + file.getId();
                result.put(key, file);
            }
        }
        return result;
    }

    /**
     * A private method for getting Article translations.
     * Uses translation type i.e "ENG", "RUS", "UA" modifications for them.
     *
     * @param article Article for which you want to obtain translation.
     * @param type    translation language type.
     * @return Translation with specified type.
     */
    private Translations getTranslationByType(Article article, String type) {
        Set<Translations> translations = article.getTranslations();
        for (Translations translation : translations) {
            String translationType = translation.getType();
            if (translationType.equals(type)) return translation;
        }
        return null;
    }

    /**
     * The private method for making a new one Content instance by the current context.
     * @param fileName name field parameter.
     * @param path path field value
     * @param context current context
     * @return new Content instance.
     */
    private Content getFileContentInstance(String fileName, String path, String context) {
        Content content = null;
        switch (context) {
            case ConstantParams.IMAGE_CONTEXT:
                content = new Image(fileName, path);
                break;
            case ConstantParams.FILE_CONTEXT:
                //do something;
                break;
            default:
                content = new Image(fileName, path);
                break;
        }
        return content;
    }
}
