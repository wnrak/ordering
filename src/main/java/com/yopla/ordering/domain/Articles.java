package com.yopla.ordering.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Articles.
 */
@Entity
@Table(name = "articles")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "articles")
public class Articles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "article_name", nullable = false)
    private String articleName;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "tax_rate_if_pick_up")
    private Integer taxRateIfPickUp;

    @Column(name = "tax_rate_if_dine_in")
    private Integer taxRateIfDineIn;

    @Column(name = "information")
    private String information;

    @Column(name = "ingredient")
    private String ingredient;

    @Column(name = "image")
    private String image;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArticleName() {
        return articleName;
    }

    public Articles articleName(String articleName) {
        this.articleName = articleName;
        return this;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public Integer getPrice() {
        return price;
    }

    public Articles price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTaxRateIfPickUp() {
        return taxRateIfPickUp;
    }

    public Articles taxRateIfPickUp(Integer taxRateIfPickUp) {
        this.taxRateIfPickUp = taxRateIfPickUp;
        return this;
    }

    public void setTaxRateIfPickUp(Integer taxRateIfPickUp) {
        this.taxRateIfPickUp = taxRateIfPickUp;
    }

    public Integer getTaxRateIfDineIn() {
        return taxRateIfDineIn;
    }

    public Articles taxRateIfDineIn(Integer taxRateIfDineIn) {
        this.taxRateIfDineIn = taxRateIfDineIn;
        return this;
    }

    public void setTaxRateIfDineIn(Integer taxRateIfDineIn) {
        this.taxRateIfDineIn = taxRateIfDineIn;
    }

    public String getInformation() {
        return information;
    }

    public Articles information(String information) {
        this.information = information;
        return this;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getIngredient() {
        return ingredient;
    }

    public Articles ingredient(String ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getImage() {
        return image;
    }

    public Articles image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Articles)) {
            return false;
        }
        return id != null && id.equals(((Articles) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Articles{" +
            "id=" + getId() +
            ", articleName='" + getArticleName() + "'" +
            ", price=" + getPrice() +
            ", taxRateIfPickUp=" + getTaxRateIfPickUp() +
            ", taxRateIfDineIn=" + getTaxRateIfDineIn() +
            ", information='" + getInformation() + "'" +
            ", ingredient='" + getIngredient() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
