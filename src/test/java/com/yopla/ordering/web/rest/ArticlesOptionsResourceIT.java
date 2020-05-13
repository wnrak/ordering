package com.yopla.ordering.web.rest;

import com.yopla.ordering.YoplaApp;
import com.yopla.ordering.domain.ArticlesOptions;
import com.yopla.ordering.repository.ArticlesOptionsRepository;
import com.yopla.ordering.repository.search.ArticlesOptionsSearchRepository;

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
 * Integration tests for the {@link ArticlesOptionsResource} REST controller.
 */
@SpringBootTest(classes = YoplaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArticlesOptionsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CHOICE = "AAAAAAAAAA";
    private static final String UPDATED_CHOICE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    @Autowired
    private ArticlesOptionsRepository articlesOptionsRepository;

    /**
     * This repository is mocked in the com.yopla.ordering.repository.search test package.
     *
     * @see com.yopla.ordering.repository.search.ArticlesOptionsSearchRepositoryMockConfiguration
     */
    @Autowired
    private ArticlesOptionsSearchRepository mockArticlesOptionsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticlesOptionsMockMvc;

    private ArticlesOptions articlesOptions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticlesOptions createEntity(EntityManager em) {
        ArticlesOptions articlesOptions = new ArticlesOptions()
            .name(DEFAULT_NAME)
            .choice(DEFAULT_CHOICE)
            .price(DEFAULT_PRICE);
        return articlesOptions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticlesOptions createUpdatedEntity(EntityManager em) {
        ArticlesOptions articlesOptions = new ArticlesOptions()
            .name(UPDATED_NAME)
            .choice(UPDATED_CHOICE)
            .price(UPDATED_PRICE);
        return articlesOptions;
    }

    @BeforeEach
    public void initTest() {
        articlesOptions = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticlesOptions() throws Exception {
        int databaseSizeBeforeCreate = articlesOptionsRepository.findAll().size();

        // Create the ArticlesOptions
        restArticlesOptionsMockMvc.perform(post("/api/articles-options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlesOptions)))
            .andExpect(status().isCreated());

        // Validate the ArticlesOptions in the database
        List<ArticlesOptions> articlesOptionsList = articlesOptionsRepository.findAll();
        assertThat(articlesOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        ArticlesOptions testArticlesOptions = articlesOptionsList.get(articlesOptionsList.size() - 1);
        assertThat(testArticlesOptions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testArticlesOptions.getChoice()).isEqualTo(DEFAULT_CHOICE);
        assertThat(testArticlesOptions.getPrice()).isEqualTo(DEFAULT_PRICE);

        // Validate the ArticlesOptions in Elasticsearch
        verify(mockArticlesOptionsSearchRepository, times(1)).save(testArticlesOptions);
    }

    @Test
    @Transactional
    public void createArticlesOptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articlesOptionsRepository.findAll().size();

        // Create the ArticlesOptions with an existing ID
        articlesOptions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticlesOptionsMockMvc.perform(post("/api/articles-options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlesOptions)))
            .andExpect(status().isBadRequest());

        // Validate the ArticlesOptions in the database
        List<ArticlesOptions> articlesOptionsList = articlesOptionsRepository.findAll();
        assertThat(articlesOptionsList).hasSize(databaseSizeBeforeCreate);

        // Validate the ArticlesOptions in Elasticsearch
        verify(mockArticlesOptionsSearchRepository, times(0)).save(articlesOptions);
    }


    @Test
    @Transactional
    public void getAllArticlesOptions() throws Exception {
        // Initialize the database
        articlesOptionsRepository.saveAndFlush(articlesOptions);

        // Get all the articlesOptionsList
        restArticlesOptionsMockMvc.perform(get("/api/articles-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articlesOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].choice").value(hasItem(DEFAULT_CHOICE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }
    
    @Test
    @Transactional
    public void getArticlesOptions() throws Exception {
        // Initialize the database
        articlesOptionsRepository.saveAndFlush(articlesOptions);

        // Get the articlesOptions
        restArticlesOptionsMockMvc.perform(get("/api/articles-options/{id}", articlesOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articlesOptions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.choice").value(DEFAULT_CHOICE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE));
    }

    @Test
    @Transactional
    public void getNonExistingArticlesOptions() throws Exception {
        // Get the articlesOptions
        restArticlesOptionsMockMvc.perform(get("/api/articles-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticlesOptions() throws Exception {
        // Initialize the database
        articlesOptionsRepository.saveAndFlush(articlesOptions);

        int databaseSizeBeforeUpdate = articlesOptionsRepository.findAll().size();

        // Update the articlesOptions
        ArticlesOptions updatedArticlesOptions = articlesOptionsRepository.findById(articlesOptions.getId()).get();
        // Disconnect from session so that the updates on updatedArticlesOptions are not directly saved in db
        em.detach(updatedArticlesOptions);
        updatedArticlesOptions
            .name(UPDATED_NAME)
            .choice(UPDATED_CHOICE)
            .price(UPDATED_PRICE);

        restArticlesOptionsMockMvc.perform(put("/api/articles-options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticlesOptions)))
            .andExpect(status().isOk());

        // Validate the ArticlesOptions in the database
        List<ArticlesOptions> articlesOptionsList = articlesOptionsRepository.findAll();
        assertThat(articlesOptionsList).hasSize(databaseSizeBeforeUpdate);
        ArticlesOptions testArticlesOptions = articlesOptionsList.get(articlesOptionsList.size() - 1);
        assertThat(testArticlesOptions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testArticlesOptions.getChoice()).isEqualTo(UPDATED_CHOICE);
        assertThat(testArticlesOptions.getPrice()).isEqualTo(UPDATED_PRICE);

        // Validate the ArticlesOptions in Elasticsearch
        verify(mockArticlesOptionsSearchRepository, times(1)).save(testArticlesOptions);
    }

    @Test
    @Transactional
    public void updateNonExistingArticlesOptions() throws Exception {
        int databaseSizeBeforeUpdate = articlesOptionsRepository.findAll().size();

        // Create the ArticlesOptions

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticlesOptionsMockMvc.perform(put("/api/articles-options")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlesOptions)))
            .andExpect(status().isBadRequest());

        // Validate the ArticlesOptions in the database
        List<ArticlesOptions> articlesOptionsList = articlesOptionsRepository.findAll();
        assertThat(articlesOptionsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ArticlesOptions in Elasticsearch
        verify(mockArticlesOptionsSearchRepository, times(0)).save(articlesOptions);
    }

    @Test
    @Transactional
    public void deleteArticlesOptions() throws Exception {
        // Initialize the database
        articlesOptionsRepository.saveAndFlush(articlesOptions);

        int databaseSizeBeforeDelete = articlesOptionsRepository.findAll().size();

        // Delete the articlesOptions
        restArticlesOptionsMockMvc.perform(delete("/api/articles-options/{id}", articlesOptions.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticlesOptions> articlesOptionsList = articlesOptionsRepository.findAll();
        assertThat(articlesOptionsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ArticlesOptions in Elasticsearch
        verify(mockArticlesOptionsSearchRepository, times(1)).deleteById(articlesOptions.getId());
    }

    @Test
    @Transactional
    public void searchArticlesOptions() throws Exception {
        // Initialize the database
        articlesOptionsRepository.saveAndFlush(articlesOptions);
        when(mockArticlesOptionsSearchRepository.search(queryStringQuery("id:" + articlesOptions.getId())))
            .thenReturn(Collections.singletonList(articlesOptions));
        // Search the articlesOptions
        restArticlesOptionsMockMvc.perform(get("/api/_search/articles-options?query=id:" + articlesOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articlesOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].choice").value(hasItem(DEFAULT_CHOICE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }
}
