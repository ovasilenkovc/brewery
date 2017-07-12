package com.brewery.content.services;

import com.brewery.content.Content;

import java.io.IOException;
import java.util.List;

/**
 * ContentService Interface
 */
public interface ContentService {

    Long save(Content content, String context);

    List<Content> getAll(String context);

    Content getOne(Long id, String context);

    void remove(Long id, String context) throws IOException;

    void update(Content content, String context);

}
