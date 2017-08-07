package com.brewery.content.product;

import com.brewery.content.Content;
import net.sf.oval.constraint.NotEmpty;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "type", catalog = "brewery")
public class ProductType implements Content {

    @Id
    @NotEmpty(message = "Product type name can't be empty")
    @Column(name = "product_type_name", unique = true, nullable = false, length = 45)
    private String typeName;

    @NotEmpty(message = "Product type iconPath can't be empty")
    @Column(name = "product_type_ico", nullable = false)
    private String iconPath;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productType")
    @JsonManagedReference
    private Set<Product> products;

    public ProductType() {
    }

    public ProductType(String typeName, String iconPath) {
        this.typeName = typeName;
        this.iconPath = iconPath;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "typeName='" + typeName + '\'' +
                ", iconPath='" + iconPath + '\'' +
                ", products=" + products +
                '}';
    }
}
