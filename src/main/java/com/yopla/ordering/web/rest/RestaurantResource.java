package com.yopla.ordering.web.rest;

import com.yopla.ordering.domain.Restaurant;
import com.yopla.ordering.repository.RestaurantRepository;
import com.yopla.ordering.repository.search.RestaurantSearchRepository;
import com.yopla.ordering.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.yopla.ordering.domain.Restaurant}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RestaurantResource {

    private final Logger log = LoggerFactory.getLogger(RestaurantResource.class);

    private static final String ENTITY_NAME = "restaurant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RestaurantRepository restaurantRepository;

    private final RestaurantSearchRepository restaurantSearchRepository;

    public RestaurantResource(RestaurantRepository restaurantRepository, RestaurantSearchRepository restaurantSearchRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantSearchRepository = restaurantSearchRepository;
    }

    /**
     * {@code POST  /restaurants} : Create a new restaurant.
     *
     * @param restaurant the restaurant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new restaurant, or with status {@code 400 (Bad Request)} if the restaurant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/restaurants")
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) throws URISyntaxException {
        log.debug("REST request to save Restaurant : {}", restaurant);
        if (restaurant.getId() != null) {
            throw new BadRequestAlertException("A new restaurant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Restaurant result = restaurantRepository.save(restaurant);
        restaurantSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/restaurants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /restaurants} : Updates an existing restaurant.
     *
     * @param restaurant the restaurant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated restaurant,
     * or with status {@code 400 (Bad Request)} if the restaurant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the restaurant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/restaurants")
    public ResponseEntity<Restaurant> updateRestaurant(@Valid @RequestBody Restaurant restaurant) throws URISyntaxException {
        log.debug("REST request to update Restaurant : {}", restaurant);
        if (restaurant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Restaurant result = restaurantRepository.save(restaurant);
        restaurantSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, restaurant.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /restaurants} : get all the restaurants.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of restaurants in body.
     */
    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurants() {
        log.debug("REST request to get all Restaurants");
        return restaurantRepository.findAll();
    }

    /**
     * {@code GET  /restaurants/:id} : get the "id" restaurant.
     *
     * @param id the id of the restaurant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the restaurant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable Long id) {
        log.debug("REST request to get Restaurant : {}", id);
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(restaurant);
    }

    /**
     * {@code DELETE  /restaurants/:id} : delete the "id" restaurant.
     *
     * @param id the id of the restaurant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        log.debug("REST request to delete Restaurant : {}", id);
        restaurantRepository.deleteById(id);
        restaurantSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/restaurants?query=:query} : search for the restaurant corresponding
     * to the query.
     *
     * @param query the query of the restaurant search.
     * @return the result of the search.
     */
    @GetMapping("/_search/restaurants")
    public List<Restaurant> searchRestaurants(@RequestParam String query) {
        log.debug("REST request to search Restaurants for query {}", query);
        return StreamSupport
            .stream(restaurantSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
