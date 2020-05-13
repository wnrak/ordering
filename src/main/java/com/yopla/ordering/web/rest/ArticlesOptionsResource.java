package com.yopla.ordering.web.rest;

import com.yopla.ordering.domain.ArticlesOptions;
import com.yopla.ordering.repository.ArticlesOptionsRepository;
import com.yopla.ordering.repository.search.ArticlesOptionsSearchRepository;
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
 * REST controller for managing {@link com.yopla.ordering.domain.ArticlesOptions}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ArticlesOptionsResource {

    private final Logger log = LoggerFactory.getLogger(ArticlesOptionsResource.class);

    private static final String ENTITY_NAME = "articlesOptions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticlesOptionsRepository articlesOptionsRepository;

    private final ArticlesOptionsSearchRepository articlesOptionsSearchRepository;

    public ArticlesOptionsResource(ArticlesOptionsRepository articlesOptionsRepository, ArticlesOptionsSearchRepository articlesOptionsSearchRepository) {
        this.articlesOptionsRepository = articlesOptionsRepository;
        this.articlesOptionsSearchRepository = articlesOptionsSearchRepository;
    }

    /**
     * {@code POST  /articles-options} : Create a new articlesOptions.
     *
     * @param articlesOptions the articlesOptions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articlesOptions, or with status {@code 400 (Bad Request)} if the articlesOptions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/articles-options")
    public ResponseEntity<ArticlesOptions> createArticlesOptions(@RequestBody ArticlesOptions articlesOptions) throws URISyntaxException {
        log.debug("REST request to save ArticlesOptions : {}", articlesOptions);
        if (articlesOptions.getId() != null) {
            throw new BadRequestAlertException("A new articlesOptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArticlesOptions result = articlesOptionsRepository.save(articlesOptions);
        articlesOptionsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/articles-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /articles-options} : Updates an existing articlesOptions.
     *
     * @param articlesOptions the articlesOptions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articlesOptions,
     * or with status {@code 400 (Bad Request)} if the articlesOptions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articlesOptions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/articles-options")
    public ResponseEntity<ArticlesOptions> updateArticlesOptions(@RequestBody ArticlesOptions articlesOptions) throws URISyntaxException {
        log.debug("REST request to update ArticlesOptions : {}", articlesOptions);
        if (articlesOptions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArticlesOptions result = articlesOptionsRepository.save(articlesOptions);
        articlesOptionsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articlesOptions.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /articles-options} : get all the articlesOptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articlesOptions in body.
     */
    @GetMapping("/articles-options")
    public List<ArticlesOptions> getAllArticlesOptions() {
        log.debug("REST request to get all ArticlesOptions");
        return articlesOptionsRepository.findAll();
    }

    /**
     * {@code GET  /articles-options/:id} : get the "id" articlesOptions.
     *
     * @param id the id of the articlesOptions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articlesOptions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/articles-options/{id}")
    public ResponseEntity<ArticlesOptions> getArticlesOptions(@PathVariable Long id) {
        log.debug("REST request to get ArticlesOptions : {}", id);
        Optional<ArticlesOptions> articlesOptions = articlesOptionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(articlesOptions);
    }

    /**
     * {@code DELETE  /articles-options/:id} : delete the "id" articlesOptions.
     *
     * @param id the id of the articlesOptions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/articles-options/{id}")
    public ResponseEntity<Void> deleteArticlesOptions(@PathVariable Long id) {
        log.debug("REST request to delete ArticlesOptions : {}", id);
        articlesOptionsRepository.deleteById(id);
        articlesOptionsSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/articles-options?query=:query} : search for the articlesOptions corresponding
     * to the query.
     *
     * @param query the query of the articlesOptions search.
     * @return the result of the search.
     */
    @GetMapping("/_search/articles-options")
    public List<ArticlesOptions> searchArticlesOptions(@RequestParam String query) {
        log.debug("REST request to search ArticlesOptions for query {}", query);
        return StreamSupport
            .stream(articlesOptionsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
