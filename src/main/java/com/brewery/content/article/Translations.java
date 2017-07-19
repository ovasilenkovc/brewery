package com.brewery.content.article;

import com.brewery.content.Content;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;

import javax.persistence.*;

/**
 * Translations implementation of the Content
 */
@Entity
@Table(name = "translations", catalog = "brewery")
public class Translations implements Content{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "translation_id", unique = true, nullable = false)
    private Long translation_id;

    @NotEmpty(message = "The title can not be blank")
    @Length(min = 4, message = "the title should consist minimum from 4 chars")
    private String title;

    @NotEmpty(message = "The translation text can not be blank")
    @Length(min = 20, message = "the translation text should consist minimum from 20 chars")
    private String translation;

    @NotEmpty(message = "Translation type is required parameter!")
    private String type;

    public Translations() {
    }

    public Translations(String title, String translation, String type) {
        this.title = title;
        this.translation = translation;
        this.type = type;
    }

    public Long getTranslation_id() {
        return translation_id;
    }

    public void setTranslation_id(Long translation_id) {
        this.translation_id = translation_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Translations that = (Translations) o;

        if (translation_id != null ? !translation_id.equals(that.translation_id) : that.translation_id != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (translation != null ? !translation.equals(that.translation) : that.translation != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = translation_id != null ? translation_id.hashCode() : 0;
//        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Translations{" +
                "translation_id=" + translation_id +
                ", title='" + title + '\'' +
                ", translation='" + translation + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
