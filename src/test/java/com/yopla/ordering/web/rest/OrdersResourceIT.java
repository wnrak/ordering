package com.yopla.ordering.web.rest;

import com.yopla.ordering.YoplaApp;
import com.yopla.ordering.domain.Orders;
import com.yopla.ordering.repository.OrdersRepository;
import com.yopla.ordering.repository.search.OrdersSearchRepository;

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
 * Integration tests for the {@link OrdersResource} REST controller.
 */
@SpringBootTest(classes = YoplaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrdersResourceIT {

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Integer UPDATED_ORDER_NUMBER = 2;

    private static final String DEFAULT_ORDER_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TABLE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_TOTAL_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_AMOUNT = 2D;

    @Autowired
    private OrdersRepository ordersRepository;

    /**
     * This repository is mocked in the com.yopla.ordering.repository.search test package.
     *
     * @see com.yopla.ordering.repository.search.OrdersSearchRepositoryMockConfiguration
     */
    @Autowired
    private OrdersSearchRepository mockOrdersSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdersMockMvc;

    private Orders orders;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orders createEntity(EntityManager em) {
        Orders orders = new Orders()
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .tableNumber(DEFAULT_TABLE_NUMBER)
            .customerName(DEFAULT_CUSTOMER_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .address(DEFAULT_ADDRESS)
            .totalAmount(DEFAULT_TOTAL_AMOUNT);
        return orders;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orders createUpdatedEntity(EntityManager em) {
        Orders orders = new Orders()
            .orderNumber(UPDATED_ORDER_NUMBER)
            .orderStatus(UPDATED_ORDER_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .tableNumber(UPDATED_TABLE_NUMBER)
            .customerName(UPDATED_CUSTOMER_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .totalAmount(UPDATED_TOTAL_AMOUNT);
        return orders;
    }

    @BeforeEach
    public void initTest() {
        orders = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrders() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders
        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orders)))
            .andExpect(status().isCreated());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate + 1);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testOrders.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testOrders.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testOrders.getTableNumber()).isEqualTo(DEFAULT_TABLE_NUMBER);
        assertThat(testOrders.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testOrders.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrders.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testOrders.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOrders.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);

        // Validate the Orders in Elasticsearch
        verify(mockOrdersSearchRepository, times(1)).save(testOrders);
    }

    @Test
    @Transactional
    public void createOrdersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders with an existing ID
        orders.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdersMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orders)))
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate);

        // Validate the Orders in Elasticsearch
        verify(mockOrdersSearchRepository, times(0)).save(orders);
    }


    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList
        restOrdersMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS)))
            .andExpect(jsonPath("$.[*].tableNumber").value(hasItem(DEFAULT_TABLE_NUMBER)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", orders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orders.getId().intValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS))
            .andExpect(jsonPath("$.tableNumber").value(DEFAULT_TABLE_NUMBER))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrders() throws Exception {
        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Update the orders
        Orders updatedOrders = ordersRepository.findById(orders.getId()).get();
        // Disconnect from session so that the updates on updatedOrders are not directly saved in db
        em.detach(updatedOrders);
        updatedOrders
            .orderNumber(UPDATED_ORDER_NUMBER)
            .orderStatus(UPDATED_ORDER_STATUS)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .tableNumber(UPDATED_TABLE_NUMBER)
            .customerName(UPDATED_CUSTOMER_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .totalAmount(UPDATED_TOTAL_AMOUNT);

        restOrdersMockMvc.perform(put("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrders)))
            .andExpect(status().isOk());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testOrders.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrders.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testOrders.getTableNumber()).isEqualTo(UPDATED_TABLE_NUMBER);
        assertThat(testOrders.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testOrders.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrders.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testOrders.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOrders.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);

        // Validate the Orders in Elasticsearch
        verify(mockOrdersSearchRepository, times(1)).save(testOrders);
    }

    @Test
    @Transactional
    public void updateNonExistingOrders() throws Exception {
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Create the Orders

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdersMockMvc.perform(put("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orders)))
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Orders in Elasticsearch
        verify(mockOrdersSearchRepository, times(0)).save(orders);
    }

    @Test
    @Transactional
    public void deleteOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        int databaseSizeBeforeDelete = ordersRepository.findAll().size();

        // Delete the orders
        restOrdersMockMvc.perform(delete("/api/orders/{id}", orders.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Orders in Elasticsearch
        verify(mockOrdersSearchRepository, times(1)).deleteById(orders.getId());
    }

    @Test
    @Transactional
    public void searchOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);
        when(mockOrdersSearchRepository.search(queryStringQuery("id:" + orders.getId())))
            .thenReturn(Collections.singletonList(orders));
        // Search the orders
        restOrdersMockMvc.perform(get("/api/_search/orders?query=id:" + orders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS)))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS)))
            .andExpect(jsonPath("$.[*].tableNumber").value(hasItem(DEFAULT_TABLE_NUMBER)))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.doubleValue())));
    }
}
