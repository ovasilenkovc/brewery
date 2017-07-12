package com.brewery.content.image;

import com.brewery.content.File;

import javax.persistence.*;

/**
 * Image representation of the abstract File.class
 */
@Entity
@Table(name = "images", catalog = "brewery")
public class Image extends File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "path")
    private String path;


    public Image() {
    }

    public Image(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public void setPath(String path) {
        this.path = path;
    }

}
