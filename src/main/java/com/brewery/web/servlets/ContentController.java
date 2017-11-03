package com.brewery.web.servlets;

import com.brewery.content.Content;
import com.brewery.content.LocalizationTypes;
import com.brewery.content.article.Article;
import com.brewery.content.article.Translations;
import com.brewery.content.services.ContentServiceImpl;
import com.brewery.utils.ConstantParams;
import com.brewery.utils.ParamUtils;
import com.brewery.utils.ResponseMaker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller for Content managing.
 */
@Controller
public class ContentController {

    @Autowired
    private ContentServiceImpl contentService;

    private static final Logger LOGGER = Logger.getLogger(ContentController.class);

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/article", method = RequestMethod.POST)
    public ResponseEntity<String> addArticle(@RequestBody @Valid Article article) {
        LOGGER.info("Article saving process execution");
        Map<String, List<String>> error = contentService.checkLocalization(article);
        if(!error.isEmpty()){
            LOGGER.error("Content saving failed!");
            return ResponseMaker.makeResponse(error, ConstantParams.JSON_HEADER_TYPE, HttpStatus.BAD_REQUEST);
        }

        Long newId = contentService.save(article, ConstantParams.ARTICLE_CONTEXT);
        Map<String, Long> response = new HashMap<>();
        response.put("id", newId);
        return ResponseMaker.makeResponse(response, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/content/article", method = RequestMethod.GET)
    public ResponseEntity<String> getArticles() {
        LOGGER.info("Getting of all existed articles");
        List<Content> articles = contentService.getAll(ConstantParams.ARTICLE_CONTEXT);
        return ResponseMaker.makeResponse(articles, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/article/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getArticle(@PathVariable Long id) {
        LOGGER.info("Getting an article by specified id :" + id);
        Article article = (Article) contentService.getOne(id, ConstantParams.ARTICLE_CONTEXT);
        return ResponseMaker.makeResponse(article, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/article/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeArticle(@PathVariable Long id) throws IOException {
        LOGGER.info("Article removing process execution");
        contentService.remove(id, ConstantParams.ARTICLE_CONTEXT);
        String message = "Article with id: " + id + " removed successfully!";
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/article/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateArticle(@PathVariable Long id, @RequestBody @Valid Article article) {
        article.setArticle_id(id);
        LOGGER.info("Article updating process execution");
        Article articleForUpdate = (Article) contentService.getOne(id, ConstantParams.ARTICLE_CONTEXT);
        articleForUpdate.setTitle(article.getTitle());
        articleForUpdate.setDate(article.getDate());
        articleForUpdate.setTranslations(article.getTranslations());
        contentService.update(articleForUpdate, ConstantParams.ARTICLE_CONTEXT);
        String message = "Article has been updated successfully!";
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/article/{id}/translation", method = RequestMethod.POST)
    public ResponseEntity<String> addTranslation(@PathVariable Long id, @RequestBody @Valid Translations translation) {
        LOGGER.info("Article translation adding process execution");

        if(!ParamUtils.isLocalizationValid(translation.getType())){
            LOGGER.error("Content saving failed!");
            return ResponseMaker.makeResponse("Translation is invalid", ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
        }

        contentService.addTranslation(id, translation, ConstantParams.ARTICLE_CONTEXT);
        String message = "Article has been updated successfully!";
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/content/article/{articleId}/translation", method = RequestMethod.GET)
    public ResponseEntity<String> getTranslations(@PathVariable Long articleId) {
        LOGGER.info("Getting a translation by specified article id :" + articleId);
        Set<Translations> translations = contentService.getTranslations(articleId);
        return ResponseMaker.makeResponse(translations, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/content/local/types", method = RequestMethod.GET)
    public ResponseEntity<String> getAllAvailableRoles() {
        String[] typesArr = Arrays.toString(LocalizationTypes.values()).replaceAll("^.|.$", "").split(",");
        List<String> roles = new ArrayList<>();

        for (String local: typesArr){
            String trimmedLocalize = local.trim();
            roles.add(trimmedLocalize);
        }

        return ResponseMaker.makeResponse(roles, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/content/contacts", method = RequestMethod.GET)
    public ResponseEntity<String> getContacts() throws IOException {
        Map<String, String> cntacts = contentService.getContacts();
        return ResponseMaker.makeResponse(cntacts, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/admin/content/contacts", method = RequestMethod.POST)
    public ResponseEntity<String> saveContacts(@RequestBody Map<String, Object> reqMap) throws IOException {
        contentService.saveContacts(reqMap);
        return ResponseMaker.makeResponse("Contacts have been saved!", ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
