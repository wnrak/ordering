package com.yopla.ordering.web.rest;

import com.yopla.ordering.domain.Orders;
import com.yopla.ordering.repository.OrdersRepository;
import com.yopla.ordering.repository.search.OrdersSearchRepository;
import com.yopla.ordering.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.yopla.ordering.domain.Orders}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrdersResource {

    private final Logger log = LoggerFactory.getLogger(OrdersResource.class);

    private static final String ENTITY_NAME = "orders";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrdersRepository ordersRepository;

    private final OrdersSearchRepository ordersSearchRepository;

    public OrdersResource(OrdersRepository ordersRepository, OrdersSearchRepository ordersSearchRepository) {
        this.ordersRepository = ordersRepository;
        this.ordersSearchRepository = ordersSearchRepository;
    }

    /**
     * {@code POST  /orders} : Create a new orders.
     *
     * @param orders the orders to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orders, or with status {@code 400 (Bad Request)} if the orders has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/orders")
    public ResponseEntity<Orders> createOrders(@RequestBody Orders orders) throws URISyntaxException {
        log.debug("REST request to save Orders : {}", orders);
        if (orders.getId() != null) {
            throw new BadRequestAlertException("A new orders cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Orders result = ordersRepository.save(orders);
        ordersSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /orders} : Updates an existing orders.
     *
     * @param orders the orders to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orders,
     * or with status {@code 400 (Bad Request)} if the orders is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orders couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/orders")
    public ResponseEntity<Orders> updateOrders(@RequestBody Orders orders) throws URISyntaxException {
        log.debug("REST request to update Orders : {}", orders);
        if (orders.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Orders result = ordersRepository.save(orders);
        ordersSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orders.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /orders} : get all the orders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orders in body.
     */
    @GetMapping("/orders")
    public List<Orders> getAllOrders() {
        log.debug("REST request to get all Orders");
        return ordersRepository.findAll();
    }

    /**
     * {@code GET  /orders/:id} : get the "id" orders.
     *
     * @param id the id of the orders to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orders, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<Orders> getOrders(@PathVariable Long id) {
        log.debug("REST request to get Orders : {}", id);
        Optional<Orders> orders = ordersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orders);
    }

    /**
     * {@code DELETE  /orders/:id} : delete the "id" orders.
     *
     * @param id the id of the orders to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrders(@PathVariable Long id) {
        log.debug("REST request to delete Orders : {}", id);
        ordersRepository.deleteById(id);
        ordersSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/orders?query=:query} : search for the orders corresponding
     * to the query.
     *
     * @param query the query of the orders search.
     * @return the result of the search.
     */
    @GetMapping("/_search/orders")
    public List<Orders> searchOrders(@RequestParam String query) {
        log.debug("REST request to search Orders for query {}", query);
        return StreamSupport
            .stream(ordersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
