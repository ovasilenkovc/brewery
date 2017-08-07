package com.brewery.content.article;


import com.brewery.content.Content;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Article implementation of the Content
 */
@Entity
@Table(name = "articles", catalog = "brewery")
public class Article implements Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id", unique = true, nullable = false)
    private Long article_id;

    @NotEmpty(message = "The title can not be blank")
    @Length(min = 4, message = "the title should consist minimum from 4 chars")
    @Column(name = "title", length = 45)
    private String title;

    @Temporal(TemporalType.DATE)
    @Column(name = "post_date")
    private Date date;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "translations_article",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "translation_id"))
    private Set<Translations> translations = new HashSet<>();

    public Article() {
    }

    public Article(Date date, Set<Translations> translations) {
        this.date = date;
        this.translations = translations;
    }

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Translations> getTranslations() {
        return translations;
    }

    public void setTranslations(Set<Translations> translations) {
        this.translations = translations;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (article_id != null ? !article_id.equals(article.article_id) : article.article_id != null) return false;
        if (title != null ? !title.equals(article.title) : article.title != null) return false;
        if (date != null ? !date.equals(article.date) : article.date != null) return false;
        return translations != null ? translations.equals(article.translations) : article.translations == null;
    }

    @Override
    public int hashCode() {
        int result = article_id != null ? article_id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (translations != null ? translations.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Article{" +
                "article_id=" + article_id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", translations=" + translations +
                '}';
    }
}
