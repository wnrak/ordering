package com.yopla.ordering.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ArticlesOptionGroups.
 */
@Entity
@Table(name = "articles_option_groups")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "articlesoptiongroups")
public class ArticlesOptionGroups implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "option_name", nullable = false)
    private String optionName;

    @NotNull
    @Column(name = "active_option_name", nullable = false)
    private String activeOptionName;

    @Column(name = "min_value")
    private Integer minValue;

    @Column(name = "max_value")
    private Integer maxValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionName() {
        return optionName;
    }

    public ArticlesOptionGroups optionName(String optionName) {
        this.optionName = optionName;
        return this;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getActiveOptionName() {
        return activeOptionName;
    }

    public ArticlesOptionGroups activeOptionName(String activeOptionName) {
        this.activeOptionName = activeOptionName;
        return this;
    }

    public void setActiveOptionName(String activeOptionName) {
        this.activeOptionName = activeOptionName;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public ArticlesOptionGroups minValue(Integer minValue) {
        this.minValue = minValue;
        return this;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public ArticlesOptionGroups maxValue(Integer maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticlesOptionGroups)) {
            return false;
        }
        return id != null && id.equals(((ArticlesOptionGroups) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ArticlesOptionGroups{" +
            "id=" + getId() +
            ", optionName='" + getOptionName() + "'" +
            ", activeOptionName='" + getActiveOptionName() + "'" +
            ", minValue=" + getMinValue() +
            ", maxValue=" + getMaxValue() +
            "}";
    }
}
