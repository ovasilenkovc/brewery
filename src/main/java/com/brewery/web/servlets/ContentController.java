package com.brewery.web.servlets;

import com.brewery.content.Content;
import com.brewery.content.File;
import com.brewery.content.article.Article;
import com.brewery.content.article.Translations;
import com.brewery.content.services.ContentServiceImpl;
import com.brewery.utils.ConstantParams;
import com.brewery.utils.ResponseMaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller for Content managing.
 *
 */
@Controller
public class ContentController {

    @Autowired
    private ContentServiceImpl contentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/article", method = RequestMethod.POST)
    public ResponseEntity<String> addArticle(@RequestBody Article article) {
        LOGGER.info("Article saving process execution");
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
    public ResponseEntity<String> updateArticle(@PathVariable Long id, @RequestBody Article article) {
        article.setArticle_id(id);
        LOGGER.info("Article updating process execution");
        contentService.update(article, ConstantParams.ARTICLE_CONTEXT);
        String message = "Article has been updated successfully!";
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/article/{id}/translation", method = RequestMethod.POST)
    public ResponseEntity<String> addTranslation(@PathVariable Long id, @RequestBody Translations translation) {
        LOGGER.info("Article translation adding process execution");
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/files/{path}", method = RequestMethod.POST)
    public ResponseEntity<String> imagesSave(@PathVariable String path,
                                             @RequestParam(value = "files") MultipartFile[] files) throws Exception {

        LOGGER.info("Image saving process execution");
        Map<Long, String> result = contentService.saveFiles(files, ConstantParams.IMAGE_CONTEXT, path);
        return ResponseMaker.makeResponse(result, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/files/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeImage(@PathVariable Long id) throws IOException {
        LOGGER.info("Image removing process execution");
        contentService.remove(id, ConstantParams.IMAGE_CONTEXT);
        String message = "Image with id: " + id + " removed successfully!";
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/content/files/{path}", method = RequestMethod.GET)
    public ResponseEntity<String> getImages(@PathVariable String path) throws IOException {
        LOGGER.info("Image getting process execution");
        String context = path.equals("pictures") ? ConstantParams.IMAGE_CONTEXT : ConstantParams.FILE_CONTEXT;
        Map<String, File> result = contentService.getBase64EncodedFiles(context);
        return ResponseMaker.makeResponse(result, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
