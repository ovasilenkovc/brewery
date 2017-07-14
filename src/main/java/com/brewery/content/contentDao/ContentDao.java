package com.brewery.content.contentDao;

import com.brewery.content.Content;
import java.util.List;

/**
 * Content Data Access Object interface.
 */
public interface ContentDao {

    Long save(Content content);

    void update(Content content);

    Content getOne(Long id);

    List getAll();

    boolean remove(Content content);

}
