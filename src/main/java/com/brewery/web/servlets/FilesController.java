package com.brewery.web.servlets;

import com.brewery.content.File;
import com.brewery.content.services.ContentServiceImpl;
import com.brewery.utils.ConstantParams;
import com.brewery.utils.ResponseMaker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Controller for files managing
 */
@Controller
public class FilesController {

    @Autowired
    private ContentServiceImpl contentService;

    private static final Logger LOGGER = Logger.getLogger(FilesController.class);

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
    public ResponseEntity<String> removeImage(@PathVariable Long id) {
        LOGGER.info("Image removing process execution");
        contentService.remove(id, ConstantParams.IMAGE_CONTEXT);
        String message = "Image with id: " + id + " removed successfully!";
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/content/files/{path}", method = RequestMethod.GET)
    public ResponseEntity<String> getImages(@PathVariable String path) {
        LOGGER.info("Image getting process execution");
        String context = path.equals("pictures") ? ConstantParams.IMAGE_CONTEXT : ConstantParams.FILE_CONTEXT;
        Map<String, File> result = contentService.getBase64EncodedFiles(context);
        return ResponseMaker.makeResponse(result, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

}
