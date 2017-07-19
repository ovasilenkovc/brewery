package com.brewery.content.product;

import com.brewery.content.Content;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "descriptions", catalog = "brewery")
public class Description implements Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "desc_id", unique = true, nullable = false)
    private Long descId;

    @NotEmpty(message = "The title can not be blank")
    @Length(min = 4, message = "the title should consist minimum from 4 chars")
    @Column(name = "title", length = 45)
    private String title;

    @NotEmpty(message = "The product description text can not be blank")
    @Length(min = 20, message = "The Product description text should consist minimum from 20 chars")
    private String description;

    @NotEmpty(message = "The product composition text can not be blank")
    @Length(min = 10, message = "The product composition text should consist minimum from 10 chars")
    private String composition;

    @NotEmpty(message = "Translation type is required parameter!")
    @Column(name = "translation_type", nullable = false)
    private String type;

    public Description() {
    }

    public Long getDescId() {
        return descId;
    }

    public void setDescId(Long descId) {
        this.descId = descId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
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

        Description that = (Description) o;

        if (descId != null ? !descId.equals(that.descId) : that.descId != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (composition != null ? !composition.equals(that.composition) : that.composition != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = descId != null ? descId.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Description{" +
                "descId=" + descId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", composition='" + composition + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
