package com.yopla.ordering.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "restaurant")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "restaurant_name", nullable = false)
    private String restaurantName;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "banner", nullable = false)
    private String banner;

    @NotNull
    @Column(name = "logo", nullable = false)
    private String logo;

    @NotNull
    @Column(name = "number_of_tables", nullable = false)
    private Integer numberOfTables;

    @NotNull
    @Column(name = "availability", nullable = false)
    private Boolean availability;

    @NotNull
    @Column(name = "api_key", nullable = false)
    private String apiKey;

    @Column(name = "pay_later")
    private Boolean payLater;

    @Column(name = "ask_for_service")
    private Boolean askForService;

    @Column(name = "enable_sms")
    private Boolean enableSms;

    @NotNull
    @Column(name = "slug", nullable = false)
    private String slug;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public Restaurant restaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
        return this;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getLocation() {
        return location;
    }

    public Restaurant location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBanner() {
        return banner;
    }

    public Restaurant banner(String banner) {
        this.banner = banner;
        return this;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getLogo() {
        return logo;
    }

    public Restaurant logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getNumberOfTables() {
        return numberOfTables;
    }

    public Restaurant numberOfTables(Integer numberOfTables) {
        this.numberOfTables = numberOfTables;
        return this;
    }

    public void setNumberOfTables(Integer numberOfTables) {
        this.numberOfTables = numberOfTables;
    }

    public Boolean isAvailability() {
        return availability;
    }

    public Restaurant availability(Boolean availability) {
        this.availability = availability;
        return this;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public String getApiKey() {
        return apiKey;
    }

    public Restaurant apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Boolean isPayLater() {
        return payLater;
    }

    public Restaurant payLater(Boolean payLater) {
        this.payLater = payLater;
        return this;
    }

    public void setPayLater(Boolean payLater) {
        this.payLater = payLater;
    }

    public Boolean isAskForService() {
        return askForService;
    }

    public Restaurant askForService(Boolean askForService) {
        this.askForService = askForService;
        return this;
    }

    public void setAskForService(Boolean askForService) {
        this.askForService = askForService;
    }

    public Boolean isEnableSms() {
        return enableSms;
    }

    public Restaurant enableSms(Boolean enableSms) {
        this.enableSms = enableSms;
        return this;
    }

    public void setEnableSms(Boolean enableSms) {
        this.enableSms = enableSms;
    }

    public String getSlug() {
        return slug;
    }

    public Restaurant slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        return id != null && id.equals(((Restaurant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", restaurantName='" + getRestaurantName() + "'" +
            ", location='" + getLocation() + "'" +
            ", banner='" + getBanner() + "'" +
            ", logo='" + getLogo() + "'" +
            ", numberOfTables=" + getNumberOfTables() +
            ", availability='" + isAvailability() + "'" +
            ", apiKey='" + getApiKey() + "'" +
            ", payLater='" + isPayLater() + "'" +
            ", askForService='" + isAskForService() + "'" +
            ", enableSms='" + isEnableSms() + "'" +
            ", slug='" + getSlug() + "'" +
            "}";
    }
}
