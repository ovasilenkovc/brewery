package com.brewery.content.product;

import com.brewery.content.Content;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product", catalog = "brewery")
public class Product implements Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true, nullable = false)
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type", nullable = false)
    private ProductType productType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "prod_descs",
            joinColumns = @JoinColumn(name = "prod_id"),
            inverseJoinColumns = @JoinColumn(name = "description_id"))
    private Set<Description> descriptions = new HashSet<>();

    public Product() {
    }

    public Product(String name, ProductType productType) {
        this.name = name;
        this.productType = productType;
    }

    public Product(String name, ProductType productType, Set<Description> descriptions) {
        this.name = name;
        this.productType = productType;
        this.descriptions = descriptions;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Set<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<Description> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productId != null ? !productId.equals(product.productId) : product.productId != null) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (productType != null ? !productType.equals(product.productType) : product.productType != null) return false;
        return descriptions != null ? descriptions.equals(product.descriptions) : product.descriptions == null;
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (productType != null ? productType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", productType=" + productType +
                ", descriptions=" + descriptions +
                '}';
    }
}
