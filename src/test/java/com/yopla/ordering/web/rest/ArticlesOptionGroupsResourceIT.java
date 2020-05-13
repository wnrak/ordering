package com.yopla.ordering.web.rest;

import com.yopla.ordering.YoplaApp;
import com.yopla.ordering.domain.ArticlesOptionGroups;
import com.yopla.ordering.repository.ArticlesOptionGroupsRepository;
import com.yopla.ordering.repository.search.ArticlesOptionGroupsSearchRepository;

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
 * Integration tests for the {@link ArticlesOptionGroupsResource} REST controller.
 */
@SpringBootTest(classes = YoplaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArticlesOptionGroupsResourceIT {

    private static final String DEFAULT_OPTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVE_OPTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVE_OPTION_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_MIN_VALUE = 1;
    private static final Integer UPDATED_MIN_VALUE = 2;

    private static final Integer DEFAULT_MAX_VALUE = 1;
    private static final Integer UPDATED_MAX_VALUE = 2;

    @Autowired
    private ArticlesOptionGroupsRepository articlesOptionGroupsRepository;

    /**
     * This repository is mocked in the com.yopla.ordering.repository.search test package.
     *
     * @see com.yopla.ordering.repository.search.ArticlesOptionGroupsSearchRepositoryMockConfiguration
     */
    @Autowired
    private ArticlesOptionGroupsSearchRepository mockArticlesOptionGroupsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticlesOptionGroupsMockMvc;

    private ArticlesOptionGroups articlesOptionGroups;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticlesOptionGroups createEntity(EntityManager em) {
        ArticlesOptionGroups articlesOptionGroups = new ArticlesOptionGroups()
            .optionName(DEFAULT_OPTION_NAME)
            .activeOptionName(DEFAULT_ACTIVE_OPTION_NAME)
            .minValue(DEFAULT_MIN_VALUE)
            .maxValue(DEFAULT_MAX_VALUE);
        return articlesOptionGroups;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticlesOptionGroups createUpdatedEntity(EntityManager em) {
        ArticlesOptionGroups articlesOptionGroups = new ArticlesOptionGroups()
            .optionName(UPDATED_OPTION_NAME)
            .activeOptionName(UPDATED_ACTIVE_OPTION_NAME)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE);
        return articlesOptionGroups;
    }

    @BeforeEach
    public void initTest() {
        articlesOptionGroups = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticlesOptionGroups() throws Exception {
        int databaseSizeBeforeCreate = articlesOptionGroupsRepository.findAll().size();

        // Create the ArticlesOptionGroups
        restArticlesOptionGroupsMockMvc.perform(post("/api/articles-option-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlesOptionGroups)))
            .andExpect(status().isCreated());

        // Validate the ArticlesOptionGroups in the database
        List<ArticlesOptionGroups> articlesOptionGroupsList = articlesOptionGroupsRepository.findAll();
        assertThat(articlesOptionGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        ArticlesOptionGroups testArticlesOptionGroups = articlesOptionGroupsList.get(articlesOptionGroupsList.size() - 1);
        assertThat(testArticlesOptionGroups.getOptionName()).isEqualTo(DEFAULT_OPTION_NAME);
        assertThat(testArticlesOptionGroups.getActiveOptionName()).isEqualTo(DEFAULT_ACTIVE_OPTION_NAME);
        assertThat(testArticlesOptionGroups.getMinValue()).isEqualTo(DEFAULT_MIN_VALUE);
        assertThat(testArticlesOptionGroups.getMaxValue()).isEqualTo(DEFAULT_MAX_VALUE);

        // Validate the ArticlesOptionGroups in Elasticsearch
        verify(mockArticlesOptionGroupsSearchRepository, times(1)).save(testArticlesOptionGroups);
    }

    @Test
    @Transactional
    public void createArticlesOptionGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articlesOptionGroupsRepository.findAll().size();

        // Create the ArticlesOptionGroups with an existing ID
        articlesOptionGroups.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticlesOptionGroupsMockMvc.perform(post("/api/articles-option-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlesOptionGroups)))
            .andExpect(status().isBadRequest());

        // Validate the ArticlesOptionGroups in the database
        List<ArticlesOptionGroups> articlesOptionGroupsList = articlesOptionGroupsRepository.findAll();
        assertThat(articlesOptionGroupsList).hasSize(databaseSizeBeforeCreate);

        // Validate the ArticlesOptionGroups in Elasticsearch
        verify(mockArticlesOptionGroupsSearchRepository, times(0)).save(articlesOptionGroups);
    }


    @Test
    @Transactional
    public void checkOptionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = articlesOptionGroupsRepository.findAll().size();
        // set the field null
        articlesOptionGroups.setOptionName(null);

        // Create the ArticlesOptionGroups, which fails.

        restArticlesOptionGroupsMockMvc.perform(post("/api/articles-option-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlesOptionGroups)))
            .andExpect(status().isBadRequest());

        List<ArticlesOptionGroups> articlesOptionGroupsList = articlesOptionGroupsRepository.findAll();
        assertThat(articlesOptionGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveOptionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = articlesOptionGroupsRepository.findAll().size();
        // set the field null
        articlesOptionGroups.setActiveOptionName(null);

        // Create the ArticlesOptionGroups, which fails.

        restArticlesOptionGroupsMockMvc.perform(post("/api/articles-option-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlesOptionGroups)))
            .andExpect(status().isBadRequest());

        List<ArticlesOptionGroups> articlesOptionGroupsList = articlesOptionGroupsRepository.findAll();
        assertThat(articlesOptionGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArticlesOptionGroups() throws Exception {
        // Initialize the database
        articlesOptionGroupsRepository.saveAndFlush(articlesOptionGroups);

        // Get all the articlesOptionGroupsList
        restArticlesOptionGroupsMockMvc.perform(get("/api/articles-option-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articlesOptionGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].optionName").value(hasItem(DEFAULT_OPTION_NAME)))
            .andExpect(jsonPath("$.[*].activeOptionName").value(hasItem(DEFAULT_ACTIVE_OPTION_NAME)))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE)))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE)));
    }
    
    @Test
    @Transactional
    public void getArticlesOptionGroups() throws Exception {
        // Initialize the database
        articlesOptionGroupsRepository.saveAndFlush(articlesOptionGroups);

        // Get the articlesOptionGroups
        restArticlesOptionGroupsMockMvc.perform(get("/api/articles-option-groups/{id}", articlesOptionGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articlesOptionGroups.getId().intValue()))
            .andExpect(jsonPath("$.optionName").value(DEFAULT_OPTION_NAME))
            .andExpect(jsonPath("$.activeOptionName").value(DEFAULT_ACTIVE_OPTION_NAME))
            .andExpect(jsonPath("$.minValue").value(DEFAULT_MIN_VALUE))
            .andExpect(jsonPath("$.maxValue").value(DEFAULT_MAX_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingArticlesOptionGroups() throws Exception {
        // Get the articlesOptionGroups
        restArticlesOptionGroupsMockMvc.perform(get("/api/articles-option-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticlesOptionGroups() throws Exception {
        // Initialize the database
        articlesOptionGroupsRepository.saveAndFlush(articlesOptionGroups);

        int databaseSizeBeforeUpdate = articlesOptionGroupsRepository.findAll().size();

        // Update the articlesOptionGroups
        ArticlesOptionGroups updatedArticlesOptionGroups = articlesOptionGroupsRepository.findById(articlesOptionGroups.getId()).get();
        // Disconnect from session so that the updates on updatedArticlesOptionGroups are not directly saved in db
        em.detach(updatedArticlesOptionGroups);
        updatedArticlesOptionGroups
            .optionName(UPDATED_OPTION_NAME)
            .activeOptionName(UPDATED_ACTIVE_OPTION_NAME)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE);

        restArticlesOptionGroupsMockMvc.perform(put("/api/articles-option-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticlesOptionGroups)))
            .andExpect(status().isOk());

        // Validate the ArticlesOptionGroups in the database
        List<ArticlesOptionGroups> articlesOptionGroupsList = articlesOptionGroupsRepository.findAll();
        assertThat(articlesOptionGroupsList).hasSize(databaseSizeBeforeUpdate);
        ArticlesOptionGroups testArticlesOptionGroups = articlesOptionGroupsList.get(articlesOptionGroupsList.size() - 1);
        assertThat(testArticlesOptionGroups.getOptionName()).isEqualTo(UPDATED_OPTION_NAME);
        assertThat(testArticlesOptionGroups.getActiveOptionName()).isEqualTo(UPDATED_ACTIVE_OPTION_NAME);
        assertThat(testArticlesOptionGroups.getMinValue()).isEqualTo(UPDATED_MIN_VALUE);
        assertThat(testArticlesOptionGroups.getMaxValue()).isEqualTo(UPDATED_MAX_VALUE);

        // Validate the ArticlesOptionGroups in Elasticsearch
        verify(mockArticlesOptionGroupsSearchRepository, times(1)).save(testArticlesOptionGroups);
    }

    @Test
    @Transactional
    public void updateNonExistingArticlesOptionGroups() throws Exception {
        int databaseSizeBeforeUpdate = articlesOptionGroupsRepository.findAll().size();

        // Create the ArticlesOptionGroups

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticlesOptionGroupsMockMvc.perform(put("/api/articles-option-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlesOptionGroups)))
            .andExpect(status().isBadRequest());

        // Validate the ArticlesOptionGroups in the database
        List<ArticlesOptionGroups> articlesOptionGroupsList = articlesOptionGroupsRepository.findAll();
        assertThat(articlesOptionGroupsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ArticlesOptionGroups in Elasticsearch
        verify(mockArticlesOptionGroupsSearchRepository, times(0)).save(articlesOptionGroups);
    }

    @Test
    @Transactional
    public void deleteArticlesOptionGroups() throws Exception {
        // Initialize the database
        articlesOptionGroupsRepository.saveAndFlush(articlesOptionGroups);

        int databaseSizeBeforeDelete = articlesOptionGroupsRepository.findAll().size();

        // Delete the articlesOptionGroups
        restArticlesOptionGroupsMockMvc.perform(delete("/api/articles-option-groups/{id}", articlesOptionGroups.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticlesOptionGroups> articlesOptionGroupsList = articlesOptionGroupsRepository.findAll();
        assertThat(articlesOptionGroupsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ArticlesOptionGroups in Elasticsearch
        verify(mockArticlesOptionGroupsSearchRepository, times(1)).deleteById(articlesOptionGroups.getId());
    }

    @Test
    @Transactional
    public void searchArticlesOptionGroups() throws Exception {
        // Initialize the database
        articlesOptionGroupsRepository.saveAndFlush(articlesOptionGroups);
        when(mockArticlesOptionGroupsSearchRepository.search(queryStringQuery("id:" + articlesOptionGroups.getId())))
            .thenReturn(Collections.singletonList(articlesOptionGroups));
        // Search the articlesOptionGroups
        restArticlesOptionGroupsMockMvc.perform(get("/api/_search/articles-option-groups?query=id:" + articlesOptionGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articlesOptionGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].optionName").value(hasItem(DEFAULT_OPTION_NAME)))
            .andExpect(jsonPath("$.[*].activeOptionName").value(hasItem(DEFAULT_ACTIVE_OPTION_NAME)))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE)))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE)));
    }
}
