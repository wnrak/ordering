package com.yopla.ordering.web.rest;

import com.yopla.ordering.YoplaApp;
import com.yopla.ordering.domain.Restaurant;
import com.yopla.ordering.repository.RestaurantRepository;
import com.yopla.ordering.repository.search.RestaurantSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RestaurantResource} REST controller.
 */
@SpringBootTest(classes = YoplaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RestaurantResourceIT {

    private static final String DEFAULT_RESTAURANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RESTAURANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER = "AAAAAAAAAA";
    private static final String UPDATED_BANNER = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_TABLES = 1;
    private static final Integer UPDATED_NUMBER_OF_TABLES = 2;

    private static final Boolean DEFAULT_AVAILABILITY = false;
    private static final Boolean UPDATED_AVAILABILITY = true;

    private static final String DEFAULT_API_KEY = "AAAAAAAAAA";
    private static final String UPDATED_API_KEY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PAY_LATER = false;
    private static final Boolean UPDATED_PAY_LATER = true;

    private static final Boolean DEFAULT_ASK_FOR_SERVICE = false;
    private static final Boolean UPDATED_ASK_FOR_SERVICE = true;

    private static final Boolean DEFAULT_ENABLE_SMS = false;
    private static final Boolean UPDATED_ENABLE_SMS = true;

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    @Autowired
    private RestaurantRepository restaurantRepository;

    /**
     * This repository is mocked in the com.yopla.ordering.repository.search test package.
     *
     * @see com.yopla.ordering.repository.search.RestaurantSearchRepositoryMockConfiguration
     */
    @Autowired
    private RestaurantSearchRepository mockRestaurantSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRestaurantMockMvc;

    private Restaurant restaurant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Restaurant createEntity(EntityManager em) {
        Restaurant restaurant = new Restaurant()
            .restaurantName(DEFAULT_RESTAURANT_NAME)
            .location(DEFAULT_LOCATION)
            .banner(DEFAULT_BANNER)
            .logo(DEFAULT_LOGO)
            .numberOfTables(DEFAULT_NUMBER_OF_TABLES)
            .availability(DEFAULT_AVAILABILITY)
            .apiKey(DEFAULT_API_KEY)
            .payLater(DEFAULT_PAY_LATER)
            .askForService(DEFAULT_ASK_FOR_SERVICE)
            .enableSms(DEFAULT_ENABLE_SMS)
            .slug(DEFAULT_SLUG);
        return restaurant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Restaurant createUpdatedEntity(EntityManager em) {
        Restaurant restaurant = new Restaurant()
            .restaurantName(UPDATED_RESTAURANT_NAME)
            .location(UPDATED_LOCATION)
            .banner(UPDATED_BANNER)
            .logo(UPDATED_LOGO)
            .numberOfTables(UPDATED_NUMBER_OF_TABLES)
            .availability(UPDATED_AVAILABILITY)
            .apiKey(UPDATED_API_KEY)
            .payLater(UPDATED_PAY_LATER)
            .askForService(UPDATED_ASK_FOR_SERVICE)
            .enableSms(UPDATED_ENABLE_SMS)
            .slug(UPDATED_SLUG);
        return restaurant;
    }

    @BeforeEach
    public void initTest() {
        restaurant = createEntity(em);
    }

    @Test
    @Transactional
    public void createRestaurant() throws Exception {
        int databaseSizeBeforeCreate = restaurantRepository.findAll().size();

        // Create the Restaurant
        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurant)))
            .andExpect(status().isCreated());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeCreate + 1);
        Restaurant testRestaurant = restaurantList.get(restaurantList.size() - 1);
        assertThat(testRestaurant.getRestaurantName()).isEqualTo(DEFAULT_RESTAURANT_NAME);
        assertThat(testRestaurant.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testRestaurant.getBanner()).isEqualTo(DEFAULT_BANNER);
        assertThat(testRestaurant.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testRestaurant.getNumberOfTables()).isEqualTo(DEFAULT_NUMBER_OF_TABLES);
        assertThat(testRestaurant.isAvailability()).isEqualTo(DEFAULT_AVAILABILITY);
        assertThat(testRestaurant.getApiKey()).isEqualTo(DEFAULT_API_KEY);
        assertThat(testRestaurant.isPayLater()).isEqualTo(DEFAULT_PAY_LATER);
        assertThat(testRestaurant.isAskForService()).isEqualTo(DEFAULT_ASK_FOR_SERVICE);
        assertThat(testRestaurant.isEnableSms()).isEqualTo(DEFAULT_ENABLE_SMS);
        assertThat(testRestaurant.getSlug()).isEqualTo(DEFAULT_SLUG);

        // Validate the Restaurant in Elasticsearch
        verify(mockRestaurantSearchRepository, times(1)).save(testRestaurant);
    }

    @Test
    @Transactional
    public void createRestaurantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = restaurantRepository.findAll().size();

        // Create the Restaurant with an existing ID
        restaurant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurant)))
            .andExpect(status().isBadRequest());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeCreate);

        // Validate the Restaurant in Elasticsearch
        verify(mockRestaurantSearchRepository, times(0)).save(restaurant);
    }


    @Test
    @Transactional
    public void checkRestaurantNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setRestaurantName(null);

        // Create the Restaurant, which fails.

        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurant)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setLocation(null);

        // Create the Restaurant, which fails.

        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurant)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBannerIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setBanner(null);

        // Create the Restaurant, which fails.

        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurant)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLogoIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setLogo(null);

        // Create the Restaurant, which fails.

        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurant)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfTablesIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setNumberOfTables(null);

        // Create the Restaurant, which fails.

        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurant)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailabilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setAvailability(null);

        // Create the Restaurant, which fails.

        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurant)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApiKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setApiKey(null);

        // Create the Restaurant, which fails.

        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurant)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSlugIsRequired() throws Exception {
        int databaseSizeBeforeTest = restaurantRepository.findAll().size();
        // set the field null
        restaurant.setSlug(null);

        // Create the Restaurant, which fails.

        restRestaurantMockMvc.perform(post("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurant)))
            .andExpect(status().isBadRequest());

        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRestaurants() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get all the restaurantList
        restRestaurantMockMvc.perform(get("/api/restaurants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurant.getId().intValue())))
            .andExpect(jsonPath("$.[*].restaurantName").value(hasItem(DEFAULT_RESTAURANT_NAME)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].banner").value(hasItem(DEFAULT_BANNER)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].numberOfTables").value(hasItem(DEFAULT_NUMBER_OF_TABLES)))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY.booleanValue())))
            .andExpect(jsonPath("$.[*].apiKey").value(hasItem(DEFAULT_API_KEY)))
            .andExpect(jsonPath("$.[*].payLater").value(hasItem(DEFAULT_PAY_LATER.booleanValue())))
            .andExpect(jsonPath("$.[*].askForService").value(hasItem(DEFAULT_ASK_FOR_SERVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].enableSms").value(hasItem(DEFAULT_ENABLE_SMS.booleanValue())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)));
    }
    
    @Test
    @Transactional
    public void getRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        // Get the restaurant
        restRestaurantMockMvc.perform(get("/api/restaurants/{id}", restaurant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(restaurant.getId().intValue()))
            .andExpect(jsonPath("$.restaurantName").value(DEFAULT_RESTAURANT_NAME))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.banner").value(DEFAULT_BANNER))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO))
            .andExpect(jsonPath("$.numberOfTables").value(DEFAULT_NUMBER_OF_TABLES))
            .andExpect(jsonPath("$.availability").value(DEFAULT_AVAILABILITY.booleanValue()))
            .andExpect(jsonPath("$.apiKey").value(DEFAULT_API_KEY))
            .andExpect(jsonPath("$.payLater").value(DEFAULT_PAY_LATER.booleanValue()))
            .andExpect(jsonPath("$.askForService").value(DEFAULT_ASK_FOR_SERVICE.booleanValue()))
            .andExpect(jsonPath("$.enableSms").value(DEFAULT_ENABLE_SMS.booleanValue()))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG));
    }

    @Test
    @Transactional
    public void getNonExistingRestaurant() throws Exception {
        // Get the restaurant
        restRestaurantMockMvc.perform(get("/api/restaurants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();

        // Update the restaurant
        Restaurant updatedRestaurant = restaurantRepository.findById(restaurant.getId()).get();
        // Disconnect from session so that the updates on updatedRestaurant are not directly saved in db
        em.detach(updatedRestaurant);
        updatedRestaurant
            .restaurantName(UPDATED_RESTAURANT_NAME)
            .location(UPDATED_LOCATION)
            .banner(UPDATED_BANNER)
            .logo(UPDATED_LOGO)
            .numberOfTables(UPDATED_NUMBER_OF_TABLES)
            .availability(UPDATED_AVAILABILITY)
            .apiKey(UPDATED_API_KEY)
            .payLater(UPDATED_PAY_LATER)
            .askForService(UPDATED_ASK_FOR_SERVICE)
            .enableSms(UPDATED_ENABLE_SMS)
            .slug(UPDATED_SLUG);

        restRestaurantMockMvc.perform(put("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRestaurant)))
            .andExpect(status().isOk());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);
        Restaurant testRestaurant = restaurantList.get(restaurantList.size() - 1);
        assertThat(testRestaurant.getRestaurantName()).isEqualTo(UPDATED_RESTAURANT_NAME);
        assertThat(testRestaurant.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testRestaurant.getBanner()).isEqualTo(UPDATED_BANNER);
        assertThat(testRestaurant.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testRestaurant.getNumberOfTables()).isEqualTo(UPDATED_NUMBER_OF_TABLES);
        assertThat(testRestaurant.isAvailability()).isEqualTo(UPDATED_AVAILABILITY);
        assertThat(testRestaurant.getApiKey()).isEqualTo(UPDATED_API_KEY);
        assertThat(testRestaurant.isPayLater()).isEqualTo(UPDATED_PAY_LATER);
        assertThat(testRestaurant.isAskForService()).isEqualTo(UPDATED_ASK_FOR_SERVICE);
        assertThat(testRestaurant.isEnableSms()).isEqualTo(UPDATED_ENABLE_SMS);
        assertThat(testRestaurant.getSlug()).isEqualTo(UPDATED_SLUG);

        // Validate the Restaurant in Elasticsearch
        verify(mockRestaurantSearchRepository, times(1)).save(testRestaurant);
    }

    @Test
    @Transactional
    public void updateNonExistingRestaurant() throws Exception {
        int databaseSizeBeforeUpdate = restaurantRepository.findAll().size();

        // Create the Restaurant

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestaurantMockMvc.perform(put("/api/restaurants")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(restaurant)))
            .andExpect(status().isBadRequest());

        // Validate the Restaurant in the database
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Restaurant in Elasticsearch
        verify(mockRestaurantSearchRepository, times(0)).save(restaurant);
    }

    @Test
    @Transactional
    public void deleteRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);

        int databaseSizeBeforeDelete = restaurantRepository.findAll().size();

        // Delete the restaurant
        restRestaurantMockMvc.perform(delete("/api/restaurants/{id}", restaurant.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        assertThat(restaurantList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Restaurant in Elasticsearch
        verify(mockRestaurantSearchRepository, times(1)).deleteById(restaurant.getId());
    }

    @Test
    @Transactional
    public void searchRestaurant() throws Exception {
        // Initialize the database
        restaurantRepository.saveAndFlush(restaurant);
        when(mockRestaurantSearchRepository.search(queryStringQuery("id:" + restaurant.getId())))
            .thenReturn(Collections.singletonList(restaurant));
        // Search the restaurant
        restRestaurantMockMvc.perform(get("/api/_search/restaurants?query=id:" + restaurant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurant.getId().intValue())))
            .andExpect(jsonPath("$.[*].restaurantName").value(hasItem(DEFAULT_RESTAURANT_NAME)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].banner").value(hasItem(DEFAULT_BANNER)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].numberOfTables").value(hasItem(DEFAULT_NUMBER_OF_TABLES)))
            .andExpect(jsonPath("$.[*].availability").value(hasItem(DEFAULT_AVAILABILITY.booleanValue())))
            .andExpect(jsonPath("$.[*].apiKey").value(hasItem(DEFAULT_API_KEY)))
            .andExpect(jsonPath("$.[*].payLater").value(hasItem(DEFAULT_PAY_LATER.booleanValue())))
            .andExpect(jsonPath("$.[*].askForService").value(hasItem(DEFAULT_ASK_FOR_SERVICE.booleanValue())))
            .andExpect(jsonPath("$.[*].enableSms").value(hasItem(DEFAULT_ENABLE_SMS.booleanValue())))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)));
    }
}
