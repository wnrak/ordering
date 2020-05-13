package com.yopla.ordering.web.rest;

import com.yopla.ordering.YoplaApp;
import com.yopla.ordering.domain.Categories;
import com.yopla.ordering.repository.CategoriesRepository;
import com.yopla.ordering.repository.search.CategoriesSearchRepository;

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
 * Integration tests for the {@link CategoriesResource} REST controller.
 */
@SpringBootTest(classes = YoplaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CategoriesResourceIT {

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    @Autowired
    private CategoriesRepository categoriesRepository;

    /**
     * This repository is mocked in the com.yopla.ordering.repository.search test package.
     *
     * @see com.yopla.ordering.repository.search.CategoriesSearchRepositoryMockConfiguration
     */
    @Autowired
    private CategoriesSearchRepository mockCategoriesSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriesMockMvc;

    private Categories categories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categories createEntity(EntityManager em) {
        Categories categories = new Categories()
            .categoryName(DEFAULT_CATEGORY_NAME);
        return categories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categories createUpdatedEntity(EntityManager em) {
        Categories categories = new Categories()
            .categoryName(UPDATED_CATEGORY_NAME);
        return categories;
    }

    @BeforeEach
    public void initTest() {
        categories = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategories() throws Exception {
        int databaseSizeBeforeCreate = categoriesRepository.findAll().size();

        // Create the Categories
        restCategoriesMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categories)))
            .andExpect(status().isCreated());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeCreate + 1);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);

        // Validate the Categories in Elasticsearch
        verify(mockCategoriesSearchRepository, times(1)).save(testCategories);
    }

    @Test
    @Transactional
    public void createCategoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoriesRepository.findAll().size();

        // Create the Categories with an existing ID
        categories.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriesMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categories)))
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Categories in Elasticsearch
        verify(mockCategoriesSearchRepository, times(0)).save(categories);
    }


    @Test
    @Transactional
    public void checkCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = categoriesRepository.findAll().size();
        // set the field null
        categories.setCategoryName(null);

        // Create the Categories, which fails.

        restCategoriesMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categories)))
            .andExpect(status().isBadRequest());

        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get all the categoriesList
        restCategoriesMockMvc.perform(get("/api/categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categories.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)));
    }
    
    @Test
    @Transactional
    public void getCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        // Get the categories
        restCategoriesMockMvc.perform(get("/api/categories/{id}", categories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categories.getId().intValue()))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingCategories() throws Exception {
        // Get the categories
        restCategoriesMockMvc.perform(get("/api/categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Update the categories
        Categories updatedCategories = categoriesRepository.findById(categories.getId()).get();
        // Disconnect from session so that the updates on updatedCategories are not directly saved in db
        em.detach(updatedCategories);
        updatedCategories
            .categoryName(UPDATED_CATEGORY_NAME);

        restCategoriesMockMvc.perform(put("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategories)))
            .andExpect(status().isOk());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);
        Categories testCategories = categoriesList.get(categoriesList.size() - 1);
        assertThat(testCategories.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);

        // Validate the Categories in Elasticsearch
        verify(mockCategoriesSearchRepository, times(1)).save(testCategories);
    }

    @Test
    @Transactional
    public void updateNonExistingCategories() throws Exception {
        int databaseSizeBeforeUpdate = categoriesRepository.findAll().size();

        // Create the Categories

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriesMockMvc.perform(put("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categories)))
            .andExpect(status().isBadRequest());

        // Validate the Categories in the database
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Categories in Elasticsearch
        verify(mockCategoriesSearchRepository, times(0)).save(categories);
    }

    @Test
    @Transactional
    public void deleteCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);

        int databaseSizeBeforeDelete = categoriesRepository.findAll().size();

        // Delete the categories
        restCategoriesMockMvc.perform(delete("/api/categories/{id}", categories.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Categories> categoriesList = categoriesRepository.findAll();
        assertThat(categoriesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Categories in Elasticsearch
        verify(mockCategoriesSearchRepository, times(1)).deleteById(categories.getId());
    }

    @Test
    @Transactional
    public void searchCategories() throws Exception {
        // Initialize the database
        categoriesRepository.saveAndFlush(categories);
        when(mockCategoriesSearchRepository.search(queryStringQuery("id:" + categories.getId())))
            .thenReturn(Collections.singletonList(categories));
        // Search the categories
        restCategoriesMockMvc.perform(get("/api/_search/categories?query=id:" + categories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categories.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)));
    }
}
