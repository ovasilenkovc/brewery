package com.brewery.content.services;

import com.brewery.content.Content;
import com.brewery.content.File;
import com.brewery.content.article.Article;
import com.brewery.content.article.Translations;
import com.brewery.content.contentDao.ContentDaoFactory;
import com.brewery.content.contentDao.impl.ProductTypeDaoImpl;
import com.brewery.content.image.Image;
import com.brewery.content.product.Description;
import com.brewery.content.product.Product;
import com.brewery.content.product.ProductType;
import com.brewery.exceptions.CustomRestException;
import com.brewery.services.fileFolder.FileFolderService;
import com.brewery.utils.ConstantParams;
import com.brewery.utils.ParamUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

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

    private static final Logger LOGGER = Logger.getLogger(ContentServiceImpl.class);

    private static final String CONTACT_FILE_NAME = "contacts.json";

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
        LOGGER.info("content saving");
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
        LOGGER.info("getting all content instances");
        return daoFactory.getContentDao(context).getAll();
    }

    public List<Content> getAllProducts(String context) {
        List<Content> products = new ArrayList<>();

        for(Object productObj: getAll(context)){
            Product product = (Product) productObj;
            ProductType type = product.getProductType();
            String base64iconRepresentation = getBase64ProductTypeIcon(type.getIconPath());

            if(base64iconRepresentation != null){
                type.setIconPath(base64iconRepresentation);
            }

            product.setProductType(type);
            products.add(product);
        }
        return products;
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
        LOGGER.info("getting one content instance");
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
    public void remove(Long id, String context) {
        Content content = daoFactory.getContentDao(context).getOne(id);
        daoFactory.getContentDao(context).remove(content);
        if (ConstantParams.IMAGE_CONTEXT.equals(context) || ConstantParams.FILE_CONTEXT.equals(context)) {
            File file = (File) content;
            fileFolderService.removeFile(file.getName(), file.getPath());
        }
    }

    /**
     * Transactional method for removing item from the data base.
     * Chooses where from it should be removed uses current context parameter.
     *
     * @param content removing item.
     * @param context current context.
     */
    @Transactional
    public void remove(Content content, String context) {
        daoFactory.getContentDao(context).remove(content);
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
     * A transactional method for adding/update Translations for needed Article.
     * Uses an article id parameter for getting an Article
     * for which the translation should be saved.
     *
     * @param productId   product id.
     * @param description Product Description instance.
     * @param context     current context.
     */
    @Transactional()
    public void saveUpdateDescription(Long productId, Description description, String context) {
        Product product = (Product) daoFactory.getContentDao(context).getOne(productId);

        Description prodDesc = getDescriptionByType(product, description.getType());
        if (prodDesc != null) {
            prodDesc.setTitle(description.getTitle());
            prodDesc.setDescription(description.getDescription());
            prodDesc.setComposition(description.getComposition());
            product.getDescriptions().add(prodDesc);
        } else {
            product.getDescriptions().add(description);
        }
        daoFactory.getContentDao(context).update(product);
    }

    @Transactional
    public void saveProductType(MultipartFile icon, String typeName, String context) throws Exception {
        String path = "/icons";
        String fileName = icon.getOriginalFilename();
        String iconPath = "/icons/" + fileName;
        try {
            String savingResult = fileFolderService.saveFile(icon, path);
            if (!"".equals(savingResult) && savingResult != null) {
                Content productType = new ProductType(typeName, iconPath);
                daoFactory.getContentDao(context).save(productType);
            }
        } catch (Exception e) {
            throw new Exception(e.getCause().getMessage(), e);
        }
    }

    @Transactional
    public void removeProductType(String typeName, String context){

        ProductTypeDaoImpl productTypeDao = (ProductTypeDaoImpl) daoFactory.getContentDao(context);
        ProductType productType = productTypeDao.getOneType(typeName);
        Set<Product> products = productType.getProducts();

        if(!products.isEmpty()){
            StringBuilder message = new StringBuilder("You can't remove this product type because you have products: ");
            for (Product product: products){
                String prodName = product.getName();
                message.append(prodName).append(", ");
            }
            message.append("that related to type ").append(productType.getTypeName());
            throw new CustomRestException(message.toString());
        }

        productTypeDao.remove(productType);
    }

    public List<Content> getAllProductTypes(String context) {
        List<Content> storedTypes = new ArrayList<>();
        for(Object content: daoFactory.getContentDao(context).getAll()){
            ProductType type = (ProductType) content;
            type.setIconPath(getBase64ProductTypeIcon(type.getIconPath()));
            storedTypes.add(type);
        }
        return storedTypes;
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
    public Map<Long, String> saveFiles(MultipartFile[] files, String context, String path) {
        Map<Long, String> result = new HashMap<>();
        LOGGER.info("Files saving started");
        String storagePath = "/" + path;

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

    /**
     * A transactional method for saving file into DB and Store.
     * At first it saves new item information into the data base, if all operations was succeed
     * it would save file into physical store using specified path.
     *
     * @param content     File Content representation.
     * @param context     current context.
     * @param file        Physical File for saving.
     * @param storagePath path for storage where file should be saved.
     * @param result      result map that will be filled.
     */
    @Transactional
    private void saveFile(Content content, String context, MultipartFile file, String storagePath, Map<Long, String> result){
        String savingResult = fileFolderService.saveFile(file, storagePath);
        Long fileId = daoFactory.getContentDao(context).save(content);
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
    public Map<String, File> getBase64EncodedFiles(String context) {
        Map<String, File> result = new HashMap<>();
        List<Content> files = daoFactory.getContentDao(context).getAll();

        for (Content content : files) {
            File file = (File) content;
            String absolutePath = ConstantParams.TEMP_FOLDER_PATH + ConstantParams.ROOT_PROJECT_DIR + file.getPath() + "/" + file.getName();
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
     * The method for removing Content metadata (ie: Article_Translations, Product_Description).
     * For current specified localization.
     *
     * @param id           Content id for which we want to remove;
     * @param localizeType Metadata localization;
     * @param context      current context.
     */
    @Transactional
    public void removeContentMetadata(Long id, String localizeType, String context) {
        Content content = daoFactory.getContentDao(context).getOne(id);
        removeMetadata(content, localizeType, context);
    }

    public Map<String, String> getContacts() throws IOException {
        return fileFolderService.getJSonFileContent(CONTACT_FILE_NAME);
    }

    public void saveContacts(Map<String, Object> contactsProps) throws IOException {
        fileFolderService.updateJsonFile(contactsProps, CONTACT_FILE_NAME);
    }

    private void removeMetadata(Content content, String local, String context) {
        if (ConstantParams.ARTICLE_CONTEXT.equals(context)) {
            Translations translation = getTranslationByType((Article) content, local);
            daoFactory.getContentDao(ConstantParams.TRANSLATION_CONTEXT).remove(translation);
        } else if (ConstantParams.PRODUCT_CONTEXT.equals(context)) {
            Description description = getDescriptionByType((Product) content, local);
            daoFactory.getContentDao(ConstantParams.PRODUCT_DESCRIPTION).remove(description);
        }
    }

    public Map<String, List<String>> checkLocalization(Content content) {
        Map<String, List<String>> response = new HashMap<>();
        String message = "Content translations with next titles are not valid!";
        if (content instanceof Product) {
            List<String> invalidTranslations = checkDescriptionsLocalization((Product) content);
            if (!invalidTranslations.isEmpty()) {
                response.put(message, invalidTranslations);
            }
        } else if (content instanceof Article) {
            List<String> invalidTranslations = checkTranslationsLocalization((Article) content);
            if (!invalidTranslations.isEmpty()) {
                response.put(message, invalidTranslations);
            }
        }
        return response;
    }

    private List<String> checkDescriptionsLocalization(Product product) {
        List<String> errors = new ArrayList<>();
        Set<Description> descriptions = product.getDescriptions();
        for (Description description : descriptions) {
            String localizeType = description.getType();
            if (!ParamUtils.isLocalizationValid(localizeType)) {
                String error = "TITLE: " + description.getTitle() + "; TYPE: " + description.getType();
                errors.add(error);
            }
        }
        return errors;
    }

    private List<String> checkTranslationsLocalization(Article article) {
        List<String> errors = new ArrayList<>();
        Set<Translations> translations = article.getTranslations();
        for (Translations translation : translations) {
            String localizeType = translation.getType();
            if (!ParamUtils.isLocalizationValid(localizeType)) {
                String error = "TITLE: " + translation.getTitle() + "; TYPE: " + translation.getType();
                errors.add(error);
            }
        }
        return errors;
    }

    /**
     * The private method for getting Article translations.
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
     * The private method for getting Product descriptions.
     * Uses translation type i.e "ENG", "RUS", "UA" modifications for them.
     *
     * @param product Product for which you want to obtain translation.
     * @param type    translation language type.
     * @return Translation with specified type.
     */
    private Description getDescriptionByType(Product product, String type) {
        Set<Description> descriptions = product.getDescriptions();
        for (Description description : descriptions) {
            String translationType = description.getType();
            if (translationType.equals(type)) return description;
        }
        return null;
    }

    /**
     * The private method for making a new one Content instance by the current context.
     *
     * @param fileName name field parameter.
     * @param path     path field value
     * @param context  current context
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

    private String getBase64ProductTypeIcon(String iconPath) {
        String absolutePath = ConstantParams.TEMP_FOLDER_PATH + ConstantParams.ROOT_PROJECT_DIR + iconPath;
        return fileFolderService.getBase64StringEncoded(absolutePath);
    }
}
