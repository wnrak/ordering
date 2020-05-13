package com.yopla.ordering.web.rest;

import com.yopla.ordering.YoplaApp;
import com.yopla.ordering.domain.QrCodeTables;
import com.yopla.ordering.repository.QrCodeTablesRepository;
import com.yopla.ordering.repository.search.QrCodeTablesSearchRepository;

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
 * Integration tests for the {@link QrCodeTablesResource} REST controller.
 */
@SpringBootTest(classes = YoplaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class QrCodeTablesResourceIT {

    private static final String DEFAULT_TABLE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_QR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_QR_CODE = "BBBBBBBBBB";

    @Autowired
    private QrCodeTablesRepository qrCodeTablesRepository;

    /**
     * This repository is mocked in the com.yopla.ordering.repository.search test package.
     *
     * @see com.yopla.ordering.repository.search.QrCodeTablesSearchRepositoryMockConfiguration
     */
    @Autowired
    private QrCodeTablesSearchRepository mockQrCodeTablesSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQrCodeTablesMockMvc;

    private QrCodeTables qrCodeTables;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QrCodeTables createEntity(EntityManager em) {
        QrCodeTables qrCodeTables = new QrCodeTables()
            .tableNumber(DEFAULT_TABLE_NUMBER)
            .qrCode(DEFAULT_QR_CODE);
        return qrCodeTables;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QrCodeTables createUpdatedEntity(EntityManager em) {
        QrCodeTables qrCodeTables = new QrCodeTables()
            .tableNumber(UPDATED_TABLE_NUMBER)
            .qrCode(UPDATED_QR_CODE);
        return qrCodeTables;
    }

    @BeforeEach
    public void initTest() {
        qrCodeTables = createEntity(em);
    }

    @Test
    @Transactional
    public void createQrCodeTables() throws Exception {
        int databaseSizeBeforeCreate = qrCodeTablesRepository.findAll().size();

        // Create the QrCodeTables
        restQrCodeTablesMockMvc.perform(post("/api/qr-code-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qrCodeTables)))
            .andExpect(status().isCreated());

        // Validate the QrCodeTables in the database
        List<QrCodeTables> qrCodeTablesList = qrCodeTablesRepository.findAll();
        assertThat(qrCodeTablesList).hasSize(databaseSizeBeforeCreate + 1);
        QrCodeTables testQrCodeTables = qrCodeTablesList.get(qrCodeTablesList.size() - 1);
        assertThat(testQrCodeTables.getTableNumber()).isEqualTo(DEFAULT_TABLE_NUMBER);
        assertThat(testQrCodeTables.getQrCode()).isEqualTo(DEFAULT_QR_CODE);

        // Validate the QrCodeTables in Elasticsearch
        verify(mockQrCodeTablesSearchRepository, times(1)).save(testQrCodeTables);
    }

    @Test
    @Transactional
    public void createQrCodeTablesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qrCodeTablesRepository.findAll().size();

        // Create the QrCodeTables with an existing ID
        qrCodeTables.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQrCodeTablesMockMvc.perform(post("/api/qr-code-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qrCodeTables)))
            .andExpect(status().isBadRequest());

        // Validate the QrCodeTables in the database
        List<QrCodeTables> qrCodeTablesList = qrCodeTablesRepository.findAll();
        assertThat(qrCodeTablesList).hasSize(databaseSizeBeforeCreate);

        // Validate the QrCodeTables in Elasticsearch
        verify(mockQrCodeTablesSearchRepository, times(0)).save(qrCodeTables);
    }


    @Test
    @Transactional
    public void getAllQrCodeTables() throws Exception {
        // Initialize the database
        qrCodeTablesRepository.saveAndFlush(qrCodeTables);

        // Get all the qrCodeTablesList
        restQrCodeTablesMockMvc.perform(get("/api/qr-code-tables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qrCodeTables.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableNumber").value(hasItem(DEFAULT_TABLE_NUMBER)))
            .andExpect(jsonPath("$.[*].qrCode").value(hasItem(DEFAULT_QR_CODE)));
    }
    
    @Test
    @Transactional
    public void getQrCodeTables() throws Exception {
        // Initialize the database
        qrCodeTablesRepository.saveAndFlush(qrCodeTables);

        // Get the qrCodeTables
        restQrCodeTablesMockMvc.perform(get("/api/qr-code-tables/{id}", qrCodeTables.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qrCodeTables.getId().intValue()))
            .andExpect(jsonPath("$.tableNumber").value(DEFAULT_TABLE_NUMBER))
            .andExpect(jsonPath("$.qrCode").value(DEFAULT_QR_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingQrCodeTables() throws Exception {
        // Get the qrCodeTables
        restQrCodeTablesMockMvc.perform(get("/api/qr-code-tables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQrCodeTables() throws Exception {
        // Initialize the database
        qrCodeTablesRepository.saveAndFlush(qrCodeTables);

        int databaseSizeBeforeUpdate = qrCodeTablesRepository.findAll().size();

        // Update the qrCodeTables
        QrCodeTables updatedQrCodeTables = qrCodeTablesRepository.findById(qrCodeTables.getId()).get();
        // Disconnect from session so that the updates on updatedQrCodeTables are not directly saved in db
        em.detach(updatedQrCodeTables);
        updatedQrCodeTables
            .tableNumber(UPDATED_TABLE_NUMBER)
            .qrCode(UPDATED_QR_CODE);

        restQrCodeTablesMockMvc.perform(put("/api/qr-code-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQrCodeTables)))
            .andExpect(status().isOk());

        // Validate the QrCodeTables in the database
        List<QrCodeTables> qrCodeTablesList = qrCodeTablesRepository.findAll();
        assertThat(qrCodeTablesList).hasSize(databaseSizeBeforeUpdate);
        QrCodeTables testQrCodeTables = qrCodeTablesList.get(qrCodeTablesList.size() - 1);
        assertThat(testQrCodeTables.getTableNumber()).isEqualTo(UPDATED_TABLE_NUMBER);
        assertThat(testQrCodeTables.getQrCode()).isEqualTo(UPDATED_QR_CODE);

        // Validate the QrCodeTables in Elasticsearch
        verify(mockQrCodeTablesSearchRepository, times(1)).save(testQrCodeTables);
    }

    @Test
    @Transactional
    public void updateNonExistingQrCodeTables() throws Exception {
        int databaseSizeBeforeUpdate = qrCodeTablesRepository.findAll().size();

        // Create the QrCodeTables

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQrCodeTablesMockMvc.perform(put("/api/qr-code-tables")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qrCodeTables)))
            .andExpect(status().isBadRequest());

        // Validate the QrCodeTables in the database
        List<QrCodeTables> qrCodeTablesList = qrCodeTablesRepository.findAll();
        assertThat(qrCodeTablesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the QrCodeTables in Elasticsearch
        verify(mockQrCodeTablesSearchRepository, times(0)).save(qrCodeTables);
    }

    @Test
    @Transactional
    public void deleteQrCodeTables() throws Exception {
        // Initialize the database
        qrCodeTablesRepository.saveAndFlush(qrCodeTables);

        int databaseSizeBeforeDelete = qrCodeTablesRepository.findAll().size();

        // Delete the qrCodeTables
        restQrCodeTablesMockMvc.perform(delete("/api/qr-code-tables/{id}", qrCodeTables.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QrCodeTables> qrCodeTablesList = qrCodeTablesRepository.findAll();
        assertThat(qrCodeTablesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the QrCodeTables in Elasticsearch
        verify(mockQrCodeTablesSearchRepository, times(1)).deleteById(qrCodeTables.getId());
    }

    @Test
    @Transactional
    public void searchQrCodeTables() throws Exception {
        // Initialize the database
        qrCodeTablesRepository.saveAndFlush(qrCodeTables);
        when(mockQrCodeTablesSearchRepository.search(queryStringQuery("id:" + qrCodeTables.getId())))
            .thenReturn(Collections.singletonList(qrCodeTables));
        // Search the qrCodeTables
        restQrCodeTablesMockMvc.perform(get("/api/_search/qr-code-tables?query=id:" + qrCodeTables.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qrCodeTables.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableNumber").value(hasItem(DEFAULT_TABLE_NUMBER)))
            .andExpect(jsonPath("$.[*].qrCode").value(hasItem(DEFAULT_QR_CODE)));
    }
}
