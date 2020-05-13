package com.yopla.ordering.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ArticlesOptions.
 */
@Entity
@Table(name = "articles_options")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "articlesoptions")
public class ArticlesOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "choice")
    private String choice;

    @Column(name = "price")
    private Integer price;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ArticlesOptions name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChoice() {
        return choice;
    }

    public ArticlesOptions choice(String choice) {
        this.choice = choice;
        return this;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Integer getPrice() {
        return price;
    }

    public ArticlesOptions price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticlesOptions)) {
            return false;
        }
        return id != null && id.equals(((ArticlesOptions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ArticlesOptions{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", choice='" + getChoice() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
