package com.brewery.utils;

import java.util.*;

/**
 * Class with static default parameters that are used throughout the project.
 */
public final class ConstantParams {


    //Headers
    public static final String CONTENT_TYPE_HEADER = "Content-Type";

    public static final String JSON_HEADER_TYPE = "application/json; charset=UTF-8";

    public static final String MULTIPART_HEADER_TYPE = "multipart/form-data";

    public static final String TEXT_HEADER_TYPE = "text/html; charset=utf-8";

    public static final String IMAGE_HEADER_TYPE = "image/png";


    //message types
    public static final String ERROR_MESSAGE = "error";

    public static final String INFO_MESSAGE = "massage";

    //content parameters
    public static final String IMAGE_CONTEXT = "IMAGE";

    public static final String FILE_CONTEXT = "FILE";

    public static final String TRANSLATION_CONTEXT = "TRANSLATION";

    public static final String ARTICLE_CONTEXT = "ARTICLE";

    public static final String TEMP_FOLDER_PATH = System.getProperty("java.io.tmpdir");

    public static final String ROOT_PROJECT_DIR = "/brewery";

    public static final Map<String, Set<String>> CONTENT_MIME_TYPES = new HashMap<String, Set<String>>(){{
        put(IMAGE_CONTEXT, new HashSet<>((Arrays.asList("image/gif", "image/jpeg", "image/pjpeg", "image/png"))));
        put(FILE_CONTEXT, new HashSet<>((Arrays.asList("application/pdf", "application/xml", "text/html", "text/javascript", "text/xml"))));
    }};

}
