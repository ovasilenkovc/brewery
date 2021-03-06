package com.brewery.web.servlets;

import com.brewery.content.Content;
import com.brewery.content.product.Description;
import com.brewery.content.product.Product;
import com.brewery.content.product.ProductType;
import com.brewery.content.services.ContentServiceImpl;
import com.brewery.utils.ConstantParams;
import com.brewery.utils.ParamUtils;
import com.brewery.utils.ResponseMaker;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing Products and any other instances which related to it
 */
@Controller
public class ProductController {

    @Autowired
    private ContentServiceImpl contentService;

    private static final Logger LOGGER = Logger.getLogger(ProductController.class);

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/product", method = RequestMethod.POST)
    public ResponseEntity<String> saveProduct(@RequestBody @Valid Product product) {
        LOGGER.info("Product saving process execution");
        Map<String, List<String>> error = contentService.checkLocalization(product);
        if (!error.isEmpty()) {
            LOGGER.error("Content saving failed!");
            return ResponseMaker.makeResponse(error, ConstantParams.JSON_HEADER_TYPE, HttpStatus.BAD_REQUEST);
        }

        Map<String, Long> response = new HashMap<>();
        response.put("id", contentService.save(product, ConstantParams.PRODUCT_CONTEXT));
        return ResponseMaker.makeResponse(response, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/content/product", method = RequestMethod.GET)
    public ResponseEntity<String> getProducts() {
        LOGGER.info("Getting instances of Product");
        List<Content> products = contentService.getAllProducts(ConstantParams.PRODUCT_CONTEXT);
        return ResponseMaker.makeResponse(products, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/content/product/{productId}", method = RequestMethod.GET)
    public ResponseEntity<String> getProduct(@PathVariable Long productId) {
        LOGGER.info("Getting one instance of Product by id");
        Product product = (Product) contentService.getOne(productId, ConstantParams.PRODUCT_CONTEXT);
        return ResponseMaker.makeResponse(product, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/product/{productId}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody @Valid Product product) {
        LOGGER.info("Product updating process execution");
        Product forUpdate = (Product) contentService.getOne(productId, ConstantParams.PRODUCT_CONTEXT);
        forUpdate.setProductType(product.getProductType());
        forUpdate.setName(product.getName());
        forUpdate.setDescriptions(product.getDescriptions());
        contentService.update(forUpdate, ConstantParams.PRODUCT_CONTEXT);
        return ResponseMaker.makeResponse("Product has been updated successfully",
                ConstantParams.JSON_HEADER_TYPE, HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/product/{productId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeProduct(@PathVariable Long productId) {
        LOGGER.info("Product removing process execution");
        contentService.remove(productId, ConstantParams.PRODUCT_CONTEXT);
        return ResponseMaker.makeResponse("Product has been updated successfully",
                ConstantParams.JSON_HEADER_TYPE, HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/product/type", method = RequestMethod.POST)
    public ResponseEntity<String> saveProductType(@RequestParam String typeName, @RequestParam MultipartFile files) throws Exception {
        LOGGER.info("Product type saving process execution");
        Map<String, Long> response = new HashMap<>();
        contentService.saveProductType(files, typeName, ConstantParams.PRODUCT_TYPE_CONTEXT);
        return ResponseMaker.makeResponse(response, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/content/product/type", method = RequestMethod.GET)
    public ResponseEntity<String> getAllTypes() {
        LOGGER.info("Getting all instances of ProductType");
        return ResponseMaker.makeResponse(
                contentService.getAllProductTypes(ConstantParams.PRODUCT_TYPE_CONTEXT),
                ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/product/type/{typeName}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeType(@PathVariable String typeName) {
        LOGGER.info("Removing instances of ProductType");
        contentService.removeProductType(typeName, ConstantParams.PRODUCT_TYPE_CONTEXT);
        return ResponseMaker.makeResponse("Product type has been removed successfully",
                ConstantParams.JSON_HEADER_TYPE, HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/product/{productId}/description", method = RequestMethod.POST)
    public ResponseEntity<String> addUpdateDescription(@PathVariable Long productId, @RequestBody @Valid Description description) {
        LOGGER.info("Adding product description process execution");

        if (!ParamUtils.isLocalizationValid(description.getType())) {
            LOGGER.error("Content saving failed!");
            return ResponseMaker.makeResponse("Description is invalid", ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK);
        }

        contentService.saveUpdateDescription(productId, description, ConstantParams.PRODUCT_CONTEXT);
        String message = "Description saving/updating was succeeded!";
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK, ConstantParams.INFO_MESSAGE);
    }

    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')")
    @RequestMapping(value = "/admin/content/product/{productId}/description/{type}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeDescription(@PathVariable Long productId, @PathVariable String type) {
        LOGGER.info("Adding product description process execution");
        contentService.removeContentMetadata(productId, type, ConstantParams.PRODUCT_CONTEXT);
        String message = "Description removing was succeeded!";
        return ResponseMaker.makeResponse(message, ConstantParams.JSON_HEADER_TYPE, HttpStatus.OK, ConstantParams.INFO_MESSAGE);
    }

}
