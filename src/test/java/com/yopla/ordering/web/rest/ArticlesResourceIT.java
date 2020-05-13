package com.yopla.ordering.web.rest;

import com.yopla.ordering.YoplaApp;
import com.yopla.ordering.domain.Articles;
import com.yopla.ordering.repository.ArticlesRepository;
import com.yopla.ordering.repository.search.ArticlesSearchRepository;

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
 * Integration tests for the {@link ArticlesResource} REST controller.
 */
@SpringBootTest(classes = YoplaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArticlesResourceIT {

    private static final String DEFAULT_ARTICLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final Integer DEFAULT_TAX_RATE_IF_PICK_UP = 1;
    private static final Integer UPDATED_TAX_RATE_IF_PICK_UP = 2;

    private static final Integer DEFAULT_TAX_RATE_IF_DINE_IN = 1;
    private static final Integer UPDATED_TAX_RATE_IF_DINE_IN = 2;

    private static final String DEFAULT_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_INFORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_INGREDIENT = "AAAAAAAAAA";
    private static final String UPDATED_INGREDIENT = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    @Autowired
    private ArticlesRepository articlesRepository;

    /**
     * This repository is mocked in the com.yopla.ordering.repository.search test package.
     *
     * @see com.yopla.ordering.repository.search.ArticlesSearchRepositoryMockConfiguration
     */
    @Autowired
    private ArticlesSearchRepository mockArticlesSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticlesMockMvc;

    private Articles articles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Articles createEntity(EntityManager em) {
        Articles articles = new Articles()
            .articleName(DEFAULT_ARTICLE_NAME)
            .price(DEFAULT_PRICE)
            .taxRateIfPickUp(DEFAULT_TAX_RATE_IF_PICK_UP)
            .taxRateIfDineIn(DEFAULT_TAX_RATE_IF_DINE_IN)
            .information(DEFAULT_INFORMATION)
            .ingredient(DEFAULT_INGREDIENT)
            .image(DEFAULT_IMAGE);
        return articles;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Articles createUpdatedEntity(EntityManager em) {
        Articles articles = new Articles()
            .articleName(UPDATED_ARTICLE_NAME)
            .price(UPDATED_PRICE)
            .taxRateIfPickUp(UPDATED_TAX_RATE_IF_PICK_UP)
            .taxRateIfDineIn(UPDATED_TAX_RATE_IF_DINE_IN)
            .information(UPDATED_INFORMATION)
            .ingredient(UPDATED_INGREDIENT)
            .image(UPDATED_IMAGE);
        return articles;
    }

    @BeforeEach
    public void initTest() {
        articles = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticles() throws Exception {
        int databaseSizeBeforeCreate = articlesRepository.findAll().size();

        // Create the Articles
        restArticlesMockMvc.perform(post("/api/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articles)))
            .andExpect(status().isCreated());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeCreate + 1);
        Articles testArticles = articlesList.get(articlesList.size() - 1);
        assertThat(testArticles.getArticleName()).isEqualTo(DEFAULT_ARTICLE_NAME);
        assertThat(testArticles.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testArticles.getTaxRateIfPickUp()).isEqualTo(DEFAULT_TAX_RATE_IF_PICK_UP);
        assertThat(testArticles.getTaxRateIfDineIn()).isEqualTo(DEFAULT_TAX_RATE_IF_DINE_IN);
        assertThat(testArticles.getInformation()).isEqualTo(DEFAULT_INFORMATION);
        assertThat(testArticles.getIngredient()).isEqualTo(DEFAULT_INGREDIENT);
        assertThat(testArticles.getImage()).isEqualTo(DEFAULT_IMAGE);

        // Validate the Articles in Elasticsearch
        verify(mockArticlesSearchRepository, times(1)).save(testArticles);
    }

    @Test
    @Transactional
    public void createArticlesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articlesRepository.findAll().size();

        // Create the Articles with an existing ID
        articles.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticlesMockMvc.perform(post("/api/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articles)))
            .andExpect(status().isBadRequest());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Articles in Elasticsearch
        verify(mockArticlesSearchRepository, times(0)).save(articles);
    }


    @Test
    @Transactional
    public void checkArticleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = articlesRepository.findAll().size();
        // set the field null
        articles.setArticleName(null);

        // Create the Articles, which fails.

        restArticlesMockMvc.perform(post("/api/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articles)))
            .andExpect(status().isBadRequest());

        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = articlesRepository.findAll().size();
        // set the field null
        articles.setPrice(null);

        // Create the Articles, which fails.

        restArticlesMockMvc.perform(post("/api/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articles)))
            .andExpect(status().isBadRequest());

        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArticles() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        // Get all the articlesList
        restArticlesMockMvc.perform(get("/api/articles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articles.getId().intValue())))
            .andExpect(jsonPath("$.[*].articleName").value(hasItem(DEFAULT_ARTICLE_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].taxRateIfPickUp").value(hasItem(DEFAULT_TAX_RATE_IF_PICK_UP)))
            .andExpect(jsonPath("$.[*].taxRateIfDineIn").value(hasItem(DEFAULT_TAX_RATE_IF_DINE_IN)))
            .andExpect(jsonPath("$.[*].information").value(hasItem(DEFAULT_INFORMATION)))
            .andExpect(jsonPath("$.[*].ingredient").value(hasItem(DEFAULT_INGREDIENT)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)));
    }
    
    @Test
    @Transactional
    public void getArticles() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        // Get the articles
        restArticlesMockMvc.perform(get("/api/articles/{id}", articles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articles.getId().intValue()))
            .andExpect(jsonPath("$.articleName").value(DEFAULT_ARTICLE_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.taxRateIfPickUp").value(DEFAULT_TAX_RATE_IF_PICK_UP))
            .andExpect(jsonPath("$.taxRateIfDineIn").value(DEFAULT_TAX_RATE_IF_DINE_IN))
            .andExpect(jsonPath("$.information").value(DEFAULT_INFORMATION))
            .andExpect(jsonPath("$.ingredient").value(DEFAULT_INGREDIENT))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE));
    }

    @Test
    @Transactional
    public void getNonExistingArticles() throws Exception {
        // Get the articles
        restArticlesMockMvc.perform(get("/api/articles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticles() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();

        // Update the articles
        Articles updatedArticles = articlesRepository.findById(articles.getId()).get();
        // Disconnect from session so that the updates on updatedArticles are not directly saved in db
        em.detach(updatedArticles);
        updatedArticles
            .articleName(UPDATED_ARTICLE_NAME)
            .price(UPDATED_PRICE)
            .taxRateIfPickUp(UPDATED_TAX_RATE_IF_PICK_UP)
            .taxRateIfDineIn(UPDATED_TAX_RATE_IF_DINE_IN)
            .information(UPDATED_INFORMATION)
            .ingredient(UPDATED_INGREDIENT)
            .image(UPDATED_IMAGE);

        restArticlesMockMvc.perform(put("/api/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticles)))
            .andExpect(status().isOk());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);
        Articles testArticles = articlesList.get(articlesList.size() - 1);
        assertThat(testArticles.getArticleName()).isEqualTo(UPDATED_ARTICLE_NAME);
        assertThat(testArticles.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testArticles.getTaxRateIfPickUp()).isEqualTo(UPDATED_TAX_RATE_IF_PICK_UP);
        assertThat(testArticles.getTaxRateIfDineIn()).isEqualTo(UPDATED_TAX_RATE_IF_DINE_IN);
        assertThat(testArticles.getInformation()).isEqualTo(UPDATED_INFORMATION);
        assertThat(testArticles.getIngredient()).isEqualTo(UPDATED_INGREDIENT);
        assertThat(testArticles.getImage()).isEqualTo(UPDATED_IMAGE);

        // Validate the Articles in Elasticsearch
        verify(mockArticlesSearchRepository, times(1)).save(testArticles);
    }

    @Test
    @Transactional
    public void updateNonExistingArticles() throws Exception {
        int databaseSizeBeforeUpdate = articlesRepository.findAll().size();

        // Create the Articles

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticlesMockMvc.perform(put("/api/articles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articles)))
            .andExpect(status().isBadRequest());

        // Validate the Articles in the database
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Articles in Elasticsearch
        verify(mockArticlesSearchRepository, times(0)).save(articles);
    }

    @Test
    @Transactional
    public void deleteArticles() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);

        int databaseSizeBeforeDelete = articlesRepository.findAll().size();

        // Delete the articles
        restArticlesMockMvc.perform(delete("/api/articles/{id}", articles.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Articles> articlesList = articlesRepository.findAll();
        assertThat(articlesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Articles in Elasticsearch
        verify(mockArticlesSearchRepository, times(1)).deleteById(articles.getId());
    }

    @Test
    @Transactional
    public void searchArticles() throws Exception {
        // Initialize the database
        articlesRepository.saveAndFlush(articles);
        when(mockArticlesSearchRepository.search(queryStringQuery("id:" + articles.getId())))
            .thenReturn(Collections.singletonList(articles));
        // Search the articles
        restArticlesMockMvc.perform(get("/api/_search/articles?query=id:" + articles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articles.getId().intValue())))
            .andExpect(jsonPath("$.[*].articleName").value(hasItem(DEFAULT_ARTICLE_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].taxRateIfPickUp").value(hasItem(DEFAULT_TAX_RATE_IF_PICK_UP)))
            .andExpect(jsonPath("$.[*].taxRateIfDineIn").value(hasItem(DEFAULT_TAX_RATE_IF_DINE_IN)))
            .andExpect(jsonPath("$.[*].information").value(hasItem(DEFAULT_INFORMATION)))
            .andExpect(jsonPath("$.[*].ingredient").value(hasItem(DEFAULT_INGREDIENT)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)));
    }
}
